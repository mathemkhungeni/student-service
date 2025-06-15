package emk.student.student_service.controllers;

import emk.student.student_service.dtos.GradeResponse;
import emk.student.student_service.services.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grades")
@Tag(name = "Grade Management", description = "APIs for managing student grades")
public class GradeController {
  private final GradeService gradeService;

  @GetMapping
  @Operation(
      summary = "Get all grades",
      description = "Retrieves a paginated list of all grades with optional filtering by status")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved grades",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GradeResponse.class)))
      })
  public ResponseEntity<Page<GradeResponse>> getAllGrades(
      @Parameter(description = "Filter grades by status (optional)")
          @RequestParam(required = false, defaultValue = "Current")
          String status,
      @Parameter(description = "Pagination and sorting parameters")
          @PageableDefault(size = 10, sort = "id")
          Pageable pageable) {
    return gradeService.getAllGrades(status, pageable);
  }
}
