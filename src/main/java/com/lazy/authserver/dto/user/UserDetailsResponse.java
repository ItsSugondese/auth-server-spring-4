package com.lazy.authserver.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsResponse {
    private String email;
    private String clientId;
    @JsonIgnore
    private boolean hasPassword;
}
