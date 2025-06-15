package emk.student.student_service.repositories;

import emk.student.student_service.models.Grade;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
  Optional<Grade> findByStudent_StudentId(String studentId);

  Page<Grade> findByStatus(String status, Pageable pageable);
}
