package com.itbc.logger.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "logId")
    private Long logId;
    private String message;
    private LogType logType;
    private LocalDateTime date;
}
