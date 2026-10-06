package com.Lucifer.StudentProject.exception;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        LocalDateTime timestamp,
        int status,
        String error,
        Object message,
        String path
) {
}
