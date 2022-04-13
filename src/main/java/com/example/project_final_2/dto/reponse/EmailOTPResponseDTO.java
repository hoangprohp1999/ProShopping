package com.example.project_final_2.dto.reponse;

import com.example.project_final_2.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailOTPResponseDTO {
    private Long id;
    private Long userId;
    private int otpCode;
    private Instant expiryTime;
}
