package com.lazy.authserver.config.oauth;

import com.lazy.authserver.entity.user.User;
import com.lazy.authserver.exception.AppException;
import com.lazy.authserver.repository.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class JpaUserDetailsManager implements UserDetailsManager {

	private final UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username).orElseThrow(() -> new AppException(username));
		if (!user.getUsername().equals(username)) {
			throw new UsernameNotFoundException("Access Denied");
		}
		Collection<GrantedAuthority> authoriies = new HashSet<>();
		user.getAuthorities().forEach(auth -> authoriies.add(new SimpleGrantedAuthority(auth.getAuthority())));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), user.getAccountNonExpired(),
				user.getCredentialsNonExpired(), user.getAccountNonLocked(), authoriies);
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
		User user = userRepo.findByUsername(username).orElseThrow(() -> new AppException("User not found"));
		if (user.getUsername().equals(username)) {
			return true;
		}
		return false;
	}

}
