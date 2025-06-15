package emk.student.student_service.mappers;

import static org.junit.jupiter.api.Assertions.*;

import emk.student.student_service.dtos.StudentRequest;
import emk.student.student_service.dtos.StudentResponse;
import emk.student.student_service.models.Grade;
import emk.student.student_service.models.Student;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentMapperTest {

  private StudentMapper studentMapper;

  @BeforeEach
  void setUp() {
    studentMapper = new StudentMapper();
  }

  @Test
  void toEntity_WithValidRequest_ShouldMapCorrectly() {
    // Arrange
    StudentRequest request = new StudentRequest();
    request.setStudentId("STU001");
    request.setStudentName("John Doe");
    request.setMobileNumber("1234567890");
    request.setSchoolName("Test School");

    // Act
    Student result = studentMapper.toEntity(request);

    // Assert
    assertNotNull(result);
    assertEquals(request.getStudentId(), result.getStudentId());
    assertEquals(request.getStudentName(), result.getStudentName());
    assertEquals(request.getMobileNumber(), result.getMobileNumber());
    assertEquals(request.getSchoolName(), result.getSchoolName());
  }

  @Test
  void toEntity_WithNullRequest_ShouldReturnNull() {
    // Act
    Student result = studentMapper.toEntity(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toResponse_WithValidStudent_ShouldMapCorrectly() {
    // Arrange
    Student student = new Student();
    student.setId(1L);
    student.setStudentId("STU001");
    student.setStudentName("John Doe");
    student.setMobileNumber("1234567890");
    student.setSchoolName("Test School");

    List<Grade> grades = new ArrayList<>();
    Grade grade = new Grade();
    grade.setGrade("A");
    grade.setStatus("Current");
    grades.add(grade);
    student.setGrades(grades);

    // Act
    StudentResponse result = studentMapper.toResponse(student);

    // Assert
    assertNotNull(result);
    assertEquals(student.getId(), result.getId());
    assertEquals(student.getStudentId(), result.getStudentId());
    assertEquals(student.getStudentName(), result.getStudentName());
    assertEquals(student.getMobileNumber(), result.getMobileNumber());
    assertEquals(student.getSchoolName(), result.getSchoolName());
    assertEquals("A", result.getGrade());
  }

  @Test
  void toResponse_WithNullStudent_ShouldReturnNull() {
    // Act
    StudentResponse result = studentMapper.toResponse(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toResponse_WithStudentWithoutGrades_ShouldReturnNullGrade() {
    // Arrange
    Student student = new Student();
    student.setId(1L);
    student.setStudentId("STU001");
    student.setStudentName("John Doe");
    student.setMobileNumber("1234567890");
    student.setSchoolName("Test School");
    student.setGrades(new ArrayList<>());

    // Act
    StudentResponse result = studentMapper.toResponse(student);

    // Assert
    assertNotNull(result);
    assertNull(result.getGrade());
  }

  @Test
  void toResponse_WithStudentWithoutCurrentGrade_ShouldReturnNullGrade() {
    // Arrange
    Student student = new Student();
    student.setId(1L);
    student.setStudentId("STU001");
    student.setStudentName("John Doe");
    student.setMobileNumber("1234567890");
    student.setSchoolName("Test School");

    List<Grade> grades = new ArrayList<>();
    Grade grade = new Grade();
    grade.setGrade("A");
    grade.setStatus("Previous");
    grades.add(grade);
    student.setGrades(grades);

    // Act
    StudentResponse result = studentMapper.toResponse(student);

    // Assert
    assertNotNull(result);
    assertNull(result.getGrade());
  }
}
