package emk.student.student_service.exceptions;

import lombok.Getter;

@Getter
public class ResourceConflictException extends RuntimeException {
  private final String path;

  public ResourceConflictException(String message, String path) {
    super(message);
    this.path = path;
  }
}
