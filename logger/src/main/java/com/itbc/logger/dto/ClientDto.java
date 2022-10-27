package com.itbc.logger.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
public class ClientDto {

    @Email
    private String email;
    @Length(min = 3, message = "Username must be at least 3 characters long")
    private String username;
    @Length(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
