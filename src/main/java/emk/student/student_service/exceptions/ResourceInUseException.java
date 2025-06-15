package emk.student.student_service.exceptions;

public class ResourceInUseException extends RuntimeException {
  public ResourceInUseException(String resourceName, String fieldName, Object fieldValue) {
    super(
        String.format(
            "%s with %s '%s' is currently in use and cannot be deleted",
            resourceName, fieldName, fieldValue));
  }
}
