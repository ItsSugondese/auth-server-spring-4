package com.lazy.authserver.pojo.user.resetPassword;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import com.lazy.authserver.enums.PasswordSetType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordDetailRequestPojo {
    private String userEmail;
    private String password;
    private String resetToken;
    private String baseUrl;
    private String fullName;
    private String clientId;
    @JsonIgnore
    private Long tokenId;
    private PasswordSetType passwordSetType = PasswordSetType.RESET;
}
