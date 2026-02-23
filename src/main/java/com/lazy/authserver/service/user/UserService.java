package com.lazy.authserver.service.user;

import com.lazy.authserver.entity.user.User;
import com.lazy.authserver.pojo.user.UserDetailResponsePojo;
import com.lazy.authserver.pojo.user.UserRequestPojo;

/**
 * @Author: Santosh Paudel
 */
public interface UserService {
   Boolean saveUser(UserRequestPojo userRequestPojo) throws Exception;

   void deleteUserById(Integer id);

   User findById(Integer id);



}
