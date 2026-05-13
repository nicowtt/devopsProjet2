package com.openclassrooms.etudiant.service;

import com.openclassrooms.etudiant.dto.StudentDTO;
import com.openclassrooms.etudiant.dto.StudentListDTO;
import com.openclassrooms.etudiant.entities.Student;
import com.openclassrooms.etudiant.exception.ResourceNotFoundException;
import com.openclassrooms.etudiant.handler.RestExceptionHandler;
import com.openclassrooms.etudiant.mapper.StudentDTOMapper;
import com.openclassrooms.etudiant.mapper.StudentListDTOMapper;
import com.openclassrooms.etudiant.repository.StudentRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentListDTOMapper studentListDTOMapper;
    private final StudentDTOMapper studentDTOMapper;

    public Student create(@NotNull Student student) {
        log.info("Create new Student");

        Optional<Student> optionalStudent = studentRepository.findByEmail(student.getEmail());
        if (optionalStudent.isPresent()) {
            throw new IllegalArgumentException("Student with email " + student.getEmail() + " already exists");
        }
        Student studentBdd = studentRepository.save(student);
        log.info("New Student with email: {} created.", student.getEmail());
        return studentBdd;
    }

    public Student update(
        @NotNull Student student,
        @NotNull UUID uuid
    ) {
        // check if student exist
        Student studentBdd = studentRepository.findByUuid(uuid)
            .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        // check if email already exist
        Optional<Student> optionalStudent = studentRepository.findByEmail(student.getEmail());
        if (optionalStudent.isPresent() && !optionalStudent.get().getUuid().equals(uuid)) {
            throw new IllegalArgumentException("Email already in use");
        }

        // update
        studentBdd.setFirstName(student.getFirstName());
        studentBdd.setLastName(student.getLastName());
        studentBdd.setEmail(student.getEmail());
        Student updatedStudent = studentRepository.save(studentBdd);
        log.info("Student with id: {} updated.", updatedStudent.getId());
        return updatedStudent;
    }

    public List<StudentListDTO> students() {
        return studentRepository.findAll()
            .stream()
            .map(studentListDTOMapper::toDTO)
            .toList();
    }

    public StudentDTO student(@NotNull UUID studentUuid) {
        Student student = studentRepository.findByUuid(studentUuid)
            .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return studentDTOMapper.toDTO(student);
    }

    public void delete(@NotNull UUID studentUuid) {
        Student student = studentRepository.findByUuid(studentUuid)
            .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        studentRepository.delete(student);
        log.info("Student with id: {} deleted.", student.getId());
    }

}
