package emk.student.student_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
  @NotBlank(message = "Student Id is required")
  private String studentId;

  @NotBlank(message = "Student name is required")
  private String studentName;

  @NotBlank(message = "Grade is required")
  private String grade;

  @NotBlank(message = "Semester is required")
  private String semester;

  @NotBlank(message = "Mobile number is required")
  @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid mobile number format")
  private String mobileNumber;

  @NotBlank(message = "School name is required")
  private String schoolName;
}
