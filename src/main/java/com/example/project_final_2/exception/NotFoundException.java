package com.example.project_final_2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}
