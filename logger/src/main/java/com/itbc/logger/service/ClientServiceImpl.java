package com.itbc.logger.service;

import com.itbc.logger.model.Client;
import com.itbc.logger.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> findByIsAdmin(boolean isAdmin) {
        return clientRepository.findByIsAdmin(isAdmin);
    }
}
