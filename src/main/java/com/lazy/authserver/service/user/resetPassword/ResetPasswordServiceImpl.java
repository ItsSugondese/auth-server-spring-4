package com.lazy.authserver.service.user.resetPassword;


import com.lazy.authserver.config.CustomMessageSource;
import com.lazy.authserver.constant.MessageConstants;
import com.lazy.authserver.entity.auth.PasswordResetToken;
import com.lazy.authserver.entity.user.User;
import com.lazy.authserver.enums.PasswordSetType;
import com.lazy.authserver.exception.AppException;
import com.lazy.authserver.mapper.user.UserDetailMapper;
import com.lazy.authserver.pojo.user.resetPassword.ResetPasswordDetailRequestPojo;
import com.lazy.authserver.repository.passwordReset.PasswordResetTokenRepo;
import com.lazy.authserver.repository.user.UserRepo;
import com.lazy.authserver.utils.RandomGeneratorUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {
    private final UserRepo userRepo;
    private final PasswordResetTokenRepo passwordResetTokenRepo;
    private final CustomMessageSource customMessageSource;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailMapper userDetailMapper;
    private final RandomGeneratorUtil randomGeneratorUtil;

    @Override
    @Transactional
    public ResetPasswordDetailRequestPojo resetPasswordEmailVerify(ResetPasswordDetailRequestPojo requestPojo) {
        User user = userRepo.findByUsername(requestPojo.getUserEmail()).orElse(null);
        if (user != null) {
            String token = generateAndSaveToken(requestPojo.getPasswordSetType(), user.getId());
            requestPojo.setResetToken(token);
            return requestPojo;
        } else return null;
    }

    @Override
    public String generateAndSaveToken(PasswordSetType passwordSetType, Integer userId) {
        String token = passwordSetType == PasswordSetType.USER_AUTHENTICATION ? randomGeneratorUtil.generateRandomNumber(6) : UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(User.builder().id(userId).build());
        passwordResetToken.setExpiryDate(passwordSetType == PasswordSetType.SET ? LocalDateTime.now().plusHours(24) : LocalDateTime.now().plusMinutes(10));
        passwordResetToken.setStatus(1);
        passwordResetTokenRepo.saveAndFlush(passwordResetToken);
        return token;
    }

    @Override
    public ResetPasswordDetailRequestPojo validatePasswordResetToken(ResetPasswordDetailRequestPojo requestPojo) {
        PasswordResetToken resetToken = validateToken(requestPojo.getResetToken());
        requestPojo.setResetToken(resetToken.getToken());
        return requestPojo;
    }

    @Override
    @Transactional
    public ResetPasswordDetailRequestPojo resetPassword(ResetPasswordDetailRequestPojo requestPojo) {
        PasswordResetToken resetToken = validateToken(requestPojo.getResetToken());
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(requestPojo.getPassword()));
        user.setLastPasswordChangedAt(LocalDateTime.now());
        userRepo.saveAndFlush(user);
        requestPojo.setUserEmail(user.getUsername());
        if (requestPojo.getResetToken() != null) passwordResetTokenRepo.updateTokenIsActive(resetToken.getToken());
        return requestPojo;
    }

    @Override
    public ResetPasswordDetailRequestPojo setPasswordForNewUser(ResetPasswordDetailRequestPojo requestPojo) {
        User user = userRepo.findByEmailAddressAndPasswordNull(requestPojo.getUserEmail()).orElseThrow(() -> new AppException("Password reset link has been expired."));
        user.setPassword(passwordEncoder.encode(requestPojo.getPassword()));
        userRepo.saveAndFlush(user);
        requestPojo.setUserEmail(user.getUsername());
        return requestPojo;
    }

    @Override
    public PasswordResetToken validateToken(String token) {
        return passwordResetTokenRepo.findByToken(token).orElseThrow(() -> new AppException(customMessageSource.get(MessageConstants.TOKEN_INVALID)));
    }
}
