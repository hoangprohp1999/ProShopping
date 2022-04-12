package com.example.project_final_2.dto.reponse;

import com.example.project_final_2.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO {

    private String to;

    private String subject = "Verify email";

    private String text;

    public MailDTO(User user){
        this.to = user.getEmail();
    }
}
