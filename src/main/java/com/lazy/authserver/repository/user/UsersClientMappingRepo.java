package com.lazy.authserver.repository.user;

import com.lazy.authserver.entity.user.UsersClientMapping;
import com.lazy.authserver.generic.api.GenericSoftDeleteRepository;

import java.util.Optional;

public interface UsersClientMappingRepo extends GenericSoftDeleteRepository<UsersClientMapping, Long> {

    Optional<UsersClientMapping> findByClientIdAndUsersEmail(String clientId, String email);
    Optional<UsersClientMapping> findByClientIdAndUsersUsername(String clientId, String username);
}
