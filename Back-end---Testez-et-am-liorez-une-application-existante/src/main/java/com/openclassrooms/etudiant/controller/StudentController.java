package com.openclassrooms.etudiant.controller;


import com.openclassrooms.etudiant.dto.StudentDTO;
import com.openclassrooms.etudiant.dto.StudentListDTO;
import com.openclassrooms.etudiant.dto.StudentSaveDTO;
import com.openclassrooms.etudiant.entities.Student;
import com.openclassrooms.etudiant.mapper.StudentSaveDTOMapper;
import com.openclassrooms.etudiant.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentSaveDTOMapper studentSaveDTOMapper;

    @PostMapping("/api/createStudent")
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentSaveDTO studentSaveDTO) {
        studentService.create(studentSaveDTOMapper.toEntity(studentSaveDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/api/updateStudent/{uuid}")
    public ResponseEntity<?> updateStudent(
        @PathVariable UUID uuid,
        @RequestBody @Valid StudentSaveDTO studentSaveDTO
    ) {
        Student student = studentSaveDTOMapper.toEntity(studentSaveDTO);
        studentService.update(student, uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/getStudentList")
    public List<StudentListDTO> getStudentsList () {
        return studentService.students();
    }

    @GetMapping("/api/getStudent/{uuid}")
    public StudentDTO getStudent(@PathVariable UUID uuid) {
        return studentService.student(uuid);
    }

    @DeleteMapping("/api/deleteStudent/{uuid}")
    public ResponseEntity<?> deleteStudent(@PathVariable UUID uuid) {
        studentService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
