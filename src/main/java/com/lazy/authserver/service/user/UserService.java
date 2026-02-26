package com.lazy.authserver.service.user;

import com.lazy.authserver.dto.user.UserEmailDetailsRequest;
import com.lazy.authserver.entity.user.Users;

/**
 * @Author: Santosh Paudel
 */
public interface UserService {
   void saveUser(UserEmailDetailsRequest userRequestPojo) throws Exception;

   void deleteUserById(Integer id);

   Users findById(Integer id);



}
