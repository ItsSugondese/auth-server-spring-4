package com.lazy.authserver.repository.oauth;

import com.lazy.authserver.entity.oauth.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {

	Optional<Client> findByClientId(String clientId);
}
