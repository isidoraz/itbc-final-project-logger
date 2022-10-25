package com.itbc.logger.service;

import com.itbc.logger.model.Client;
import com.itbc.logger.model.Login;
import com.itbc.logger.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public void registerClient(Client client) {
        clientRepository.save(client);
    }

    public List<Client> findAll(){
        var clients = clientRepository.findAll();
        List<Client> clientsList = StreamSupport
                .stream(clients.spliterator(), false)
                .collect(Collectors.toList());
        return clientsList;
    }

    public void login(Login login) {

    }
}
