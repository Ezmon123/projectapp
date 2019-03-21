package com.cezary.projectboard.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTLoginSucessResponse {
    private boolean success;
    private String token;
}
