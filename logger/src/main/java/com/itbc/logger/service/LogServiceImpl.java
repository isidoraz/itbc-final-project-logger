package com.itbc.logger.service;

import com.itbc.logger.model.Log;
import com.itbc.logger.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Override
    public Log save(Log log) {
        return logRepository.save(log);
    }
}
