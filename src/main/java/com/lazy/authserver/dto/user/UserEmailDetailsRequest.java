package com.lazy.authserver.dto.user;

import com.lazy.authserver.annotations.dtovalidation.RequiredIfIdNull;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEmailDetailsRequest {
    @NotNull
    private String email;
    @NotNull
    private String clientId;

}
