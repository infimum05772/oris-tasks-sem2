package ru.kpfu.itis.arifulina.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDto {
    private String text;
    private String sender;
}
