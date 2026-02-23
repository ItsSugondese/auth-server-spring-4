//package com.lazy.authserver.utils;
//
//import com.lazy.authserver.config.jwt.JwtHelper;
//import com.lazy.authserver.enums.user.UserType;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserDataConfig {
//
//    private final JwtHelper jwtUtil;
//    private final HttpServletRequest request;
//
////    public Long userId() {
////        String accessToken = jwtUtil.extractAccessTokenFromCookies(request).orElse(null);
////        String refreshToken = jwtUtil.extractRefreshTokenFromCookies(request).orElse(null);
////
////        if (accessToken != null) {
////            return jwtUtil.extractAppUserId(accessToken);
////        } else if (refreshToken != null) {
////            return jwtUtil.extractAppUserId(refreshToken);
////        }
////
////        return null;
////    }
//
//    public Long userId() {
//        return  jwtUtil.extractAppUserId(request.getHeader("Authorization").substring(7));
//
//    }
//
//    public UserType getUserType() {
//        String accessToken = jwtUtil.extractAccessTokenFromCookies(request).orElse(null);
//        String refreshToken = jwtUtil.extractRefreshTokenFromCookies(request).orElse(null);
//
//        if (accessToken != null) {
//            return jwtUtil.extractAppUserType(accessToken);
//        } else if (refreshToken != null) {
//            return jwtUtil.extractAppUserType(refreshToken);
//        }
//
//        return null;
//    }
//
//
//}
