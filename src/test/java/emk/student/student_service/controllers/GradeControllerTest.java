package emk.student.student_service.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import emk.student.student_service.dtos.GradeResponse;
import emk.student.student_service.services.GradeService;
import java.util.Collections;
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
class GradeControllerTest {

  @Mock private GradeService gradeService;

  @InjectMocks private GradeController gradeController;

  private GradeResponse gradeResponse;
  private Page<GradeResponse> gradePage;

  @BeforeEach
  void setUp() {
    gradeResponse = new GradeResponse();
    gradeResponse.setId(1L);
    gradeResponse.setGrade("A");
    gradeResponse.setStatus("Current");

    gradePage = new PageImpl<>(Collections.singletonList(gradeResponse));
  }

  @Test
  void getAllGrades_WithStatus_ShouldReturnFilteredGrades() {
    Pageable pageable = PageRequest.of(0, 10);
    ResponseEntity<Page<GradeResponse>> expectedResponse = ResponseEntity.ok(gradePage);

    when(gradeService.getAllGrades(eq("Current"), any(Pageable.class)))
        .thenReturn(expectedResponse);

    ResponseEntity<Page<GradeResponse>> response =
        gradeController.getAllGrades("Current", pageable);

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getContent()).hasSize(1);
    verify(gradeService).getAllGrades("Current", pageable);
  }

  @Test
  void getAllGrades_WithoutStatus_ShouldReturnAllGrades() {
    Pageable pageable = PageRequest.of(0, 10);
    ResponseEntity<Page<GradeResponse>> expectedResponse = ResponseEntity.ok(gradePage);

    when(gradeService.getAllGrades(eq(null), any(Pageable.class))).thenReturn(expectedResponse);

    ResponseEntity<Page<GradeResponse>> response = gradeController.getAllGrades(null, pageable);

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getContent()).hasSize(1);
    verify(gradeService).getAllGrades(null, pageable);
  }
}
