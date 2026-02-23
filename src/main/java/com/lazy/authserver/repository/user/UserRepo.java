package com.lazy.authserver.repository.user;

import com.lazy.authserver.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer>{


	Optional<User> findByUsername(String username);

	@Query(value = "select * from users u where u.password is null and u.email_address=?1", nativeQuery = true)
	Optional<User> findByEmailAddressAndPasswordNull(String userEmail);
}
