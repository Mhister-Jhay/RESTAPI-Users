package com.users.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionResponse {
    private LocalDateTime errorTime;
    private String errorPath;
    private String errorMessage;
    private Integer statusCode;
}
