package emk.student.student_service.models;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Data
@Table(name = "students")
@Entity
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String studentId;

  @Column(nullable = false)
  private String studentName;

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
  private List<Grade> grades;

  private String mobileNumber;
  private String schoolName;
}
