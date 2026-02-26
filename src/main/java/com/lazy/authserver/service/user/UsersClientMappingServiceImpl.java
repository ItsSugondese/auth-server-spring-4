package com.lazy.authserver.service.user;

import com.lazy.authserver.config.CustomMessageSource;
import com.lazy.authserver.constant.ModuleNameConstants;
import com.lazy.authserver.dto.user.UserDetailsResponse;
import com.lazy.authserver.dto.user.UserEmailDetailsRequest;
import com.lazy.authserver.entity.user.Users;
import com.lazy.authserver.entity.user.UsersClientMapping;
import com.lazy.authserver.enums.Message;
import com.lazy.authserver.exception.AppException;
import com.lazy.authserver.mapper.user.UsersClientMappingMapper;
import com.lazy.authserver.repository.user.UsersClientMappingRepo;
import com.lazy.authserver.utils.NullAwareBeanUtilsBean;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
@RequiredArgsConstructor
public class UsersClientMappingServiceImpl implements UsersClientMappingService{

    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final UsersClientMappingRepo usersClientMappingRepo;
    private final UsersClientMappingMapper usersClientMappingMapper;
    private final CustomMessageSource customMessageSource;

    @Override
    public void saveUserClientMapping(Users users, UserEmailDetailsRequest request) {
        UsersClientMapping usersClientMapping = new UsersClientMapping();

        try {
            beanUtilsBean.copyProperties(usersClientMapping, request);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AppException(e.getMessage(), e);
        }

        usersClientMapping.setUsers(users);

        usersClientMappingRepo.save(usersClientMapping);

    }

    @Override
    public UsersClientMapping findByClientIdAndEmail(String clientId, String email) {
        return usersClientMappingRepo.findByClientIdAndUsersEmail(clientId, email)
                .orElseThrow(() -> new AppException(customMessageSource.get(Message.ID_NOT_FOUND.getCode(), ModuleNameConstants.USER_CLIENT)));
    }

    @Override
    public UserDetailsResponse getUserDetailsByEmailAndClientId(UserEmailDetailsRequest request) {
        return usersClientMappingMapper.getUserDetailsByEmailAndClientId(request.getEmail(), request.getClientId());
    }
}
