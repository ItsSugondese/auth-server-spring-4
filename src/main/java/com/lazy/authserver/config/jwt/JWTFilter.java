//package com.lazy.authserver.config.jwt;
//
//import com.lazy.authserver.config.security.UrlConstants;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//@Component
//public class JWTFilter extends OncePerRequestFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);
//
//    private final JwtHelper jwtHelper;
//    private final UserDetailsService userDetailsService;
//    private final HttpServletRequest servletRequest;
//
//    // ✅ Modern approach: Use List of path patterns
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String requestPath = request.getRequestURI();
//
//
//        if (!UrlConstants.PUBLIC_ENDPOINTS.matches(request)) {
//            //        // Extract token from cookies
////            String token = jwtHelper.extractAccessTokenFromCookies(request)
////                    .orElse(null);
////
////            if (token == null) {
////                logger.warn("⚠️ No access token found for: {}", requestPath);
////                sendUnauthorizedResponse(response, "No access token found");
////                return;
////            }
//
//            String token = request.getHeader("Authorization").substring(7);
//
//
//            if (token == null) {
//                logger.warn("⚠️ No access token found for: {}", requestPath);
//                sendUnauthorizedResponse(response, "No access token found");
//                return;
//            }
//
//            // Extract username from token with proper error handling
//            String username = null;
//            try {
//                username = jwtHelper.extractUsername(token);
//                logger.debug("✅ Extracted username: {}", username);
//            } catch (ExpiredJwtException e) {
//                logger.warn("⏰ Access token expired at: {} for path: {}",
//                        e.getClaims().getExpiration(), requestPath);
//                sendUnauthorizedResponse(response, "Access token expired");
//                return; // ✅ Stop here, send 401
//            } catch (MalformedJwtException e) {
//                logger.error("❌ Malformed JWT token: {}", e.getMessage());
//                sendUnauthorizedResponse(response, "Invalid token format");
//                return;
//            } catch (IllegalArgumentException e) {
//                logger.error("❌ Illegal argument: {}", e.getMessage());
//                sendUnauthorizedResponse(response, "Invalid token");
//                return;
//            } catch (Exception e) {
//                logger.error("❌ Unexpected error processing token: {}", e.getMessage());
//                sendUnauthorizedResponse(response, "Token validation failed");
//                return;
//            }
//
//            // Authenticate if not already authenticated
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                authenticateUser(username, token, request);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//
////    @Override
////    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
////            throws ServletException, IOException {
////
////        String requestPath = request.getRequestURI();
////
////        logger.info("🌐 Request: {} {}", request.getMethod(), requestPath);
////
////        // Skip authentication for public endpoints
////        if (isPublicEndpoint(requestPath)) {
////            logger.debug("✅ Public endpoint accessed: {}", requestPath);
////            filterChain.doFilter(request, response);
////            return;
////        }
////
////        // Extract token from cookies
////        String token = jwtHelper.extractAccessTokenFromCookies(request)
////                .orElse(null);
////
////        if (token == null) {
////            logger.warn("⚠️ No access token found for: {}", requestPath);
////            sendUnauthorizedResponse(response, "No access token found");
////            return;
////        }
////
////        // Extract username from token with proper error handling
////        String username = null;
////        try {
////            username = jwtHelper.extractUsername(token);
////            logger.debug("✅ Extracted username: {}", username);
////        } catch (ExpiredJwtException e) {
////            logger.warn("⏰ Access token expired at: {} for path: {}",
////                    e.getClaims().getExpiration(), requestPath);
////            sendUnauthorizedResponse(response, "Access token expired");
////            return; // ✅ Stop here, send 401
////        } catch (MalformedJwtException e) {
////            logger.error("❌ Malformed JWT token: {}", e.getMessage());
////            sendUnauthorizedResponse(response, "Invalid token format");
////            return;
////        } catch (IllegalArgumentException e) {
////            logger.error("❌ Illegal argument: {}", e.getMessage());
////            sendUnauthorizedResponse(response, "Invalid token");
////            return;
////        } catch (Exception e) {
////            logger.error("❌ Unexpected error processing token: {}", e.getMessage());
////            sendUnauthorizedResponse(response, "Token validation failed");
////            return;
////        }
////
////        // Authenticate if not already authenticated
////        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////            authenticateUser(username, token, request);
////        }
////
////        filterChain.doFilter(request, response);
////    }
//    /**
//     * Check if the request path matches any public endpoint pattern
//     * ✅ Modern approach: Custom path matching
//     */
////    private boolean isPublicEndpoint(String requestPath) {
////        return UrlConstants.PUBLIC_ENDPOINTS.stream()
////                .anyMatch(pattern -> pathMatches(requestPath, pattern));
////    }
//
//    /**
//     * Custom path matching logic
//     * Supports wildcards: ** (matches multiple path segments), * (matches single segment)
//     */
//    private boolean pathMatches(String requestPath, String pattern) {
//        // Remove trailing slashes for consistent matching
//        String cleanPath = requestPath.endsWith("/") ? requestPath.substring(0, requestPath.length() - 1) : requestPath;
//        String cleanPattern = pattern.endsWith("/") ? pattern.substring(0, pattern.length() - 1) : pattern;
//
//        // Handle ** wildcard (matches any number of path segments)
//        if (cleanPattern.endsWith("/**")) {
//            String basePattern = cleanPattern.substring(0, cleanPattern.length() - 3);
//            return cleanPath.startsWith(basePattern);
//        }
//
//        // Handle * wildcard (matches single path segment)
//        if (cleanPattern.contains("*")) {
//            return cleanPath.matches(cleanPattern.replace("*", "[^/]*"));
//        }
//
//        // Exact match
//        return cleanPath.equals(cleanPattern);
//    }
//
//    /**
//     * Send 401 Unauthorized response as JSON
//     */
//    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        String jsonResponse = String.format(
//                "{\"status\":%d,\"message\":\"%s\",\"data\":null}",
//                HttpStatus.UNAUTHORIZED.value(),
//                message
//        );
//
//        response.getWriter().write(jsonResponse);
//        response.getWriter().flush();
//    }
//
//    /**
//     * Authenticate user and set security context
//     */
//    private void authenticateUser(String username, String token, HttpServletRequest request) {
//        try {
//            // Load user details
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//            // Validate token
//            if (!jwtHelper.validateToken(token, userDetails)) {
//                logger.warn("❌ Token validation failed for user: {}", username);
//                return;
//            }
//
//            // Create authentication token
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                    userDetails,
//                    null,
//                    userDetails.getAuthorities()
//            );
//
//            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//            // Set authentication in security context
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            logger.info("✅ User authenticated: {}", username);
//
//        } catch (Exception e) {
//            logger.error("❌ Authentication failed for user {}: {}", username, e.getMessage());
//        }
//    }
//}
