package com.lazy.authserver.repository.user;

import com.lazy.authserver.entity.user.Users;
import com.lazy.authserver.generic.api.GenericSoftDeleteRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends GenericSoftDeleteRepository<Users, Integer> {


	Optional<Users> findByUsername(String username);

	@Query(value = "select * from users u where u.password is null and u.email_address=?1", nativeQuery = true)
	Optional<Users> findByEmailAddressAndPasswordNull(String userEmail);
}
