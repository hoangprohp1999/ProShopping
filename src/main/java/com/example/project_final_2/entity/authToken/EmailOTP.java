package com.example.project_final_2.entity.authToken;

import com.example.project_final_2.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Random;

@Entity
@Table(name = "email_otp")
@Setter
@Getter
@NoArgsConstructor
public class EmailOTP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "userId")
    private User user;

    @Column(name = "otp_code")
    private String otpCode;

    public EmailOTP(User user, String otpCode) {
        this.id = user.getId();
        this.otpCode = otpCode;
    }

}

