package emk.student.student_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import emk.student.student_service.dtos.StudentRequest;
import emk.student.student_service.dtos.StudentResponse;
import emk.student.student_service.exceptions.ResourceNotFoundException;
import emk.student.student_service.mappers.StudentMapper;
import emk.student.student_service.models.Grade;
import emk.student.student_service.models.Student;
import emk.student.student_service.repositories.StudentRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

  @Mock private StudentRepository studentRepository;

  @Mock private StudentMapper studentMapper;

  @Mock private GradeService gradeService;

  @InjectMocks private StudentServiceImpl studentService;

  private Student student;
  private Grade grade;
  private StudentRequest studentRequest;
  private StudentResponse studentResponse;

  @BeforeEach
  void setUp() {
    grade = new Grade();
    grade.setStatus("Current");
    grade.setGrade("1");
    grade.setStartDate(LocalDate.parse("2022-01-01"));
    List<Grade> grades = Arrays.asList(grade);
    student = new Student();
    student.setId(1L);
    student.setStudentName("John Doe");
    student.setGrades(grades);
    student.setMobileNumber("1234567890");
    student.setSchoolName("Test School");

    studentRequest = new StudentRequest();
    studentRequest.setStudentName("John Doe");
    studentRequest.setGrade("A");
    studentRequest.setMobileNumber("1234567890");
    studentRequest.setSchoolName("Test School");

    studentResponse = new StudentResponse();
    studentResponse.setId(1L);
    studentResponse.setStudentName("John Doe");
    studentResponse.setGrade("A");
    studentResponse.setMobileNumber("1234567890");
    studentResponse.setSchoolName("Test School");
  }

  @Test
  void createStudent_ValidRequest_ReturnsCreatedStudent() {
    when(studentMapper.toEntity(any(StudentRequest.class))).thenReturn(student);
    when(studentRepository.save(any(Student.class))).thenReturn(student);
    when(studentMapper.toResponse(any(Student.class))).thenReturn(studentResponse);
    when(gradeService.createGrade(any(), any())).thenReturn(grade);

    ResponseEntity<StudentResponse> result = studentService.createStudent(studentRequest);

    assertNotNull(result);
    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals(studentResponse.getId(), result.getBody().getId());
    assertEquals(studentResponse.getStudentName(), result.getBody().getStudentName());
    verify(studentRepository).save(any(Student.class));
  }

  @Test
  void getStudentById_ValidId_ReturnsStudent() {
    when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
    when(studentMapper.toResponse(any(Student.class))).thenReturn(studentResponse);

    ResponseEntity<StudentResponse> result = studentService.getStudentById(1L);

    assertNotNull(result);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(studentResponse.getId(), result.getBody().getId());
    assertEquals(studentResponse.getStudentName(), result.getBody().getStudentName());
  }

  @Test
  void getStudentById_InvalidId_ThrowsResourceNotFoundException() {
    when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById(1L));
  }

  @Test
  void getAllStudents_ReturnsListOfStudents() {
    List<Student> students = Arrays.asList(student);
    Page<Student> studentPage = new PageImpl<>(students);
    PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("studentName"));

    when(studentRepository.findAll(any(PageRequest.class))).thenReturn(studentPage);
    when(studentMapper.toResponse(any(Student.class))).thenReturn(studentResponse);

    ResponseEntity<Page<StudentResponse>> result = studentService.getAllStudents(pageRequest);

    assertNotNull(result);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(1, result.getBody().getContent().size());
    assertEquals(studentResponse.getId(), result.getBody().getContent().get(0).getId());
  }

  @Test
  void updateStudent_ValidRequest_ReturnsUpdatedStudent() {
    when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
    when(studentMapper.toEntity(any(StudentRequest.class))).thenReturn(student);
    when(studentRepository.save(any(Student.class))).thenReturn(student);
    when(studentMapper.toResponse(any(Student.class))).thenReturn(studentResponse);

    ResponseEntity<StudentResponse> result = studentService.updateStudent(1L, studentRequest);

    assertNotNull(result);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(studentResponse.getId(), result.getBody().getId());
    assertEquals(studentResponse.getStudentName(), result.getBody().getStudentName());
    verify(studentRepository).save(any(Student.class));
  }

  @Test
  void updateStudent_InvalidId_ThrowsResourceNotFoundException() {
    when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(
        ResourceNotFoundException.class, () -> studentService.updateStudent(1L, studentRequest));
  }

  @Test
  void deleteStudent_ValidId_DeletesStudent() {
    when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
    doNothing().when(studentRepository).deleteById(anyLong());

    ResponseEntity<Void> result = studentService.deleteStudent(1L);

    assertNotNull(result);
    assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    verify(studentRepository).deleteById(anyLong());
  }

  @Test
  void deleteStudent_InvalidId_ThrowsResourceNotFoundException() {
    when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> studentService.deleteStudent(1L));
  }
}
