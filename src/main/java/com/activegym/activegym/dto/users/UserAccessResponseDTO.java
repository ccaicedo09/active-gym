package com.activegym.activegym.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAccessResponseDTO {
    private Long id;
    private String accessDateTime;
    private Boolean success;
    private String userDocument;
}
