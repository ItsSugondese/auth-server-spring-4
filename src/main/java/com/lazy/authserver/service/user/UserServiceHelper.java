package com.lazy.authserver.service.user;


import org.springframework.transaction.annotation.Transactional;
import com.lazy.authserver.pojo.user.passwordUpdate.UpdatePasswordRequestPojo;
import com.lazy.authserver.pojo.user.resetPassword.ResetPasswordDetailRequestPojo;

public interface UserServiceHelper {

   Boolean resetPasswordHelper(ResetPasswordDetailRequestPojo requestPojo);

   ResetPasswordDetailRequestPojo resetPasswordMailSendHelper(ResetPasswordDetailRequestPojo requestPojo);

   ResetPasswordDetailRequestPojo validateTokenHelper(ResetPasswordDetailRequestPojo requestPojo);



}
