package com.itbc.logger.service;

import com.itbc.logger.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    LogRepository logRepository;

}
