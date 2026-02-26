package com.lazy.authserver.config.security.oauth;

import com.lazy.authserver.entity.user.Users;
import com.lazy.authserver.entity.user.UsersClientMapping;
import com.lazy.authserver.exception.AppException;
import com.lazy.authserver.repository.user.UserRepo;
import com.lazy.authserver.repository.user.UsersClientMappingRepo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class JpaUserDetailsManager implements UserDetailsManager {

	private final UsersClientMappingRepo usersClientMappingRepo;
	private final HttpServletRequest request;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String clientId = request.getParameter("client_id");

		UsersClientMapping mapping =
				usersClientMappingRepo.findByClientIdAndUsersUsername(clientId, username)
						.orElseThrow(() ->
								new UsernameNotFoundException("User not found"));

		Collection<GrantedAuthority> authorities = new HashSet<>();
		mapping.getAuthorities().forEach(auth -> authorities.add(new SimpleGrantedAuthority(auth.getAuthority())));
		return new User(
				username,
				mapping.getPassword(),
				mapping.isActive(),
				mapping.isAccountNonExpired(),
				mapping.isCredentialsNonExpired(),
				mapping.isAccountNonLocked(),
				authorities
		);
	}

	@Override
	public void createUser(UserDetails user) {
	}

	@Override
	public void updateUser(UserDetails user) {
	}

	@Override
	public void deleteUser(String username) {
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
	}

	@Override
	public boolean userExists(String username) {
		String clientId = request.getParameter("client_id");

		UsersClientMapping mapping =
				usersClientMappingRepo.findByClientIdAndUsersUsername(clientId, username)
						.orElseThrow(() ->
								new UsernameNotFoundException("User not found"));

		if (mapping.getUsers().getUsername().equals(username)) {
			return true;
		}
		return false;
	}

}
