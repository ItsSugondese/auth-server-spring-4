package com.lazy.authserver.pojo.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Santosh Paudel
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestPojo {
   private Integer id = 0;
   private String transactionPassword;
   private Integer profileId;
   @NotBlank
   private String fullName;
   @NotBlank
   private String emailAddress;
   @NotBlank
   private String mobile;
   private List<Integer> addedRoleId;
   private List<Integer> removedRoleId;
   private String description;
}
