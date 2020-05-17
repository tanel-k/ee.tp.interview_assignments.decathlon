package ee.tp.interview_assignments.decathlon.decathlon_service.service.exception;

public class InvalidInputException extends IllegalArgumentException {
    public InvalidInputException(String message) {
        super(message);
    }
}
