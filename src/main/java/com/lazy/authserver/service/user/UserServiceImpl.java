package com.lazy.authserver.service.user;

import com.lazy.authserver.config.CustomMessageSource;
import com.lazy.authserver.constant.ModuleNameConstants;
import com.lazy.authserver.entity.user.User;
import com.lazy.authserver.enums.Message;
import com.lazy.authserver.enums.PasswordSetType;
import com.lazy.authserver.exception.AppException;
import com.lazy.authserver.mapper.user.UserDetailMapper;
import com.lazy.authserver.pojo.user.UserDetailResponsePojo;
import com.lazy.authserver.pojo.user.UserRequestPojo;
import com.lazy.authserver.repository.user.UserRepo;
import com.lazy.authserver.utils.NullAwareBeanUtilsBean;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final PasswordEncoder passwordEncoder;
    private final UserDetailMapper userDetailMapper;

    @Override
    @Transactional
    @Modifying
    public Boolean saveUser(UserRequestPojo userRequestPojo) throws Exception {
        User user = userRepo.findById(userRequestPojo.getId()).orElse(new User());

        return true;
    }


    @Override
    public void deleteUserById(Integer id) {
        userRepo.deleteById(id);
    }

    @Override
    public User findById(Integer id) {
        return userRepo.findById(id).orElseThrow(() -> new AppException(customMessageSource.get(Message.ID_NOT_FOUND.getCode(), ModuleNameConstants.APP_USER)));
    }

}
