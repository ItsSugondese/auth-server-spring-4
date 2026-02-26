package com.lazy.authserver.mapper.user;

import com.lazy.authserver.dto.user.UserDetailsResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsersClientMappingMapper {

    @Select("""
            select case when ucm."password" is not null then true else false end as "hasPassword",
                    ucm.client_id as "clientId", u.email from users_client_mapping ucm
            join users u on u.id = ucm.users_id
            where ucm.client_id  = #{clientId}
            and u.email = #{email}
            """)
    UserDetailsResponse getUserDetailsByEmailAndClientId(@Param("email") String email, @Param("clientId") String clientId);
}
