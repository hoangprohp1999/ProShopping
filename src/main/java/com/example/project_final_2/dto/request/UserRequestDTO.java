package com.example.project_final_2.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
public class UserRequestDTO {
    private Long id;

    @NotBlank
    private String fullName;

    @NotNull
    private Date dOB;

    @NotNull
    private String gender;

    @NotEmpty
    @Pattern(regexp = "^[0]\\d{8}|\\d{11}$", message = "Identity Card: must contains 9-12 number")
    private String identityCard;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;

    @NotEmpty
    @Pattern(regexp = "^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$", message = "Phone number: Invalid")
    private String phoneNumber;

    @NotNull
    @Size(min = 8, max = 50)
    private String password;
}
