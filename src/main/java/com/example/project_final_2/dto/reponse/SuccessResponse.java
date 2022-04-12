package com.example.project_final_2.dto.reponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SuccessResponse {
    private boolean result = true;
    private Object response;

    public SuccessResponse(Object response) {
        this.response = response;
    }
}
