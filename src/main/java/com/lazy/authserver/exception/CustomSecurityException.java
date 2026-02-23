package com.lazy.authserver.exception;

import com.lazy.authserver.enums.ResponseStatus;
import com.lazy.authserver.pojo.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

public class CustomSecurityException {

    public static void forbiddenException(String message, HttpServletResponse response) {
                    HttpStatus httpStatus = HttpStatus.FORBIDDEN;
            final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), message, message);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                objectMapper.writeValue(response.getWriter(), apiError);
            }catch (Exception e){
                throw new AppException(e.getMessage(), e);
            }
    }

    public static void unAuthorizedException(String message, HttpServletResponse response) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), message, message);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(response.getWriter(), apiError);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }
    }

}
