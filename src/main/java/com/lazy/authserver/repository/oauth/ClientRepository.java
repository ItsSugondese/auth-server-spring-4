package com.lazy.authserver.repository.oauth;

import com.lazy.authserver.entity.oauth.Client;
import com.lazy.authserver.generic.api.GenericSoftDeleteRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends GenericSoftDeleteRepository<Client, String> {

	Optional<Client> findByClientId(String clientId);

	boolean existsByClientId(String clientId);
}
