package com.taskmanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor    // FIX: Added — required by Jackson
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String name;
    private String email;
}
