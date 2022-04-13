package com.example.project_final_2.service;

import com.example.project_final_2.dto.reponse.UserResponseDTO;
import com.example.project_final_2.dto.request.UserRequestDTO;
import com.example.project_final_2.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponseDTO> getAllUser(int pageIndex, int pageSize);

    Optional<User> findUserByID(Long id);

    UserResponseDTO getUserDTOByID(String username);

    UserResponseDTO getUserDTOByUsername(String username);

    List<UserResponseDTO> getListUserByFilter(UserRequestDTO filter, int page, int limit);

    void saveNewUser(UserRequestDTO userRequestDTO);

    void editUserInfo(String username, UserRequestDTO userRequestDTO) throws Exception;

    void deleteUserByID(Long id);

    User checkExistsUserByUsername(String username);

}
