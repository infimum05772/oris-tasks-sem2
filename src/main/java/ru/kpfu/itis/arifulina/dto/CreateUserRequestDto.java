package ru.kpfu.itis.arifulina.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CreateUserRequestDto {
    @NotBlank(message = "name shouldn't be blank")
    private String name;

    @NotBlank(message = "email shouldn't be blank")
    @Email(message = "invalid")
    private String email;

    @Size(min = 8, max = 64, message = "8-64 length")
    private String password;
}
