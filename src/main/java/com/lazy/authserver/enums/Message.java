package com.lazy.authserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Message {
    SAVE("success.save"),
    RETRIEVE("success.retrieve"),
    UPDATE("success.update"),
    DELETE("success.delete"),
    ALREADY_EXISTS("error.already.exist"),
    ENTITY_ID_NOT_FOUND("entity.id.notfound"),
    ERROR_ALREADY_APPLIED("error.already.applied"),
    ERROR_DEFAULT("error.default"),
    ERROR_DATABASE_FETCH("error.database.fetch"),
    INFO_SAVE("info.save"),
    ERROR_ALREADY_EXIST("error.already.exist"),
    USER_REGISTRATION("user.registration"),
    USER_PASSWORD_CHANGED("user.password.changed"),
    USER_FORGOT_PASSWORD("user.forgot.password"),
    USER_PASSWORD_CHANGE("user.password.change"),
    USER_TEMP_PASSWORD_SENT("user.temp.password.sent"),
    USER_CHANGE_PASSWORD("user.change.password"),
    ELIGIBILITY_CHECK("eligibility.check"),
    ID_NOT_FOUND("id.notfound"),
    ERROR_ALREADY_PAID("error.already.paid"),
    ERROR_CONFIGURATION("error.configuration"),
    SCRUTINY_FINALISED("scrutiny.finalised"),
    DATE_FINALISED("date.finalised"),
    DATE_FINALISED_MESSAGE("date.finalised.message"),
    MAIL_SENT("mail.sent"),
    VERIFIED("verified"),
    INFO_AUTHENTICATE("info.authenticate"),
    MODIFY_LOGIN_PASSWORD("modify.login.password"),
    IS_NULL("is.must");

    public String code;
}
