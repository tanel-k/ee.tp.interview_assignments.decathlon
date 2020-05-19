package ee.tp.interview_assignments.decathlon.decathlon_service.controller;

import ee.tp.interview_assignments.decathlon.decathlon_service.service.EventTypeService;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.dto.EventScoreDto;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.dto.EventTypeDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("decathlon/v1/event-types")
@AllArgsConstructor
public class EventTypeController {
    private EventTypeService service;

    @GetMapping
    public List<EventTypeDto> getEventTypes() {
        return service.findAll();
    }

    // We'll treat the (infinite) set of all possible scores for an event type as a sub-resource of said event type.
    // This way GET-ing with performance=X kind of mimics standard REST-ful filtering.
    // Clients need not know or care that we need to calculate the score.
    @GetMapping("{eventTypeName}/scores")
    public EventScoreDto getScore(@PathVariable(name = "eventTypeName") String eventTypeName,
                                  @RequestParam(name = "performance") BigDecimal performance) {
        return service.getScore(eventTypeName, performance);
    }
}
