package com.example.project_final_2;

import com.example.project_final_2.dto.reponse.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.User;

@SpringBootApplication
public class ProjectFinal2Application {

    public static void main(String[] args) {
        SpringApplication.run(ProjectFinal2Application.class, args);
    }

}
