package emk.student.student_service.dtos;

import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import lombok.Data;

@Data
public class GradeRequest {
  @NotBlank(message = "Grade is required")
  private String grade;

  @NotBlank(message = "Semester is required")
  private String semester;

  @NotBlank(message = "Status is required")
  private String status;

  @NotBlank(message = "Student Id is required")
  private String studentId;
}
