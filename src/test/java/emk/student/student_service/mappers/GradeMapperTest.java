package emk.student.student_service.mappers;

import static org.junit.jupiter.api.Assertions.*;

import emk.student.student_service.dtos.GradeRequest;
import emk.student.student_service.dtos.GradeResponse;
import emk.student.student_service.models.Grade;
import emk.student.student_service.models.Student;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GradeMapperTest {

  private GradeMapper gradeMapper;

  @BeforeEach
  void setUp() {
    gradeMapper = new GradeMapper();
  }

  @Test
  void toEntity_WithValidRequest_ShouldMapCorrectly() {
    // Arrange
    GradeRequest request = new GradeRequest();
    request.setGrade("A");
    request.setSemester("1");
    request.setStatus("Current");
    request.setStudentId("STU001");

    // Act
    Grade result = gradeMapper.toEntity(request);

    // Assert
    assertNotNull(result);
    assertEquals(request.getGrade(), result.getGrade());
    assertEquals(request.getStatus(), result.getStatus());
    assertNotNull(result.getSemester());
  }

  @Test
  void toEntity_WithNullRequest_ShouldReturnNull() {
    // Act
    Grade result = gradeMapper.toEntity(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toEntity_WithNullStartDate_ShouldNotSetStartDate() {
    // Arrange
    GradeRequest request = new GradeRequest();
    request.setGrade("A");
    request.setStatus("Current");
    request.setStudentId("STU001");

    // Act
    Grade result = gradeMapper.toEntity(request);

    // Assert
    assertNotNull(result);
    assertEquals(request.getGrade(), result.getGrade());
    assertEquals(request.getStatus(), result.getStatus());
    assertNull(result.getSemester());
  }

  @Test
  void toResponse_WithValidGrade_ShouldMapCorrectly() {
    // Arrange
    Grade grade = new Grade();
    grade.setId(1L);
    grade.setGrade("A");
    grade.setStatus("Current");

    Student student = new Student();
    student.setStudentId("STU001");
    grade.setStudent(student);

    // Act
    GradeResponse result = gradeMapper.toResponse(grade);

    // Assert
    assertNotNull(result);
    assertEquals(grade.getId(), result.getId());
    assertEquals(grade.getGrade(), result.getGrade());
    assertEquals(grade.getStatus(), result.getStatus());
    assertEquals(student.getStudentId(), result.getStudentId());
  }

  @Test
  void toResponse_WithNullGrade_ShouldReturnNull() {
    // Act
    GradeResponse result = gradeMapper.toResponse(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toResponse_WithGradeWithoutStudent_ShouldReturnNullStudentId() {
    // Arrange
    Grade grade = new Grade();
    grade.setId(1L);
    grade.setGrade("A");
    grade.setStatus("Current");

    // Act
    GradeResponse result = gradeMapper.toResponse(grade);

    // Assert
    assertNotNull(result);
    assertEquals(grade.getId(), result.getId());
    assertEquals(grade.getGrade(), result.getGrade());
    assertEquals(grade.getStatus(), result.getStatus());
    assertNull(result.getStudentId());
  }
}
