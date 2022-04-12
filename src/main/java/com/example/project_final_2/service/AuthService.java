package com.example.project_final_2.service;

import com.example.project_final_2.dto.request.UserRequestDTO;
import com.example.project_final_2.exception.InvalidInputDataException;

public interface AuthService {
    void signUp(UserRequestDTO userRequestDTO) throws InvalidInputDataException;
}
