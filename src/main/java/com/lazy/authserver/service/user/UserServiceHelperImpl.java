package com.lazy.authserver.service.user;

import com.lazy.authserver.config.CustomMessageSource;
import com.lazy.authserver.repository.user.UserRepo;
import com.lazy.authserver.utils.mail.EmailServiceHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.lazy.authserver.repository.passwordReset.PasswordResetTokenRepo;
import com.lazy.authserver.service.user.resetPassword.ResetPasswordService;
import com.lazy.authserver.exception.AppException;
import com.lazy.authserver.pojo.user.resetPassword.ResetPasswordDetailRequestPojo;


/**
 * @Author: Santosh Paudel
 */

@RequiredArgsConstructor
@Service
public class UserServiceHelperImpl implements UserServiceHelper {

   private final UserRepo userRepo;



   private final ResetPasswordService resetPasswordService;

   private final EmailServiceHelper emailServiceHelper;

//   private final UserDataConfig userDataConfig;

   private final CustomMessageSource customMessageSource;

   private final PasswordEncoder passwordEncoder;

   private final PasswordResetTokenRepo passwordResetTokenRepo;


   @Override
   @Transactional
   public Boolean resetPasswordHelper(ResetPasswordDetailRequestPojo requestPojo) {
      try {
         resetPasswordService.resetPassword(requestPojo);
         emailServiceHelper.sendResetPasswordSuccessEmail(requestPojo);
         return true;
      } catch (Exception e) {
         throw new AppException(e.getMessage());
      }
   }

   @Override
   public ResetPasswordDetailRequestPojo resetPasswordMailSendHelper(ResetPasswordDetailRequestPojo requestPojo) {
      try {
         resetPasswordService.resetPasswordEmailVerify(requestPojo);
         if (requestPojo.getResetToken() != null) emailServiceHelper.sendResetPasswordEmail(requestPojo);
         return null;
      } catch (Exception e) {
         throw new AppException(e.getMessage());
      }
   }

   @Override
   public ResetPasswordDetailRequestPojo validateTokenHelper(ResetPasswordDetailRequestPojo requestPojo) {
      try {
         resetPasswordService.validatePasswordResetToken(requestPojo);
         return requestPojo;
      } catch (Exception e) {
         throw new AppException(e.getMessage());
      }
   }

}
