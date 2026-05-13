package com.openclassrooms.etudiant.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentDTO extends StudentListDTO {
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
