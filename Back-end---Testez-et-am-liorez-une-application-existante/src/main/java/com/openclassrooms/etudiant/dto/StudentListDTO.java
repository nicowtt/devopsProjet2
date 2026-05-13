package com.openclassrooms.etudiant.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StudentListDTO {
    private UUID uuid;
    private String firstName;
    private String lastName;
}
