package emk.student.student_service.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "student_grades")
public class Grade {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String grade;
  private String semester;
  private String status;
  @ManyToOne private Student student;
}
