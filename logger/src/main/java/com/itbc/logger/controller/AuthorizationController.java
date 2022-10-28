package com.itbc.logger.controller;

import com.itbc.logger.dto.*;
import com.itbc.logger.model.Client;
import com.itbc.logger.service.ClientService;
import com.itbc.logger.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class AuthorizationController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private LogService logService;


    // HTTP method for registering users
    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@Valid @RequestBody ClientDto clientDto) {
        if (clientService.findByUsername(clientDto.getUsername()).isPresent() || clientService.findByEmail(clientDto.getEmail()).isPresent()) {
            return new ResponseEntity<>("Username or email already exists", HttpStatus.CONFLICT);  // 409
        }
        // clientDto manually turn to new Client object
        Client client = new Client();
        client.setEmail(clientDto.getEmail());
        client.setUsername(clientDto.getUsername());
        client.setPassword(clientDto.getPassword());
        // admin set to false, because admin is already in DB, so everyone registered are regular users, not admin users
        client.setAdmin(false);
        clientService.save(client);
        return new ResponseEntity<>(HttpStatus.CREATED);  // 201
    }

    // HTTP method for login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        Optional<Client> clientOptional = clientService.findByUsername(loginDto.getAccount());
        if (clientOptional.isEmpty() || !clientOptional.get().getPassword().equals(loginDto.getPassword())) {
            return new ResponseEntity<>("Email/Username or password incorrect", HttpStatus.BAD_REQUEST);  // 400
        }
        // username set as token
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(clientOptional.get().getUsername());
        return new ResponseEntity<>(tokenDto, HttpStatus.CREATED); // 200
    }


    // HTTP method for getting all clients via admin token-username
    @GetMapping
    public ResponseEntity<?> getAllClients(@RequestHeader("Authorization") String token) {
        Optional<Client> clientOptional = clientService.findByUsername(token);
        if (clientOptional.isEmpty()) {
            return new ResponseEntity<>("Incorrect token", HttpStatus.FORBIDDEN);  // 403
        }
        if (!clientOptional.get().isAdmin()) {
            return new ResponseEntity<>("Correct token, but not admin", HttpStatus.UNAUTHORIZED);  // 401
        }
        List<Client> allClients = clientService.findByIsAdmin(false);
        List<ClientSearchDto> clientSearchDtos = new ArrayList<>();
        for (Client client : allClients) {
            ClientSearchDto clientSearchDto = new ClientSearchDto();
            clientSearchDto.setId(client.getId());
            clientSearchDto.setEmail(client.getEmail());
            clientSearchDto.setUsername(client.getUsername());
            clientSearchDto.setLogCount(logService.countByClient(client));
            clientSearchDtos.add(clientSearchDto);
        }
        return new ResponseEntity<>(clientSearchDtos, HttpStatus.OK); // 200
    }

    // HTTP method for changing client password
    @PatchMapping("/{clientId}/reset-password")
    public ResponseEntity changePassword(@RequestHeader("Authorization") String token,
                                         @PathVariable long clientId,
                                         @Valid @RequestBody PasswordChangeDto passwordChangeDto) {
        Optional<Client> optionalClient = clientService.findByUsername(token);
        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>("Incorrect token", HttpStatus.FORBIDDEN);  // 403
        }
        if (!optionalClient.get().isAdmin()) {
            return new ResponseEntity<>("Correct token, but not admin", HttpStatus.UNAUTHORIZED);  // 401
        }
        Optional<Client> optionalClientPassChange = clientService.findById(clientId);
        optionalClientPassChange.get().setPassword(passwordChangeDto.getPassword());
        clientService.save(optionalClientPassChange.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204
    }



}
