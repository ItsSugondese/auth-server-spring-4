package com.lazy.authserver.controller.user;

import com.lazy.authserver.constant.MessageConstants;
import com.lazy.authserver.constant.ModuleNameConstants;
import com.lazy.authserver.enums.Message;
import com.lazy.authserver.generic.controller.BaseController;
import com.lazy.authserver.pojo.GlobalApiResponse;
import com.lazy.authserver.pojo.user.UserRequestPojo;
import com.lazy.authserver.pojo.user.resetPassword.ResetPasswordDetailRequestPojo;
import com.lazy.authserver.service.user.UserService;
import com.lazy.authserver.service.user.UserServiceHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: Santosh Paudel
 */

@RestController
@RequestMapping("/user")
@Tag(name = ModuleNameConstants.APP_USER)
public class UserController extends BaseController {

    private final UserService userService;

    private final UserServiceHelper userServiceHelper;

    public UserController(UserService userService, UserServiceHelper userServiceHelper) {
        this.userService = userService;
        this.userServiceHelper = userServiceHelper;
        this.moduleName = "User";
    }

    @GetMapping
    @Operation(summary = "Use this api save and update internal and external user by admin. do not send email address for update")
    public ResponseEntity<GlobalApiResponse> test() throws Exception {
        return ResponseEntity.ok(successResponse(customMessageSource.get(MessageConstants.CRUD_SAVE, moduleName),
                "okay"));
    }
    @PostMapping
    @Operation(summary = "Use this api save and update internal and external user by admin. do not send email address for update")
    public ResponseEntity<GlobalApiResponse> saveUser(@RequestBody UserRequestPojo userRequestPojo) throws Exception {
        return ResponseEntity.ok(successResponse(customMessageSource.get(MessageConstants.CRUD_SAVE, moduleName),
                userService.saveUser(userRequestPojo)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalApiResponse> deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok(successResponse(customMessageSource.get(MessageConstants.DELETE, moduleName), null));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<GlobalApiResponse> forgotPassword(@RequestBody ResetPasswordDetailRequestPojo requestPojo) {
        return ResponseEntity.ok(successResponse(customMessageSource.get((MessageConstants.USER_FORGOT_PASSWORD), (Object) null),
                userServiceHelper.resetPasswordMailSendHelper(requestPojo)));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<GlobalApiResponse> validateToken(@RequestBody ResetPasswordDetailRequestPojo requestPojo) {
        return ResponseEntity.ok(successResponse(customMessageSource.get((MessageConstants.TOKEN_VALID), (Object) null),
                userServiceHelper.validateTokenHelper(requestPojo)));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<GlobalApiResponse> resetPassword(@RequestBody ResetPasswordDetailRequestPojo requestPojo) {
        return ResponseEntity.ok(successResponse(customMessageSource.get((MessageConstants.USER_PASSWORD_CHANGED), (Object) null),
                userServiceHelper.resetPasswordHelper(requestPojo)));
    }

}
