package com.example.project_final_2.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailOTPRequestDTO {
    private Long id;
    private Long userId;
    private int otpCode;
    private Instant expiryTime;
}
