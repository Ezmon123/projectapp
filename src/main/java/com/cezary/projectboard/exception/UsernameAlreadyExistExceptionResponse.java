package com.cezary.projectboard.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsernameAlreadyExistExceptionResponse {
    private String username;
}
