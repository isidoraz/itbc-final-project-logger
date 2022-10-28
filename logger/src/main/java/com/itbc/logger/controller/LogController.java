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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/logs")
public class LogController {

    @Autowired
    ClientService clientService;

    @Autowired
    LogService logService;

    // HTTP method for creating logs
    // Authorization - token : username
    @PostMapping("/create")
    public ResponseEntity<LogDto> create(@RequestHeader("Authorization") String token, @Valid @RequestBody LogDto logDto) {
        Optional<Client> optionalClient = clientService.findByUsername(token);
        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // 401
        }
        if (logDto.getMessage().length() >= 1024) {
            return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);  // 413
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


    // HTTP method for searching logs
    @GetMapping("/search")
    public ResponseEntity<List<LogDto>> search(@RequestHeader("Authorization") String token,
                                               @RequestParam(required = false) LocalDateTime dateFrom,
                                               @RequestParam(required = false) LocalDateTime dateTo,
                                               @RequestParam(required = false) String message,
                                               @RequestParam(required = false) Integer logType) {
        LogType logTypeEnum = null;
        if (logType == 0) {
            logTypeEnum = LogType.ERROR;
        }
        if (logType == 1) {
            logTypeEnum = LogType.WARNING;
        }
        if (logType == 2) {
            logTypeEnum = LogType.INFO;
        }
        List<Log> logs = logService.search(token, dateFrom, dateTo, message, logTypeEnum);
        List<LogDto> logsDto = new ArrayList<>();
        for (Log log : logs) {
            LogDto logDto = new LogDto();
            logDto.setMessage(log.getMessage());
            logDto.setCreatedDate(log.getCreatedDate());
            if (log.getLogType() == LogType.ERROR) {
                logDto.setLogType(0);
            }
            if (log.getLogType() == LogType.WARNING) {
                logDto.setLogType(1);
            }
            if (log.getLogType() == LogType.INFO) {
                logDto.setLogType(2);
            }
        }
        return  new ResponseEntity<>(logsDto, HttpStatus.OK); // 200
    }
}
