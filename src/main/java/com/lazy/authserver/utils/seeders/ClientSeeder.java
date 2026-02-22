package com.lazy.authserver.utils.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientSeeder implements CommandLineRunner {

    private final RegisteredClientRepository registeredClientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        RegisteredClient adminClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("admin-client")
                .clientSecret(passwordEncoder.encode("{noop}admin-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("client_registration")
                .build();

        if(registeredClientRepository.findByClientId(adminClient.getClientId()) == null) {
            registeredClientRepository.save(adminClient);
        }
    }
}
