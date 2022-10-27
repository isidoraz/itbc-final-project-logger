package com.itbc.logger.controller;

import com.itbc.logger.dto.LogDto;
import com.itbc.logger.model.Client;
import com.itbc.logger.model.Log;
import com.itbc.logger.model.LogType;
import com.itbc.logger.service.ClientService;
import com.itbc.logger.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("api/logs")
public class LogController {

    @Autowired
    ClientService clientService;

    @Autowired
    LogService logService;

    @PostMapping("/create")
    public ResponseEntity<LogDto> create(@RequestHeader("Authorization") String token, @Valid @RequestBody LogDto logDto) {
        Optional<Client> optionalClient = clientService.findByUsername(token);
        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (logDto.getMessage().length() >= 1024) {
            return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
        }

        Log log = new Log();
        log.setMessage(logDto.getMessage());
        log.setClient(optionalClient.get());
        log.setCreatedDate(LocalDateTime.now());

        if (logDto.getLogType() == 0) {
            log.setLogType(LogType.ERROR);
        }
        if (logDto.getLogType() == 1) {
            log.setLogType(LogType.WARNING);
        }
        if (logDto.getLogType() == 2) {
            log.setLogType(LogType.INFO);
        }

        logService.save(log);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
