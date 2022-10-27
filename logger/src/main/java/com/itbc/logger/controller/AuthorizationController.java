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

    @PostMapping("/register")
    public ResponseEntity<ClientDto> registerClient(@Valid @RequestBody ClientDto clientDto) {
        if (clientService.findByUsername(clientDto.getUsername()).isPresent() || clientService.findByEmail(clientDto.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Client client = new Client();
        client.setEmail(clientDto.getEmail());
        client.setUsername(clientDto.getUsername());
        client.setPassword(clientDto.getPassword());
        client.setAdmin(false);
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

    @GetMapping
    public ResponseEntity<List<ClientSearchDto>> getAllClients(@RequestHeader("Authorization") String token) {
        Optional<Client> clientOptional = clientService.findByUsername(token);
        if (clientOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (!clientOptional.get().isAdmin()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Client> allClients = clientService.findAll();
        List<ClientSearchDto> clientSearchDtos = new ArrayList<>();
        for (Client client : allClients) {
            ClientSearchDto clientSearchDto = new ClientSearchDto();
            clientSearchDto.setId(client.getId());
            clientSearchDto.setEmail(client.getEmail());
            clientSearchDto.setUsername(client.getUsername());
            clientSearchDto.setLogCount(logService.countByClient(client));
        }
        return new ResponseEntity<>(clientSearchDtos, HttpStatus.OK);
    }

    @PatchMapping("/{clientId}/reset-password")
    public ResponseEntity changePassword(@RequestHeader("Authorization") String token,
                                         @PathVariable long clientId,
                                         @Valid @RequestBody PasswordChangeDto passwordChangeDto) {
        Optional<Client> optionalClient = clientService.findByUsername(token);
        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (!optionalClient.get().isAdmin()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Optional<Client> optionalClientPassChange = clientService.findById(clientId);
        optionalClientPassChange.get().setPassword(passwordChangeDto.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
