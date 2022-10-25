package com.itbc.logger.controller;

import com.itbc.logger.model.Client;
import com.itbc.logger.model.Login;
import com.itbc.logger.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class ClientController {

    private ClientService clientService;

    @PostMapping("/api/clients/register")
    public ResponseEntity<Void> registerClient(@RequestBody Client client){
        clientService.registerClient(client);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/api/clients/login")
    public ResponseEntity<Void> login(@RequestBody Login login){
        clientService.login(login);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/api/clients")
    public ResponseEntity<List> getAllClients(){
        var clientsList = clientService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(clientsList);
    }



}
