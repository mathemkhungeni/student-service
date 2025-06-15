package emk.student.student_service.services;

import emk.student.student_service.dtos.StudentRequest;
import emk.student.student_service.dtos.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface StudentService {
  ResponseEntity<StudentResponse> createStudent(StudentRequest studentRequest);

  ResponseEntity<StudentResponse> getStudentById(Long id);

  ResponseEntity<StudentResponse> updateStudent(Long id, StudentRequest studentRequest);

  ResponseEntity<Void> deleteStudent(Long id);

  ResponseEntity<StudentResponse> getStudentByStudentId(String studentId);

  ResponseEntity<Page<StudentResponse>> getAllStudents(Pageable pageable);
}
