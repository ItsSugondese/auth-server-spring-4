package com.lazy.authserver.service.user;


import com.lazy.authserver.pojo.user.resetPassword.ResetPasswordDetailRequestPojo;

public interface UserServiceHelper {

   Boolean resetPasswordHelper(ResetPasswordDetailRequestPojo requestPojo);

   ResetPasswordDetailRequestPojo resetPasswordMailSendHelper(ResetPasswordDetailRequestPojo requestPojo);

   ResetPasswordDetailRequestPojo validateTokenHelper(ResetPasswordDetailRequestPojo requestPojo);



}
