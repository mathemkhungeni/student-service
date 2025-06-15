package emk.student.student_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import emk.student.student_service.dtos.GradeResponse;
import emk.student.student_service.dtos.StudentRequest;
import emk.student.student_service.mappers.GradeMapper;
import emk.student.student_service.models.Grade;
import emk.student.student_service.models.Student;
import emk.student.student_service.repositories.GradeRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GradeServiceImplTest {

  @Mock private GradeRepository gradeRepository;

  @Mock private GradeMapper gradeMapper;

  @InjectMocks private GradeServiceImpl gradeService;

  private Grade grade;
  private GradeResponse gradeResponse;
  private Student student;
  private StudentRequest studentRequest;

  @BeforeEach
  void setUp() {
    grade = new Grade();
    grade.setId(1L);
    grade.setGrade("A");
    grade.setStatus("Current");

    gradeResponse = new GradeResponse();
    gradeResponse.setId(1L);
    gradeResponse.setGrade("A");
    gradeResponse.setStatus("Current");

    student = new Student();
    student.setId(1L);
    student.setStudentId("STU001");
    student.setStudentName("John Doe");

    studentRequest = new StudentRequest();
    studentRequest.setStudentId("STU001");
    studentRequest.setStudentName("John Doe");
    studentRequest.setGrade("A");
    studentRequest.setStartDate("2024-03-20");
  }

  @Test
  void createGrade_ShouldReturnCreatedGrade() {
    when(gradeRepository.save(any(Grade.class))).thenReturn(grade);

    Grade result = gradeService.createGrade(studentRequest, student);

    assertThat(result).isNotNull();
    assertThat(result.getGrade()).isEqualTo("A");
    assertThat(result.getStatus()).isEqualTo("Current");
    verify(gradeRepository).save(any(Grade.class));
  }

  @Test
  void getAllGrades_WithStatus_ShouldReturnFilteredGrades() {
    Pageable pageable = PageRequest.of(0, 10);
    List<Grade> grades = Collections.singletonList(grade);
    Page<Grade> gradePage = new PageImpl<>(grades);

    when(gradeRepository.findByStatus("Current", pageable)).thenReturn(gradePage);
    when(gradeMapper.toResponse(any(Grade.class))).thenReturn(gradeResponse);

    ResponseEntity<Page<GradeResponse>> response = gradeService.getAllGrades("Current", pageable);

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getContent()).hasSize(1);
    verify(gradeRepository).findByStatus("Current", pageable);
  }

  @Test
  void getAllGrades_WithoutStatus_ShouldReturnAllGrades() {
    Pageable pageable = PageRequest.of(0, 10);
    List<Grade> grades = Collections.singletonList(grade);
    Page<Grade> gradePage = new PageImpl<>(grades);

    when(gradeRepository.findAll(pageable)).thenReturn(gradePage);
    when(gradeMapper.toResponse(any(Grade.class))).thenReturn(gradeResponse);

    ResponseEntity<Page<GradeResponse>> response = gradeService.getAllGrades(null, pageable);

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getContent()).hasSize(1);
    verify(gradeRepository).findAll(pageable);
  }
}
