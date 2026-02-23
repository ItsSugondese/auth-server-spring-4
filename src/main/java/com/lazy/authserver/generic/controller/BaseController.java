package com.lazy.authserver.generic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazy.authserver.config.CustomMessageSource;
import com.lazy.authserver.enums.ResponseStatus;
import com.lazy.authserver.pojo.GlobalApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base Controller
 */
@SecurityRequirement(name = "oauth2")
public class BaseController {


    /**
     * ObjectMapper instance
     */
    public ObjectMapper objectMapper = new ObjectMapper();

    /**
     * API Success ResponseStatus
     */
    protected final ResponseStatus API_SUCCESS_STATUS = ResponseStatus.SUCCESS;

    /**
     * API Error ResponseStatus
     */
    protected final ResponseStatus API_ERROR_STATUS = ResponseStatus.FAIL;

    /**
     * Message Source Instance
     */
    @Autowired
    protected CustomMessageSource customMessageSource;

    /**
     * Module Name
     */
    protected String moduleName;
    protected String permissionName;
    protected String screenName;

    /**
     * This module is used to fetch the available permissions of current user in particular module
     */
    protected String module;


    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Function that sends successful API Response
     *
     * @param message
     * @param data
     * @return
     */
    protected GlobalApiResponse successResponse(String message, Object data) {
        GlobalApiResponse globalApiResponse = new GlobalApiResponse();
        globalApiResponse.setStatus(API_SUCCESS_STATUS);
        globalApiResponse.setMessage(message);
        globalApiResponse.setData(data);
        return globalApiResponse;
    }

    /**
     * Function that sends error API Response
     *
     * @param message
     * @param errors
     * @return
     */
    protected GlobalApiResponse errorResponse(String message, Object errors) {
        GlobalApiResponse globalApiResponse = new GlobalApiResponse();
        globalApiResponse.setStatus(API_ERROR_STATUS);
        globalApiResponse.setMessage(message);
        globalApiResponse.setData(errors);
        return globalApiResponse;
    }

    public String getPermissionName() {
        return permissionName;
    }

}
