package emk.student.student_service.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
  private final String path;

  public ResourceNotFoundException(String message, String path) {
    super(message);
    this.path = path;
  }
}
