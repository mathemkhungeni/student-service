package emk.student.student_service.controllers;

import emk.student.student_service.dtos.StudentRequest;
import emk.student.student_service.dtos.StudentResponse;
import emk.student.student_service.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Student Management", description = "APIs for managing student records")
public class StudentController {
  private final StudentService studentService;

  @Operation(
      summary = "Create a new student",
      description = "Creates a new student record with the provided information")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Student created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudentResponse.class))),
        @ApiResponse(responseCode = "422", description = "Invalid input data", content = @Content)
      })
  @PostMapping
  public ResponseEntity<StudentResponse> createStudent(
      @Valid @RequestBody StudentRequest studentRequest) {
    return studentService.createStudent(studentRequest);
  }

  @Operation(summary = "Get student by ID", description = "Retrieves a student record by their ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Student found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudentResponse.class))),
        @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
      })
  @GetMapping("/{id}")
  public ResponseEntity<StudentResponse> getStudentById(
      @Parameter(description = "ID of the student to retrieve") @PathVariable Long id) {
    return studentService.getStudentById(id);
  }

  @Operation(
      summary = "Update student",
      description = "Updates an existing student record with the provided information")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Student updated successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudentResponse.class))),
        @ApiResponse(responseCode = "404", description = "Student not found", content = @Content),
        @ApiResponse(responseCode = "422", description = "Invalid input data", content = @Content)
      })
  @PutMapping("/{id}")
  public ResponseEntity<StudentResponse> updateStudent(
      @Parameter(description = "ID of the student to update") @PathVariable Long id,
      @Valid @RequestBody StudentRequest studentRequest) {
    return studentService.updateStudent(id, studentRequest);
  }

  @Operation(summary = "Delete student", description = "Deletes a student record by their ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Student deleted successfully",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStudent(
      @Parameter(description = "ID of the student to delete") @PathVariable Long id) {
    return studentService.deleteStudent(id);
  }

  @Operation(
      summary = "Get student by student ID",
      description = "Retrieves a student record by their student ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Student found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudentResponse.class))),
        @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
      })
  @GetMapping("/studentId/{studentId}")
  public ResponseEntity<StudentResponse> getStudentByStudentId(
      @Parameter(description = "Student ID to retrieve") @PathVariable String studentId) {
    return studentService.getStudentByStudentId(studentId);
  }

  @Operation(
      summary = "Get all students",
      description = "Retrieves a paginated list of all student records")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "List of students retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
      })
  @GetMapping
  public ResponseEntity<Page<StudentResponse>> getAllStudents(
      @Parameter(description = "Pagination and sorting parameters")
          @PageableDefault(size = 10, sort = "id")
          Pageable pageable) {
    return studentService.getAllStudents(pageable);
  }
}
