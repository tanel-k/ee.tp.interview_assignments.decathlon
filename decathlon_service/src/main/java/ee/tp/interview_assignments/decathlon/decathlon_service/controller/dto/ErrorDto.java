package ee.tp.interview_assignments.decathlon.decathlon_service.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class ErrorDto {
    private String message;
}
