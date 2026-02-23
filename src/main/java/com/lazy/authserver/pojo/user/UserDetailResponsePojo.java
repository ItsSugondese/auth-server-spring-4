package com.lazy.authserver.pojo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lazy.authserver.constant.MessageConstants;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonIgnoreProperties
public class UserDetailResponsePojo {

    private Long id;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = MessageConstants.EMAIL_INVALID)
    private String emailAddress;
    private String mobile;
    private Boolean accountNonLocked;
    private String description;
    private String fullName;
    @JsonIgnore
    private String roleIdString;
    private String profile;
    private List<Integer> roleId = new ArrayList<>();

    public void setRoleIdString(String roleIdString) {
        this.roleIdString = roleIdString;
        this.roleId = (Arrays.asList(roleIdString.split(","))).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());;
    }
}
