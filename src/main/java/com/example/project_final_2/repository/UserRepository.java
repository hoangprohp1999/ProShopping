package com.example.project_final_2.repository;

import com.example.project_final_2.dto.request.UserRequestDTO;
import com.example.project_final_2.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u")
    List<User> getAllUser(Pageable pageable);
    User findUserByEmail(String username);

    Optional<User> findByEmail(String email);
    boolean existsUserByEmail(String email);

    boolean existsUserByIdentityCard(String idCard);

    boolean existsUserByPhoneNumber(String phoneNumber);


}