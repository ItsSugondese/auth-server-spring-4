//package com.lazy.authserver.config.jwt;
//
//import com.lazy.authserver.entity.users.AppUser;
//import com.lazy.authserver.enums.user.UserType;
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.*;
//import java.util.function.Function;
//
//@Component
//@Slf4j
//public class JwtHelper {
//
//	/*
//
//
//	1. User makes API request (e.g., GET /api/v1/shipping-address/get-all)
//   ↓
//2. Access token expired → Backend returns 401
//   ↓
//3. Axios interceptor catches 401 error
//   ↓
//4. Check if already refreshing?
//   ├─ YES → Queue this request
//   └─ NO → Start refresh
//   ↓
//5. Call POST /api/v1/auth/refresh with empty body
//   ↓
//6. Backend extracts refresh token from HTTP-only cookie
//   ↓
//7. Validate refresh token
//   ├─ VALID → Generate new access token
//   │          Set new cookie
//   │          Return 200
//   └─ INVALID → Return 401
//   ↓
//8. Frontend receives response
//   ├─ SUCCESS → Retry original request
//   │           Process queued requests
//   └─ FAILURE → Clear user data
//                Redirect to /account
//
//	 */
//
//	@Value("${jwt.secret}")
//	private String secretKey;
////900000
////	@Value("${jwt.access-token.expiration:900000}") // 15 minutes
//	private long accessTokenExpiration = 9000000;
//
//	@Value("${jwt.refresh-token.expiration:2592000000}") // 30 days
//	private long refreshTokenExpiration;
//
//	private static final String ACCESS_TOKEN_COOKIE = "access_token";
//	private static final String REFRESH_TOKEN_COOKIE = "refresh_token";
//	private final String USER_ID_KEY = "appUserId";
//	private final String USER_TYPE_KEY = "userType";
//
//	public enum TokenType {
//		ACCESS, REFRESH
//	}
//
//	// ========== Access Token Generation ==========
//
//	public String generateAccessToken(AppUser appUser) {
//		Map<String, Object> claims = buildAccessTokenClaims(appUser, null);
//		return createToken(claims, appUser.getEmail(), accessTokenExpiration, TokenType.ACCESS);
//	}
//
//	public String generateAccessTokenWithSystemUser(AppUser appUser, Long systemUserId) {
//		Map<String, Object> claims = buildAccessTokenClaims(appUser, systemUserId);
//		return createToken(claims, appUser.getEmail(), accessTokenExpiration, TokenType.ACCESS);
//	}
//
//	private Map<String, Object> buildAccessTokenClaims(AppUser appUser, Long systemUserId) {
//		Map<String, Object> claims = new HashMap<>();
//		claims.put(USER_ID_KEY, appUser.getId());
//		claims.put("email", appUser.getEmail());
//		claims.put("tokenType", TokenType.ACCESS.name());
//		claims.put(USER_TYPE_KEY, appUser.getUserType());
//
//		// Add role
//		if (appUser.getRole() != null) {
//			claims.put("role", appUser.getRole());
//		}
//
//		// Add system user ID if provided
//		if (systemUserId != null) {
//			claims.put("systemUserId", systemUserId);
//		}
//
//		// Add JTI (JWT ID) for token revocation
//		claims.put("jti", UUID.randomUUID().toString());
//
//		return claims;
//	}
//
//	// ========== Refresh Token Generation ==========
//
//	public String generateRefreshToken(AppUser appUser, String tokenId) {
//		Map<String, Object> claims = new HashMap<>();
//		claims.put(USER_ID_KEY, appUser.getId());
//		claims.put(USER_TYPE_KEY, appUser.getUserType());
//		claims.put("email", appUser.getEmail());
//		claims.put("tokenType", TokenType.REFRESH.name());
//		claims.put("jti", tokenId);
//
//		return createToken(claims, appUser.getEmail(), refreshTokenExpiration, TokenType.REFRESH);
//	}
//
//	// ========== Token Creation ==========
//
//	private String createToken(Map<String, Object> claims, String subject, long expiration, TokenType tokenType) {
//		long nowMillis = System.currentTimeMillis();
//		Date now = new Date(nowMillis);
//		Date exp = new Date(nowMillis + expiration);
//
//		return Jwts.builder()
//				.setClaims(claims)
//				.setSubject(subject)
//				.setIssuedAt(now)
//				.setExpiration(exp)
//				.signWith(getSignKey(), SignatureAlgorithm.HS256)
//				.compact();
//	}
//
//	// ========== Token Validation ==========
//
//	public Boolean validateToken(String token, UserDetails userDetails) {
//		try {
//			final String username = extractUsername(token);
//			return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//		}
//
//		catch (ExpiredJwtException e) {
//			log.debug("Token expired: {}", e.getMessage());
//			return false;
//		}
//		catch (JwtException | IllegalArgumentException e) {
//			log.error("Token validation failed: {}", e.getMessage());
//			return false;
//		}
//	}
//
//	public Boolean validateToken(String token) {
//		try {
//			Jwts.parserBuilder()
//					.setSigningKey(getSignKey())
//					.build()
//					.parseClaimsJws(token);
//			return true;
//		} catch (JwtException | IllegalArgumentException e) {
//			log.error("Token validation failed: {}", e.getMessage());
//			return false;
//		}
//	}
//
//	public Boolean validateRefreshToken(String token) {
//		try {
//			Claims claims = extractAllClaims(token);
//			String tokenType = claims.get("tokenType", String.class);
//			return TokenType.REFRESH.name().equals(tokenType) && !isTokenExpired(token);
//		} catch (JwtException e) {
//			log.error("Refresh token validation failed: {}", e.getMessage());
//			return false;
//		}
//	}
//
//	// ========== Token Extraction ==========
//
//	public String extractUsername(String token) {
//		return extractClaim(token, Claims::getSubject);
//	}
//
//	public Long extractAppUserId(String token) {
//		return extractClaim(token, claims -> claims.get(USER_ID_KEY, Long.class));
//	}
//
//	public UserType extractAppUserType(String token) {
//		return UserType.valueOf(extractClaim(token, claims -> claims.get(USER_TYPE_KEY, String.class)));
//	}
//
//	public String extractTokenId(String token) {
//		return extractClaim(token, claims -> claims.get("jti", String.class));
//	}
//
//	public String extractRole(String token) {
//		return extractClaim(token, claims -> claims.get("role", String.class));
//	}
//
//	public Date extractExpiration(String token) {
//		return extractClaim(token, Claims::getExpiration);
//	}
//
//	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = extractAllClaims(token);
//		return claimsResolver.apply(claims);
//	}
//
//	private Claims extractAllClaims(String token) {
//		return Jwts.parserBuilder()
//				.setSigningKey(getSignKey())
//				.build()
//				.parseClaimsJws(token)
//				.getBody();
//	}
//
//	private Boolean isTokenExpired(String token) {
//		try {
//			final Date expiration = extractExpiration(token);
//			return expiration != null && expiration.before(new Date());
//		} catch (JwtException e) {
//			return true;
//		}
//	}
//
//	// ========== Cookie Handling ==========
//
//	public Optional<String> extractAccessTokenFromCookies(HttpServletRequest request) {
//		return extractTokenFromCookie(request, ACCESS_TOKEN_COOKIE);
//	}
//
//	public Optional<String> extractRefreshTokenFromCookies(HttpServletRequest request) {
//		return extractTokenFromCookie(request, REFRESH_TOKEN_COOKIE);
//	}
//
//	private Optional<String> extractTokenFromCookie(HttpServletRequest request, String cookieName) {
//		if (request.getCookies() == null) {
//			return Optional.empty();
//		}
//
//		return Arrays.stream(request.getCookies())
//				.filter(cookie -> cookieName.equals(cookie.getName()))
//				.findFirst()
//				.map(Cookie::getValue);
//	}
//
//	// ========== Claim Extraction from Request ==========
//
//	public Optional<Long> getAppUserIdFromRequest(HttpServletRequest request) {
//		return extractAccessTokenFromCookies(request)
//				.map(this::extractAppUserId);
//	}
//
//	public Optional<Long> getSystemUserIdFromRequest(HttpServletRequest request) {
//		return extractAccessTokenFromCookies(request)
//				.map(token -> {
//					try {
//						Claims claims = extractAllClaims(token);
//						return claims.get("systemUserId", Long.class);
//					} catch (JwtException e) {
//						return null;
//					}
//				});
//	}
//
//	public Optional<String> getRoleFromRequest(HttpServletRequest request) {
//		return extractAccessTokenFromCookies(request)
//				.map(this::extractRole);
//	}
//
//	// ========== Utility Methods ==========
//
//	private Key getSignKey() {
//		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//		return Keys.hmacShaKeyFor(keyBytes);
//	}
//
//	public long getAccessTokenExpiration() {
//		return accessTokenExpiration;
//	}
//
//	public long getRefreshTokenExpiration() {
//		return refreshTokenExpiration;
//	}
//
//	public long getAccessTokenExpirationInSeconds() {
//		return accessTokenExpiration / 1000;
//	}
//
//	/**
//	 * Extract username from token
//	 * Alias for extractUsername for WebSocket compatibility
//	 */
//	public String getUsernameFromToken(String token) {
//		return extractUsername(token);
//	}
//
//	public Long getAppUserIdFromToken(String token) {
//		return extractAppUserId(token);
//	}
//
//
//}