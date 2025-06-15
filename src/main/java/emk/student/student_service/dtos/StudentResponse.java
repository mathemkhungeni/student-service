package emk.student.student_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
  private Long id;
  private String studentId;
  private String studentName;
  private String grade;
  private String mobileNumber;
  private String schoolName;
}
