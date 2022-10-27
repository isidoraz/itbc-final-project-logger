package com.itbc.logger.controller;

import com.itbc.logger.dto.ClientDto;
import com.itbc.logger.dto.LoginDto;
import com.itbc.logger.dto.TokenDto;
import com.itbc.logger.model.Client;
import com.itbc.logger.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class AuthorizationController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<ClientDto> registerClient(@Valid @RequestBody ClientDto clientDto) {
        if (clientService.findByUsername(clientDto.getUsername()).isPresent() || clientService.findByEmail(clientDto.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Client client = new Client();
        client.setEmail(clientDto.getEmail());
        client.setUsername(clientDto.getUsername());
        client.setPassword(clientDto.getPassword());
        clientService.save(client);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto){
        Optional<Client> clientOptional = clientService.findByUsername(loginDto.getAccount());
        if (clientOptional.isEmpty() || !clientOptional.get().getPassword().equals(loginDto.getPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(clientOptional.get().getUsername());
        return new ResponseEntity<>(tokenDto, HttpStatus.CREATED);
    }




}
