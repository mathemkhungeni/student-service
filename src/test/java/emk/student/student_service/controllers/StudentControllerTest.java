package emk.student.student_service.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import emk.student.student_service.dtos.StudentRequest;
import emk.student.student_service.dtos.StudentResponse;
import emk.student.student_service.exceptions.ResourceNotFoundException;
import emk.student.student_service.services.StudentService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
// import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private StudentService studentService;

  private StudentRequest studentRequest;
  private StudentResponse studentResponse;
  private Page<StudentResponse> studentPage;

  @BeforeEach
  void setUp() {
    studentRequest = new StudentRequest();
    studentRequest.setStudentId("STU001");
    studentRequest.setStudentName("John Doe");
    studentRequest.setGrade("A");
    studentRequest.setMobileNumber("1234567890");
    studentRequest.setSchoolName("Test School");

    studentResponse = new StudentResponse();
    studentResponse.setId(1L);
    studentResponse.setStudentId("STU001");
    studentResponse.setStudentName("John Doe");
    studentResponse.setGrade("A");
    studentResponse.setMobileNumber("1234567890");
    studentResponse.setSchoolName("Test School");

    studentPage = new PageImpl<>(List.of(studentResponse));
  }

  @Test
  void getStudentById_ValidId_ReturnsStudent() throws Exception {
    when(studentService.getStudentById(anyLong())).thenReturn(ResponseEntity.ok(studentResponse));

    mockMvc
        .perform(get("/api/students/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(studentResponse.getId()))
        .andExpect(jsonPath("$.studentId").value(studentResponse.getStudentId()))
        .andExpect(jsonPath("$.studentName").value(studentResponse.getStudentName()))
        .andExpect(jsonPath("$.grade").value(studentResponse.getGrade()))
        .andExpect(jsonPath("$.mobileNumber").value(studentResponse.getMobileNumber()))
        .andExpect(jsonPath("$.schoolName").value(studentResponse.getSchoolName()));
  }

  @Test
  void getStudentById_InvalidId_ReturnsNotFound() throws Exception {
    when(studentService.getStudentById(anyLong()))
        .thenThrow(
            new ResourceNotFoundException("Student not found with ID: 1", "/api/students/1"));

    mockMvc
        .perform(get("/api/students/1"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.error").value("Not Found"))
        .andExpect(jsonPath("$.message").value("Student not found with ID: 1"));
  }

  @Test
  void deleteStudent_ValidId_ReturnsNoContent() throws Exception {
    when(studentService.deleteStudent(anyLong())).thenReturn(ResponseEntity.noContent().build());

    mockMvc.perform(delete("/api/students/1")).andExpect(status().isNoContent());
  }

  @Test
  void deleteStudent_InvalidId_ReturnsNotFound() throws Exception {
    when(studentService.deleteStudent(anyLong()))
        .thenThrow(
            new ResourceNotFoundException("Student not found with ID: 1", "/api/students/1"));

    mockMvc
        .perform(delete("/api/students/1"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.error").value("Not Found"))
        .andExpect(jsonPath("$.message").value("Student not found with ID: 1"));
  }

  @Test
  void getStudentByStudentId_ValidId_ReturnsStudent() throws Exception {
    when(studentService.getStudentByStudentId(anyString()))
        .thenReturn(ResponseEntity.ok(studentResponse));

    mockMvc
        .perform(get("/api/students/studentId/STU001"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(studentResponse.getId()))
        .andExpect(jsonPath("$.studentId").value(studentResponse.getStudentId()))
        .andExpect(jsonPath("$.studentName").value(studentResponse.getStudentName()))
        .andExpect(jsonPath("$.grade").value(studentResponse.getGrade()))
        .andExpect(jsonPath("$.mobileNumber").value(studentResponse.getMobileNumber()))
        .andExpect(jsonPath("$.schoolName").value(studentResponse.getSchoolName()));
  }

  @Test
  void getStudentByStudentId_InvalidId_ReturnsNotFound() throws Exception {
    when(studentService.getStudentByStudentId(anyString()))
        .thenThrow(
            new ResourceNotFoundException(
                "Student not found with studentId: STU001", "/api/students/studentId/STU001"));

    mockMvc
        .perform(get("/api/students/studentId/STU001"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.error").value("Not Found"))
        .andExpect(jsonPath("$.message").value("Student not found with studentId: STU001"));
  }

  @Test
  void getAllStudents_ReturnsPageOfStudents() throws Exception {
    PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("id"));
    when(studentService.getAllStudents(any(PageRequest.class)))
        .thenReturn(ResponseEntity.ok(studentPage));

    mockMvc
        .perform(get("/api/students").param("page", "0").param("size", "10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(studentResponse.getId()))
        .andExpect(jsonPath("$.content[0].studentId").value(studentResponse.getStudentId()))
        .andExpect(jsonPath("$.content[0].studentName").value(studentResponse.getStudentName()))
        .andExpect(jsonPath("$.content[0].grade").value(studentResponse.getGrade()))
        .andExpect(jsonPath("$.content[0].mobileNumber").value(studentResponse.getMobileNumber()))
        .andExpect(jsonPath("$.content[0].schoolName").value(studentResponse.getSchoolName()))
        .andExpect(jsonPath("$.totalElements").value(1))
        .andExpect(jsonPath("$.totalPages").value(1))
        .andExpect(jsonPath("$.size").value(1))
        .andExpect(jsonPath("$.number").value(0));
  }

  @Test
  void createStudent_InvalidRequest_ReturnsBadRequest() throws Exception {
    studentRequest.setMobileNumber("invalid-number");

    mockMvc
        .perform(
            post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentRequest)))
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void updateStudent_InvalidRequest_ReturnsBadRequest() throws Exception {
    studentRequest.setMobileNumber("invalid-number");

    mockMvc
        .perform(
            put("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentRequest)))
        .andExpect(status().isUnprocessableEntity());
  }
}
