package com.lazy.authserver.service.user.resetPassword;


import com.lazy.authserver.entity.auth.PasswordResetToken;
import com.lazy.authserver.enums.PasswordSetType;
import com.lazy.authserver.pojo.user.resetPassword.ResetPasswordDetailRequestPojo;

public interface ResetPasswordService {
    ResetPasswordDetailRequestPojo resetPasswordEmailVerify(ResetPasswordDetailRequestPojo requestPojo);

    String generateAndSaveToken(PasswordSetType passwordSetType, Integer userId);

    ResetPasswordDetailRequestPojo validatePasswordResetToken(ResetPasswordDetailRequestPojo requestPojo);

    ResetPasswordDetailRequestPojo resetPassword(ResetPasswordDetailRequestPojo requestPojo);

    ResetPasswordDetailRequestPojo setPasswordForNewUser(ResetPasswordDetailRequestPojo requestPojo);

    PasswordResetToken validateToken(String token);
}
