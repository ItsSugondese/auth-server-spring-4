package com.lazy.authserver.service.user;

import com.lazy.authserver.config.CustomMessageSource;
import com.lazy.authserver.constant.ModuleNameConstants;
import com.lazy.authserver.dto.user.UserDetailsResponse;
import com.lazy.authserver.dto.user.UserEmailDetailsRequest;
import com.lazy.authserver.entity.user.Users;
import com.lazy.authserver.enums.Message;
import com.lazy.authserver.enums.PasswordSetType;
import com.lazy.authserver.exception.AppException;
import com.lazy.authserver.pojo.user.resetPassword.ResetPasswordDetailRequestPojo;
import com.lazy.authserver.repository.oauth.ClientRepository;
import com.lazy.authserver.repository.user.UserRepo;
import com.lazy.authserver.utils.NullAwareBeanUtilsBean;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author: Santosh Paudel
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();


    private final UserServiceHelper userServiceHelper;
    private final CustomMessageSource customMessageSource;
    private final ClientRepository clientRepository;
    private final UsersClientMappingService usersClientMappingService;
    private final HttpServletRequest servletRequest;


    @Override
    @Transactional
    @Modifying
    public void saveUser(UserEmailDetailsRequest userRequestPojo) throws Exception {
        saveValidation(userRequestPojo);

        Users users = new Users();
        try {
            beanUtilsBean.copyProperties(users, userRequestPojo);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AppException(e.getMessage(), e);
        }

        users.setUsername(userRequestPojo.getEmail());
        users = userRepo.saveAndFlush(users);

        usersClientMappingService.saveUserClientMapping(users, userRequestPojo);

        userServiceHelper.resetPasswordMailSendHelper(ResetPasswordDetailRequestPojo.builder()
                .userEmail(users.getEmail())
                .passwordSetType(PasswordSetType.SET)
                .baseUrl(servletRequest.getHeader("Origin"))
                .clientId(userRequestPojo.getClientId())
                .build());

    }

    private void saveValidation(UserEmailDetailsRequest userRequestPojo) {
        boolean doesClientExists = clientRepository.existsByClientId(userRequestPojo.getClientId());

        if (!doesClientExists) {
            throw new AppException(customMessageSource.get(Message.ID_NOT_FOUND.getCode(), ModuleNameConstants.CLIENT));
        }

        UserDetailsResponse userDetails = usersClientMappingService.getUserDetailsByEmailAndClientId(userRequestPojo);

        if (userDetails != null) {
            throw new AppException("User already registered. Please try from forgot password.");
        }
    }


    @Override
    public void deleteUserById(Integer id) {
        userRepo.deleteById(id);
    }

    @Override
    public Users findById(Integer id) {
        return userRepo.findById(id).orElseThrow(() -> new AppException(customMessageSource.get(Message.ID_NOT_FOUND.getCode(), ModuleNameConstants.USER)));
    }

}
