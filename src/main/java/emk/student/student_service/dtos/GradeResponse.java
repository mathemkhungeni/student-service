package emk.student.student_service.dtos;

import lombok.Data;

@Data
public class GradeResponse {
  private Long id;
  private String grade;
  private String status;
  private String studentId;
}
