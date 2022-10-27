package com.itbc.logger.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
public class PasswordChangeDto {

    @Length(min = 8, message = "Password must be at least 8 characters long")
    private String password;

}
