package com.itbc.logger.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientSearchDto {

    private Long id;
    private String username;
    private String email;
    private int logCount;
}
