package com.example.ProjectV1.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class  AuthenticationResponse {
    private String token;
}
