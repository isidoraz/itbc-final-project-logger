package com.itbc.logger.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LogDto {

    private String message;
    @Range(min = 0, max = 2)
    private int logType;
    private LocalDateTime createdDate;
}
