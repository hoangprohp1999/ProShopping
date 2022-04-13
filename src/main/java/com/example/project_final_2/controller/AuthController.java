package com.example.project_final_2.controller;

import com.example.project_final_2.dto.reponse.SuccessResponse;
import com.example.project_final_2.dto.request.UserRequestDTO;
import com.example.project_final_2.exception.InvalidInputDataException;
import com.example.project_final_2.exception.InvalidTokenException;
import com.example.project_final_2.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody UserRequestDTO userRequestDTO) throws InvalidInputDataException {
        authService.signUp(userRequestDTO);
        return new ResponseEntity(new SuccessResponse("Register successfully! Check email to verify your account."), HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token,
                                      @RequestParam String otp) throws InvalidTokenException {
        authService.verifiedEmail(token,otp);
        return new ResponseEntity(new SuccessResponse("Verify successfully!"), HttpStatus.OK);
    }

    @PostMapping("/otp")
    public ResponseEntity sendOTP(@RequestParam String username) {
        authService.sendOTP(username);
        return new ResponseEntity(new SuccessResponse("Verify successfully!"), HttpStatus.OK);
    }
}
