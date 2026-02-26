package com.lazy.authserver.utils.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientSeeder implements CommandLineRunner {

    private final RegisteredClientRepository registeredClientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

//        RegisteredClient adminClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("react")
//                .clientSecret(passwordEncoder.encode("frontend"))
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .scope("client_registration")
//                .build();

        RegisteredClient registeredClient =  RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("react-client")
                .clientSecret(passwordEncoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                // If you want no secret, keep NONE; otherwise use CLIENT_SECRET_BASIC
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)

                // 1. Set ONLY the password grant type
                .authorizationGrantType(new AuthorizationGrantType("password"))

                // 2. Refresh tokens are usually helpful with password grants
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)

                .scope(OidcScopes.OPENID)
                .scope("profile")
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(true) // enforces PKCE
                        .build())
                .build();

        if(registeredClientRepository.findByClientId(registeredClient.getClientId()) == null) {
            registeredClientRepository.save(registeredClient);
        }


    }
}
