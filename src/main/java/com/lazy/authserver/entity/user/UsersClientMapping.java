package com.lazy.authserver.entity.user;

import com.lazy.authserver.entity.oauth.Authorities;
import com.lazy.authserver.generic.api.AuditActiveAbstract;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users_client_mapping")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UsersClientMapping extends AuditActiveAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clientId;

    private String password;


    @Builder.Default
    private boolean accountNonExpired = true;
    @Builder.Default
    private boolean credentialsNonExpired = true;
    @Builder.Default
    private boolean accountNonLocked = true;


    private LocalDateTime lastPasswordChangedAt = LocalDateTime.now();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "users_client_mapping_authorities", joinColumns = {
            @JoinColumn(name = "USERS_CIENT_MAPPING_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
            @JoinColumn(name = "AUTHORITIES_ID", referencedColumnName = "ID") })
    private Set<Authorities> authorities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_user_client_mapping_user"))
    private Users users;

}
