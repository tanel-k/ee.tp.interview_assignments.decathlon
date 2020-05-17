package ee.tp.interview_assignments.decathlon.decathlon_service.controller;

import ee.tp.interview_assignments.decathlon.decathlon_service.service.EventTypeService;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.dto.EventScoreDto;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.dto.EventTypeDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("decathlon/v1/event_types")
@AllArgsConstructor
public class EventTypeController {
    private EventTypeService service;

    @GetMapping
    public List<EventTypeDto> getEventTypes() {
        return service.findAll();
    }

    @GetMapping("{eventTypeName}/score")
    public EventScoreDto calculateScore(@PathVariable(name = "eventTypeName") String eventTypeName,
                                        @RequestParam(name = "performance") BigDecimal performance) {
        return service.calculateScore(eventTypeName, performance);
    }
}
