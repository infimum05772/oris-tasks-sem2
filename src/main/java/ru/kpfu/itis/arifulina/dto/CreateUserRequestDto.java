package ru.kpfu.itis.arifulina.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kpfu.itis.arifulina.base.Constants;
import ru.kpfu.itis.arifulina.base.Messages;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CreateUserRequestDto {
    @NotBlank(message = Messages.BLANK_NAME_MSG)
    private String name;

    @NotBlank(message = Messages.BLANK_EMAIL_MSG)
    @Email(message = Messages.INVALID_MSG)
    private String email;

    @Size(
            min = Constants.PASSWORD_MIN_LENGTH,
            max = Constants.PASSWORD_MAX_LENGTH,
            message = Messages.PASSWORD_SIZE_MSG
    )
    private String password;
}
