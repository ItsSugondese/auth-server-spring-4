package com.lazy.authserver.config.security.oauth.grantpassword;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.io.Serial;
import java.util.Map;
import java.util.Set;

import static com.lazy.authserver.config.security.oauth.grantpassword.OAuth2PasswordGrantAuthenticationConverter.PASSWORD_GRANT_TYPE;

/**
 * Authentication token for the OAuth 2.0 Resource Owner Password Credentials Grant.
 *
 * @author Attoumane AHAMADI
 */
public class OAuth2PasswordGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
    @Serial
    private static final long serialVersionUID = 7840626509676504832L;
    private final String username;
    private final String password;
    private final String clientId;
    private final Set<String> scopes;
    private final Map<String, Object> additionalParameters;


    public OAuth2PasswordGrantAuthenticationToken(String username, String password, Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(PASSWORD_GRANT_TYPE, clientPrincipal, additionalParameters);
        this.password = password;
        this.username = username;
        this.clientId = clientPrincipal.getName();
        this.scopes = scopes;
        this.additionalParameters = additionalParameters;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getClientId() {
        return clientId;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    @Override
    public Map<String, Object> getAdditionalParameters() {
        return additionalParameters;
    }
}