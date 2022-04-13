package com.example.project_final_2.service;

import com.example.project_final_2.dto.request.EmailOTPRequestDTO;
import com.example.project_final_2.dto.request.UserRequestDTO;
import com.example.project_final_2.exception.InvalidInputDataException;
import com.example.project_final_2.exception.InvalidTokenException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

public interface AuthService {
    void signUp(UserRequestDTO userRequestDTO) throws InvalidInputDataException;

    void verifiedEmail(String token,String otp1) throws InvalidTokenException;
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void SendMail(String a, String b, String c);
    void sendOTP(String username);


}
