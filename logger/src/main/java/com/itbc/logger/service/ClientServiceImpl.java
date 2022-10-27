package com.itbc.logger.service;

import com.itbc.logger.model.Client;
import com.itbc.logger.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Override
    public Optional<Client> findByUsername(String username) {
        return clientRepository.findByUsername(username);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }
}
