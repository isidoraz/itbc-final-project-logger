package com.itbc.logger.repository;

import com.itbc.logger.model.Client;
import com.itbc.logger.model.Log;
import com.itbc.logger.model.LogType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    int countByClient(Client client);

    @Query("SELECT l FROM Log l WHERE l.client.username = :username AND " +
            "(:dateFrom IS null OR l.createdDate >= :dateFrom) AND " +
            "(:dateFrom IS null OR l.createdDate <= :dateTo) AND" +
            "(:dateFrom IS null OR l.createdDate <= :dateTo) AND " +
            "(:message IS null OR l.message like %:message%) AND " +
            "(:logType IS null OR l.logType = :logType)")
    List<Log> search(String username, LocalDateTime dateFrom, LocalDateTime dateTo, String message, LogType logType);
}
