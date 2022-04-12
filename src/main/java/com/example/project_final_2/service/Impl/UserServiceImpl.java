package com.example.project_final_2.service.Impl;

import com.example.project_final_2.dto.reponse.UserResponseDTO;
import com.example.project_final_2.dto.request.UserRequestDTO;
import com.example.project_final_2.entity.user.Role;
import com.example.project_final_2.entity.user.URole;
import com.example.project_final_2.entity.user.User;
import com.example.project_final_2.exception.CanNotSaveToDatabaseException;
import com.example.project_final_2.exception.DataNotFoundException;
import com.example.project_final_2.exception.DuplicateDataException;
import com.example.project_final_2.repository.RoleRepository;
import com.example.project_final_2.repository.UserRepository;
import com.example.project_final_2.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    public final UserRepository userRepository;
    public final ModelMapper modelMapper;
    private static final int USER_NOT_FOUND = -1;

    public UserServiceImpl(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserResponseDTO> getAllUser(int pageIndex, int pageSize) {
        return userRepository.getAllUser(PageRequest.of(pageIndex, pageSize)).stream().map(t -> convertToDTO(t)).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findUserByID(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserResponseDTO getUserDTOByID(String username) {
        return userRepository.findByEmail(username).map(t -> convertToDTO(t)).orElse(null);
    }


    @Override
    public List<UserResponseDTO> getListUserByFilter(UserRequestDTO filter, int page, int limit) {
        return null;
    }

    @Override
    @Transactional
    public void saveNewUser(UserRequestDTO userRequestDTO) {
        User user = modelMapper.map(userRequestDTO, User.class);

        //role user
        Role roleUser = roleRepository.findRoleByName(URole.ROLE_ADMIN.toString());
        user.setRole(new ArrayList<>() {{
            add(roleUser);
        }});
        user.setVerified(false);
        //encode password
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        userRepository.save(user);
    }

    protected String descriptionOf(String message) {
        return message;
    }



    @Override
    @Transactional
    public void editUserInfo(String username, UserRequestDTO userRequestDTO) throws Exception {
        try {
            User oldUser = userRepository.findByEmail(username).orElseThrow(()->new IllegalStateException("Empty"));

            userRequestDTO.setEmail(username);

            Map<String, String> errors = new HashMap<>();

            if (!oldUser.getEmail().equals(userRequestDTO.getEmail()) && userRepository.existsUserByEmail(userRequestDTO.getEmail()))
                errors.put("Email", "Already exist");

            if (!oldUser.getIdentityCard().equals(userRequestDTO.getIdentityCard()) && userRepository.existsUserByIdentityCard(userRequestDTO.getIdentityCard()))
                errors.put("Identity card", "Already exist");

            if (!oldUser.getPhoneNumber().equals(userRequestDTO.getPhoneNumber()) && userRepository.existsUserByPhoneNumber(userRequestDTO.getPhoneNumber()))
                errors.put("Phone number", "Already exist");

            if (!errors.isEmpty()) throw new DuplicateDataException(errors);


            modelMapper.map(userRequestDTO, oldUser);

            User user = oldUser;
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
            userRepository.save(oldUser);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CanNotSaveToDatabaseException(descriptionOf("Cannot edit user"));
        }
    }

    @Override
    public UserResponseDTO getUserDTOByUsername(String username) {
        return modelMapper.map( userRepository.findUserByEmail(username),UserResponseDTO.class);
    }


    @Override
    @Transactional
    public void deleteUserByID(Long id) {
        try {
            userRepository.deleteById(id);
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new DataNotFoundException("Not found: User "+id,USER_NOT_FOUND );
        }
    }

    public UserResponseDTO convertToDTO(User user){ return UserResponseDTO.getDTOFrom(user);}

    public List<UserResponseDTO> convertAllToDTO(List<User> users) {
        return users.stream().map(t -> convertToDTO(t)).collect(Collectors.toList());
    }
}
