package com.lazy.authserver.service.client;

import com.lazy.authserver.entity.oauth.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientService  {
    Client findByClientId(String clientId);
}
