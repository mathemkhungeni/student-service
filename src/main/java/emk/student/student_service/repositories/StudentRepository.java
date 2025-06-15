package emk.student.student_service.repositories;

import emk.student.student_service.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
  Student findByStudentId(String studentId);
}
