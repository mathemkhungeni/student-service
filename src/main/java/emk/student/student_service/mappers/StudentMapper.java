package emk.student.student_service.mappers;

import emk.student.student_service.dtos.StudentRequest;
import emk.student.student_service.dtos.StudentResponse;
import emk.student.student_service.models.Grade;
import emk.student.student_service.models.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

  public Student toEntity(StudentRequest request) {
    if (request == null) {
      return null;
    }

    Student student = new Student();

    student.setStudentId(request.getStudentId());
    student.setStudentName(request.getStudentName());
    student.setMobileNumber(request.getMobileNumber());
    student.setSchoolName(request.getSchoolName());

    return student;
  }

  public StudentResponse toResponse(Student student) {
    if (student == null) {
      return null;
    }

    StudentResponse studentResponse = new StudentResponse();

    studentResponse.setId(student.getId());
    studentResponse.setStudentId(student.getStudentId());
    studentResponse.setStudentName(student.getStudentName());
    studentResponse.setMobileNumber(student.getMobileNumber());
    studentResponse.setSchoolName(student.getSchoolName());

    studentResponse.setGrade(getCurrentGrade(student));

    return studentResponse;
  }

  private String getCurrentGrade(Student student) {
    if (student == null || student.getGrades() == null) {
      return null;
    }

    return student.getGrades().stream()
        .filter(grade -> grade.getStatus().equals("Current"))
        .map(Grade::getGrade)
        .findFirst()
        .orElse(null);
  }
}
