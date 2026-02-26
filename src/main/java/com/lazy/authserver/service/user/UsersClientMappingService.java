package com.lazy.authserver.service.user;

import com.lazy.authserver.dto.user.UserDetailsResponse;
import com.lazy.authserver.dto.user.UserEmailDetailsRequest;
import com.lazy.authserver.entity.user.Users;
import com.lazy.authserver.entity.user.UsersClientMapping;

public interface UsersClientMappingService {

    void saveUserClientMapping(Users users, UserEmailDetailsRequest request);

    UsersClientMapping findByClientIdAndEmail(String clientId, String email);

    UserDetailsResponse getUserDetailsByEmailAndClientId(UserEmailDetailsRequest request);
}
