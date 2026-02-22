package com.lazy.authserver.repository;

import com.lazy.authserver.entity.Authorizations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorizationRepository extends JpaRepository<Authorizations, String> {

	Optional<Authorizations> findByState(String state);
	Optional<Authorizations> findByAuthorizationCodeValue(String authorizationCode);
	Optional<Authorizations> findByAccessTokenValue(String accessToken);
	Optional<Authorizations> findByRefreshTokenValue(String refreshToken);
	@Query("select a from Authorizations a where a.state = :token" +
			" or a.authorizationCodeValue = :token" +
			" or a.accessTokenValue = :token" +
			" or a.refreshTokenValue = :token"
	)
	Optional<Authorizations> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValue(@Param("token") String token);
}
