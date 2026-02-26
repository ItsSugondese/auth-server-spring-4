package com.lazy.authserver.repository.passwordReset;

import com.lazy.authserver.entity.auth.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

    @Query(value = "select *,to_char(expiry_date,'yyyy-MM-dd hh:mm:ss') as expiry_date from password_reset_token where user_id=?1 and token=?2", nativeQuery = true)
    Optional<PasswordResetToken> findByEmailAndToken(Long id, String resetToken);

    @Query(value = "select * from password_reset_token prt" +
            " where prt.token=?1 and prt.is_active=true and prt.status=1 and prt.expiry_date>=current_timestamp",
            nativeQuery = true)
    Optional<PasswordResetToken> findByToken(String resetToken);

    @Modifying
    @Query(value = "update password_reset_token set is_active=false ,status=0 where token=?", nativeQuery = true)
    Integer updateTokenIsActive(String resetToken);

    void deleteByToken(String resetToken);
}
