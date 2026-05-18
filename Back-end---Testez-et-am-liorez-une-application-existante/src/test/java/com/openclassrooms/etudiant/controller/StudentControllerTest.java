package com.openclassrooms.etudiant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.openclassrooms.etudiant.dto.StudentSaveDTO;
import com.openclassrooms.etudiant.entities.Student;
import com.openclassrooms.etudiant.entities.User;
import com.openclassrooms.etudiant.repository.StudentRepository;
import com.openclassrooms.etudiant.repository.UserRepository;
import com.openclassrooms.etudiant.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class StudentControllerTest extends AbstractIntegrationTest {

    private static final String USER_FIRST_NAME = "userFirst";
    private static final String USER_LAST_NAME = "userLast";
    private static final String USER_LOGIN = "userLogin";
    private static final String USER_PASSWORD = "userPassword";

    private static final String STUDENT1_FIRST_NAME = "student1First";
    private static final String STUDENT1_LAST_NAME = "student1Last";
    private static final String STUDENT1_EMAIL = "student1@email.com";

    private static final String STUDENT2_FIRST_NAME = "student2First";
    private static final String STUDENT2_LAST_NAME = "student2Last";
    private static final String STUDENT2_EMAIL = "student2@email.com";

    private static final String CREATE_STUDENT_URL = "/api/createStudent";
    private static final String UPDATE_STUDENT_URL = "/api/updateStudent/{uuid}";
    private static final String GET_STUDENT_LIST = "/api/getStudentList";
    private static final String GET_STUDENT = "/api/getStudent/{uuid}";
    private static final String DELETE_STUDENT = "/api/deleteStudent/{uuid}";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserService userService;

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        User user = new User();
        user.setFirstName(USER_FIRST_NAME);
        user.setLastName(USER_LAST_NAME);
        user.setLogin(USER_LOGIN);
        user.setPassword(USER_PASSWORD);
        userService.register(user);

        token = getToken(mockMvc, objectMapper, USER_LOGIN, USER_PASSWORD);
    }

    @AfterEach
    void afterEach() {
        studentRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createStudentWithoutRequiredData() throws Exception {
        // GIVEN
        StudentSaveDTO studentSaveDTO = new StudentSaveDTO();

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_STUDENT_URL)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(studentSaveDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createStudent() throws Exception {
        // GIVEN
        StudentSaveDTO studentSaveDTO = new StudentSaveDTO();
        studentSaveDTO.setFirstName(STUDENT1_FIRST_NAME);
        studentSaveDTO.setLastName(STUDENT1_LAST_NAME);
        studentSaveDTO.setEmail(STUDENT1_EMAIL);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_STUDENT_URL)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(studentSaveDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void updateStudent() throws Exception {
        // GIVEN
        UUID uuid = UUID.randomUUID();

        Student student = new Student();
        student.setUuid(uuid);
        student.setFirstName(STUDENT1_FIRST_NAME);
        student.setLastName(STUDENT1_LAST_NAME);
        student.setEmail(STUDENT1_EMAIL);
        Student studentBdd = studentRepository.save(student);

        // Update student
        studentBdd.setEmail("updated@email.com");

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_STUDENT_URL, uuid)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(studentBdd))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getStudentList() throws Exception {
        // GIVEN 2 STUDENTS
        Student student1 = new Student();
        student1.setUuid(UUID.randomUUID());
        student1.setFirstName(STUDENT1_FIRST_NAME);
        student1.setLastName(STUDENT1_LAST_NAME);
        student1.setEmail(STUDENT1_EMAIL);

        Student student2 = new Student();
        student2.setUuid(UUID.randomUUID());
        student2.setFirstName(STUDENT2_FIRST_NAME);
        student2.setLastName(STUDENT2_LAST_NAME);
        student2.setEmail(STUDENT2_EMAIL);

        studentRepository.saveAll(List.of(student1, student2));

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get(GET_STUDENT_LIST)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    public void getStudent() throws Exception {
        // GIVEN
        UUID uuid = UUID.randomUUID();

        Student student = new Student();
        student.setUuid(uuid);
        student.setFirstName(STUDENT1_FIRST_NAME);
        student.setLastName(STUDENT1_LAST_NAME);
        student.setEmail(STUDENT1_EMAIL);
        studentRepository.save(student);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get(GET_STUDENT, uuid)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(STUDENT1_FIRST_NAME))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(STUDENT1_LAST_NAME))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(STUDENT1_EMAIL));

    }

    @Test
    public void deleteStudent() throws Exception {
        // GIVEN
        UUID uuid = UUID.randomUUID();

        Student student = new Student();
        student.setUuid(uuid);
        student.setFirstName(STUDENT1_FIRST_NAME);
        student.setLastName(STUDENT1_LAST_NAME);
        student.setEmail(STUDENT1_EMAIL);
        studentRepository.save(student);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_STUDENT, uuid)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
