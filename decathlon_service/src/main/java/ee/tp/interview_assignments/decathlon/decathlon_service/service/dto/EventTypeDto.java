package ee.tp.interview_assignments.decathlon.decathlon_service.service.dto;

import ee.tp.interview_assignments.decathlon.decathlon_service.repository.model.EventType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventTypeDto {
    private String name;
    private String displayName;
    private EventType.PerformanceDimension performanceDimension;
}
