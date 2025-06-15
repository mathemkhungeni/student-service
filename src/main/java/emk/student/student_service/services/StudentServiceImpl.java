package emk.student.student_service.services;

import emk.student.student_service.dtos.StudentRequest;
import emk.student.student_service.dtos.StudentResponse;
import emk.student.student_service.exceptions.ResourceNotFoundException;
import emk.student.student_service.mappers.StudentMapper;
import emk.student.student_service.models.Grade;
import emk.student.student_service.models.Student;
import emk.student.student_service.repositories.StudentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
  private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;
  private final GradeService gradeService;

  @Transactional
  @Override
  @CacheEvict(value = "students", allEntries = true)
  public ResponseEntity<StudentResponse> createStudent(StudentRequest studentRequest) {
    log.info("Creating student: {}", studentRequest);
    Student student = studentMapper.toEntity(studentRequest);
    Student savedStudent = studentRepository.save(student);
    Grade grade = gradeService.createGrade(studentRequest, savedStudent);
    savedStudent.setGrades(List.of(grade));
    StudentResponse response = studentMapper.toResponse(savedStudent);
    log.info("Student created with ID: {}", savedStudent.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Transactional(readOnly = true)
  @Override
  @Cacheable(value = "students", key = "#id", unless = "#result.statusCode.value() == 404")
  public ResponseEntity<StudentResponse> getStudentById(Long id) {
    log.info("Fetching student by ID: {}", id);
    Student student =
        studentRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Student not found with ID: " + id, "/api/students/" + id));
    StudentResponse response = studentMapper.toResponse(student);
    return ResponseEntity.ok(response);
  }

  @Transactional
  @Override
  @CachePut(value = "students", key = "#id")
  public ResponseEntity<StudentResponse> updateStudent(Long id, StudentRequest studentRequest) {
    log.info("Updating student with ID: {}", id);
    Student student =
        studentRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Student not found with ID: " + id, "/api/students/" + id));
    Student updatedStudent = studentMapper.toEntity(studentRequest);
    updatedStudent.setId(id);
    Student savedStudent = studentRepository.save(updatedStudent);
    StudentResponse response = studentMapper.toResponse(savedStudent);
    log.info("Student updated with ID: {}", id);
    return ResponseEntity.ok(response);
  }

  @Transactional
  @Override
  @CacheEvict(value = "students", key = "#id")
  public ResponseEntity<Void> deleteStudent(Long id) {
    log.info("Deleting student with ID: {}", id);
    Student student =
        studentRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Student not found with ID: " + id, "/api/students/" + id));
    studentRepository.deleteById(id);
    log.info("Student deleted with ID: {}", id);
    return ResponseEntity.noContent().build();
  }

  @Transactional(readOnly = true)
  @Override
  @Cacheable(value = "students", key = "#studentId", unless = "#result.statusCode.value() ==404")
  public ResponseEntity<StudentResponse> getStudentByStudentId(String studentId) {
    log.info("Fetching student by studentId: {}", studentId);
    Student student = studentRepository.findByStudentId(studentId);
    if (student == null) {
      throw new ResourceNotFoundException(
          "Student not found with studentId: " + studentId, "/api/students/studentId/" + studentId);
    }
    StudentResponse response = studentMapper.toResponse(student);
    return ResponseEntity.ok(response);
  }

  @Transactional(readOnly = true)
  @Override
  @Cacheable(
      value = "studentPages",
      key = "#pageable.pageNumber + '-' + #pageable.pageSize +'-' + #pageable.sort")
  public ResponseEntity<Page<StudentResponse>> getAllStudents(Pageable pageable) {
    log.info(
        "Fetching students with pagination: page={}, size={}, sort={}",
        pageable.getPageNumber(),
        pageable.getPageSize(),
        pageable.getSort());
    Page<Student> students = studentRepository.findAll(pageable);
    Page<StudentResponse> responses = students.map(studentMapper::toResponse);
    log.info(
        "Total students found: {}, Total pages: {}",
        responses.getTotalElements(),
        responses.getTotalPages());
    return ResponseEntity.ok(responses);
  }
}
