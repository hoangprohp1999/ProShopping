package com.example.project_final_2.service.Impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.project_final_2.dto.request.EmailOTPRequestDTO;
import com.example.project_final_2.dto.request.UserRequestDTO;
import com.example.project_final_2.entity.authToken.EmailOTP;
import com.example.project_final_2.entity.user.User;
import com.example.project_final_2.exception.InvalidInputDataException;
import com.example.project_final_2.exception.InvalidTokenException;
import com.example.project_final_2.exception.NotFoundEntityException;
import com.example.project_final_2.exception.NotFoundException;
import com.example.project_final_2.repository.EmailOTPRepository;
import com.example.project_final_2.repository.UserRepository;
import com.example.project_final_2.security.WebSecurityConfig;
import com.example.project_final_2.service.AuthService;
import com.example.project_final_2.service.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;

    private final UserRepository userRepository;

    private final EmailOTPRepository emailOTPRepository;

//    private final EmailSendServiceImpl emailSendService;
    private final JavaMailSender javaMailSender;

    public AuthServiceImpl(UserService userService, UserRepository userRepository, EmailOTPRepository emailOTPRepository, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.emailOTPRepository = emailOTPRepository;
        this.javaMailSender = javaMailSender;
    }

    public static Random random = new Random();
//    public void RandomOTP(){
//        String otp;
//        otp = String.valueOf(random.nextInt(900000) + 100000);
//    }

    public void sendMail(String toEmail,
                         String subject,
                         String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hoangprohp1999@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        javaMailSender.send(message);

        System.out.println("Mail Sent successfully");
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

    @Override
    public void sendOTP(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundEntityException("User not found"));
        System.out.println(user);
        EmailSendServiceImpl emailSendService = new EmailSendServiceImpl();
        String otp = emailSendService.RandomOTP();
        emailOTPRepository.save(new EmailOTP(user,otp));
        sendMail(username,
                "OTP CODE",otp
        );
    }

    @Override
    public void verifiedEmail(String token,String otp1) throws InvalidTokenException {
        if (token != null) {
            //if token is valid
            try {
                //decode
                Algorithm algorithm = Algorithm.HMAC256(WebSecurityConfig.SECRET_KEY);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                //authenticate
                String username = decodedJWT.getSubject();
                //check if user is exists
                User user = userRepository.findByEmail(username)
                        .orElseThrow(() -> new NotFoundEntityException("User not found"));
                EmailOTP emailOTP = emailOTPRepository.findById(user.getId()).orElseThrow(() -> new NotFoundEntityException("Email not found"));;
                System.out.println(emailOTP.getOtpCode());
                System.out.println(otp1);
                if (otp1.equals(emailOTP.getOtpCode())){
                    user.setVerified(true);
                    userRepository.save(user);

                }
                else {
                    throw new NotFoundException("Wrong Code");
                }
                emailOTPRepository.removeAllByUserId(emailOTP.getId());
            } catch (Exception e) {
                throw new InvalidTokenException(e.getMessage());
            }
        } else {
            throw new InvalidTokenException("Token is invalid!");
        }
    }
}
