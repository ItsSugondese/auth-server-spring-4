package com.lazy.authserver.entity.user;

import com.lazy.authserver.entity.oauth.Authorities;
import com.lazy.authserver.generic.api.AuditActiveAbstract;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Author: Gaurav Basyal
 */
@Builder
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditActiveAbstract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NonNull
	@Column(unique = true)
	private String username;

	@NonNull
	private String password;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "users_authorities", joinColumns = {
			@JoinColumn(name = "USERS_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "AUTHORITIES_ID", referencedColumnName = "ID") })
	private Set<Authorities> authorities;
	
	private Boolean accountNonExpired;
	private Boolean accountNonLocked;
	private Boolean credentialsNonExpired;
	private Boolean enabled;

	private LocalDateTime lastPasswordChangedAt = LocalDateTime.now();


}
