package com.lazy.authserver.service.client;

import com.lazy.authserver.config.CustomMessageSource;
import com.lazy.authserver.constant.ModuleNameConstants;
import com.lazy.authserver.entity.oauth.Client;
import com.lazy.authserver.enums.Message;
import com.lazy.authserver.exception.AppException;
import com.lazy.authserver.repository.oauth.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final CustomMessageSource customMessageSource;

    @Override
    public Client findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId).orElseThrow(() -> new AppException(customMessageSource.get(Message.ID_NOT_FOUND.getCode(), ModuleNameConstants.CLIENT)));
    }
}
