package com.example.project_final_2.repository;

import com.example.project_final_2.entity.authToken.EmailOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EmailOTPRepository extends JpaRepository<EmailOTP,Long> {
    @Transactional
    void removeAllByUserId(Long userId);
}
