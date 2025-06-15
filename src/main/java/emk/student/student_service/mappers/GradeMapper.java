package emk.student.student_service.mappers;

import emk.student.student_service.dtos.GradeRequest;
import emk.student.student_service.dtos.GradeResponse;
import emk.student.student_service.models.Grade;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Component;

@Component
public class GradeMapper {

  public Grade toEntity(GradeRequest request) {
    if (request == null) {
      return null;
    }

    Grade grade = new Grade();

    grade.setGrade(request.getGrade());
    grade.setStatus(request.getStatus());
    grade.setSemester(request.getSemester());

    return grade;
  }

  public GradeResponse toResponse(Grade grade) {
    if (grade == null) {
      return null;
    }

    GradeResponse gradeResponse = new GradeResponse();

    gradeResponse.setId(grade.getId());
    gradeResponse.setGrade(grade.getGrade());
    gradeResponse.setStatus(grade.getStatus());

    gradeResponse.setStudentId(getStudentId(grade));

    return gradeResponse;
  }

  private String getStudentId(Grade grade) {
    if (grade == null || grade.getStudent() == null) {
      return null;
    }

    return grade.getStudent().getStudentId();
  }
}
