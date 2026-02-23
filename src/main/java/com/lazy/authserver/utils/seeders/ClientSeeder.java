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

        RegisteredClient registeredClient =
                RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("react-client")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE) // public client
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .redirectUri("http://localhost:3000/callback")
                        .scope(OidcScopes.OPENID)
                        .scope("profile")
                        .clientSettings(
                                ClientSettings.builder()
                                        .requireProofKey(true)  // ✅ THIS is correct in older versions
                                        .requireAuthorizationConsent(false)
                                        .build()
                        )  // 🔥 THIS enables PKCE requirement
                        .build();

        if(registeredClientRepository.findByClientId(registeredClient.getClientId()) == null) {
            registeredClientRepository.save(registeredClient);
        }
    }
}
