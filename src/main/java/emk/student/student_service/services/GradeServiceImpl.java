package emk.student.student_service.services;

import emk.student.student_service.dtos.GradeResponse;
import emk.student.student_service.dtos.StudentRequest;
import emk.student.student_service.mappers.GradeMapper;
import emk.student.student_service.models.Grade;
import emk.student.student_service.models.Student;
import emk.student.student_service.repositories.GradeRepository;
import java.time.LocalDate;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {
  private final GradeRepository gradeRepository;
  private final GradeMapper gradeMapper;

  @Override
  public Grade createGrade(StudentRequest studentRequest, Student student) {
    Grade grade = new Grade();
    grade.setGrade(studentRequest.getGrade());
    grade.setSemester(studentRequest.getSemester());
    grade.setStatus("Current");
    grade.setStudent(student);
    return gradeRepository.save(grade);
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntity<Page<GradeResponse>> getAllGrades(String status, Pageable pageable) {
    Page<Grade> grades;
    if (Objects.nonNull(status) && !status.isEmpty()) {
      grades = gradeRepository.findByStatus(status, pageable);
    } else {
      grades = gradeRepository.findAll(pageable);
    }
    Page<GradeResponse> responses = grades.map(gradeMapper::toResponse);
    return ResponseEntity.ok(responses);
  }
}
