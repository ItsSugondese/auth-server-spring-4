package com.lazy.authserver.entity.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lazy.authserver.entity.user.UsersClientMapping;
import com.lazy.authserver.generic.api.AuditActiveAbstract;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PasswordResetToken extends AuditActiveAbstract {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @SequenceGenerator(name = "password_reset_token_seq", sequenceName = "password_reset_token_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_reset_token_seq")
    private Long id;
    @Column(length = 40)
    private String token;
    private LocalDateTime expiryDate;
    @Column(length = 2)
    private Integer status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_client_mapping_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_password_reset_token_user"))
    @JsonIgnore
    private UsersClientMapping usersClientMapping;
}