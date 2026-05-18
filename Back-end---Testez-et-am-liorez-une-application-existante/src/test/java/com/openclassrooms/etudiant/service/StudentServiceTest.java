package com.openclassrooms.etudiant.service;

import com.openclassrooms.etudiant.dto.StudentDTO;
import com.openclassrooms.etudiant.dto.StudentListDTO;
import com.openclassrooms.etudiant.entities.Student;
import com.openclassrooms.etudiant.exception.ResourceNotFoundException;
import com.openclassrooms.etudiant.mapper.StudentDTOMapper;
import com.openclassrooms.etudiant.mapper.StudentListDTOMapper;
import com.openclassrooms.etudiant.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class StudentServiceTest {
    private static final String FIRST_NAME = "firstNameTest";
    private static final String LAST_NAME = "lastNameTest";
    private static final String EMAIL = "email@test";
    private static final String EMAIL_UPDATED = "emailUpdated@test";

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentListDTOMapper studentListDTOMapper;
    @Mock
    private StudentDTOMapper studentDTOMapper;
    @InjectMocks
    private StudentService studentService;

    // CREATE TEST

    @Test
    public void test_create_null_student_throws_IllegalArgumentException() {
        // GIVEN

        // THEN
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> studentService.create(null));
    }

    @Test
    public void test_create_already_email_exist_student_throws_IllegalArgumentException() {
        // GIVEN
        Student student = new Student();
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setEmail(EMAIL);
        when(studentRepository.findByEmail(any())).thenReturn(Optional.of(student));

        // THEN
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> studentService.create(student));
    }

    @Test
    public void test_create_student() {
        // GIVEN
        Student student = new Student();
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setEmail(EMAIL);

        when(studentRepository.findByEmail(any())).thenReturn(Optional.empty());

        // WHEN
        studentService.create(student);

        // THEN
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentCaptor.capture());
        assertThat(studentCaptor.getValue()).isEqualTo(student);
    }

    // UPDATE TEST

    @Test
    public void test_update_null_student_throws_IllegalArgumentException() {
        // GIVEN

        // THEN
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> studentService.update(null, UUID.randomUUID()));
    }

    @Test
    public void test_update_null_student_uuid_throws_IllegalArgumentException() {
        // GIVEN
        Student student = new Student();

        // THEN
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> studentService.update(student, null));
    }

    @Test
    public void test_update_with_uuid_not_found_throws_ResourceNotFoundException() {
        // GIVEN
        Student student = new Student();
        when(studentRepository.findByUuid(any())).thenReturn(Optional.empty());

        // THEN
        Assertions.assertThrows(ResourceNotFoundException.class,
            () -> studentService.update(student, UUID.randomUUID()));
    }

    @Test
    public void test_update_already_email_exist_student_throws_IllegalArgumentException() {
        // GIVEN
        Student student = new Student();
        student.setUuid(UUID.fromString("4FFC3D0F-9422-491F-A6DD-8791322C9601"));
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setEmail(EMAIL);

        when(studentRepository.findByUuid(any())).thenReturn(Optional.of(student));
        when(studentRepository.findByEmail(any())).thenReturn(Optional.of(student));

        // THEN
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> studentService.update(student, UUID.randomUUID()));
    }

    @Test
    public void test_update_student() {
        // GIVEN
        UUID uuid = UUID.fromString("4FFC3D0F-9422-491F-A6DD-8791322C9601");
        Student studentBdd = new Student();
        studentBdd.setId(1L);
        studentBdd.setUuid(uuid);
        studentBdd.setFirstName(FIRST_NAME);
        studentBdd.setLastName(LAST_NAME);
        studentBdd.setEmail(EMAIL);

        Student studentToUpdate = new Student();
        studentBdd.setId(1L);
        studentToUpdate.setUuid(uuid);
        studentToUpdate.setFirstName(FIRST_NAME);
        studentToUpdate.setLastName(LAST_NAME);
        studentToUpdate.setEmail(EMAIL_UPDATED);

        when(studentRepository.findByUuid(any())).thenReturn(Optional.of(studentBdd));
        when(studentRepository.findByEmail(any())).thenReturn(Optional.empty());
        // mock return student
        when(studentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        studentService.update(studentToUpdate, uuid);

        // THEN
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentCaptor.capture());
        assertThat(studentCaptor.getValue().getEmail()).isEqualTo(EMAIL_UPDATED);
    }

    // LIST OF STUDENT TEST

    @Test
    public void test_get_students() {
        // GIVEN
        Student student = new Student();
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setEmail(EMAIL);

        Student student2 = new Student();
        student2.setFirstName("2");
        student2.setLastName("2");
        student2.setEmail("email2@test");

        StudentListDTO dto1 = new StudentListDTO();
        dto1.setUuid(UUID.fromString("4FFC3D0F-9422-491F-A6DD-8791322C9601"));
        dto1.setFirstName(FIRST_NAME);
        dto1.setLastName(LAST_NAME);

        StudentListDTO dto2 = new StudentListDTO();
        dto2.setUuid(UUID.fromString("5127EDB0-EAC2-4B14-A84A-C064C70DBC66"));
        dto2.setFirstName("2");
        dto2.setLastName("2");

        when(studentRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(studentRepository.findAll()).thenReturn(List.of(student, student2));
        when(studentListDTOMapper.toDTO(student)).thenReturn(dto1);
        when(studentListDTOMapper.toDTO(student2)).thenReturn(dto2);

        // WHEN
        studentService.create(student);
        studentService.create(student2);

        // THEN
        assertThat(studentService.students().stream().count()).isEqualTo(2);
    }

    // GET STUDENT TEST

    @Test
    public void test_student_null_studentUuid_throws_IllegalArgumentException() {
        // GIVEN

        // THEN
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> studentService.student(null));
    }

    @Test
    public void test_get_student() {
        // GIVEN
        UUID uuid = UUID.fromString("4FFC3D0F-9422-491F-A6DD-8791322C9601");
        Student studentBdd = new Student();
        studentBdd.setId(1L);
        studentBdd.setUuid(uuid);
        studentBdd.setFirstName(FIRST_NAME);
        studentBdd.setLastName(LAST_NAME);
        studentBdd.setEmail(EMAIL);

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setUuid(uuid);
        studentDTO.setFirstName(FIRST_NAME);
        studentDTO.setLastName(LAST_NAME);
        studentDTO.setEmail(EMAIL);

        when(studentRepository.findByUuid(any())).thenReturn(Optional.of(studentBdd));
        when(studentDTOMapper.toDTO(any())).thenReturn(studentDTO);

        // WHEN
        studentService.student(uuid);

        // THEN
        assertThat(studentService.student(uuid)).isEqualTo(studentDTO);
    }

    // REMOVE STUDENT TEST
    
    @Test
    public void test_delete_null_studentUuid_throws_IllegalArgumentException() {
        // GIVEN

        // THEN
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> studentService.delete(null));
    }

    @Test
    public void test_delete_with_uuid_not_found_throws_ResourceNotFoundException() {
        // GIVEN
        UUID uuid = UUID.fromString("4FFC3D0F-9422-491F-A6DD-8791322C9601");

        when(studentRepository.findByUuid(any())).thenReturn(Optional.empty());

        // THEN
        Assertions.assertThrows(ResourceNotFoundException.class,
            () -> studentService.delete(uuid));
    }

    @Test
    void test_delete() {
        // GIVEN
        UUID uuid = UUID.fromString("4FFC3D0F-9422-491F-A6DD-8791322C9601");

        Student student = new Student();
        student.setUuid(uuid);
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setEmail(EMAIL);

        when(studentRepository.findByUuid(uuid)).thenReturn(Optional.of(student));

        // WHEN
        studentService.delete(uuid);

        // THEN
        verify(studentRepository).delete(student);
    }

}
