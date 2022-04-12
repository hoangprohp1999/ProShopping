package com.example.project_final_2.dto.reponse;

import com.example.project_final_2.entity.user.URole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleDTOResponse {
    private Long roleId;
    private URole name;
}
