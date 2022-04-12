package com.example.project_final_2.service;

import com.example.project_final_2.dto.reponse.UserResponseDTO;
import com.example.project_final_2.dto.request.UserRequestDTO;
import com.example.project_final_2.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<UserResponseDTO> getAllUser(int pageIndex, int pageSize);

    public Optional<User> findUserByID(Long id);

    public UserResponseDTO getUserDTOByID(String username);

    public UserResponseDTO getUserDTOByUsername(String username);

    public List<UserResponseDTO> getListUserByFilter(UserRequestDTO filter, int page, int limit);

    public void saveNewUser(UserRequestDTO userRequestDTO);

    public void editUserInfo(String username, UserRequestDTO userRequestDTO) throws Exception;

    public void deleteUserByID(Long id);

}
