package emk.student.student_service.services;

import emk.student.student_service.dtos.GradeResponse;
import emk.student.student_service.dtos.StudentRequest;
import emk.student.student_service.models.Grade;
import emk.student.student_service.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface GradeService {

  Grade createGrade(StudentRequest studentRequest, Student student);

  ResponseEntity<Page<GradeResponse>> getAllGrades(String status, Pageable pageable);
}
