package com.lazy.authserver.pojo.user.passwordUpdate;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePasswordRequestPojo {
    private String oldPassword;
    private String newPassword;

}
