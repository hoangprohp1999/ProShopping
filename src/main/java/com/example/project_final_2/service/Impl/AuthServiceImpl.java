package com.example.project_final_2.service.Impl;

import com.example.project_final_2.dto.request.UserRequestDTO;
import com.example.project_final_2.entity.user.User;
import com.example.project_final_2.exception.InvalidInputDataException;
import com.example.project_final_2.repository.UserRepository;
import com.example.project_final_2.service.AuthService;
import com.example.project_final_2.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;

    private final UserRepository userRepository;

    public AuthServiceImpl(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void signUp(UserRequestDTO userRequestDTO) throws InvalidInputDataException {
        User user = userRepository.findUserByEmail(userRequestDTO.getEmail());
        if (user == null) {
            userService.saveNewUser(userRequestDTO);
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("email", "is taken");
            throw new InvalidInputDataException(errors);
        }
    }


}
