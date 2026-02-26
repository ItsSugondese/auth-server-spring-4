package com.lazy.authserver.entity.user;

import com.lazy.authserver.entity.oauth.Authorities;
import com.lazy.authserver.generic.api.AuditActiveAbstract;
import jakarta.persistence.*;
import lombok.*;

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
public class Users extends AuditActiveAbstract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;

	@Column(unique = true)
	private String email;


}
