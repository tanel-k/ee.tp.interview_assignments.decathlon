package ee.tp.interview_assignments.decathlon.decathlon_service.controller;

import ee.tp.interview_assignments.decathlon.decathlon_service.service.dto.EventPointsDto;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.dto.EventTypeDto;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.EventTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("decathlon/v1")
@AllArgsConstructor
public class EventTypeController {
    private EventTypeService service;

    @GetMapping("event-types/{eventType}/points")
    public EventPointsDto calculatePoints(@PathVariable(name = "eventType") String eventType,
                                          @RequestParam(name = "performance") BigDecimal performance) {
        return service.calculatePoints(eventType, performance);
    }

    @GetMapping("event-types")
    public List<EventTypeDto> getEventTypes() {
        return service.findAll();
    }
}
