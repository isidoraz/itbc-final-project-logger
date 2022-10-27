package com.itbc.logger.model;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;
    private String message;
    private LogType logType;
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
