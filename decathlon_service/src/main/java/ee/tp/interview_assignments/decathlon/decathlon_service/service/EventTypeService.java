package ee.tp.interview_assignments.decathlon.decathlon_service.service;

import ee.tp.interview_assignments.decathlon.decathlon_service.dao.EventTypeRepository;
import ee.tp.interview_assignments.decathlon.decathlon_service.dao.model.EventType;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.dto.EventScoreDto;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.dto.EventTypeDto;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.exception.InvalidInputException;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventTypeService {
    private ModelMapper modelMapper;
    private EventTypeRepository repository;
    private EventTypeScoreService scoreService;

    public EventType findByName(String name) {
        return repository.findByName(name);
    }

    public List<EventTypeDto> findAll() {
        return repository.findAll().stream()
            .map(eventType -> modelMapper.map(eventType, EventTypeDto.class))
            .collect(Collectors.toList());
    }

    public EventScoreDto calculateScore(String eventTypeName, BigDecimal performance) {
        if (eventTypeName == null) {
            throw new InvalidInputException("`eventTypeName` must not be null.");
        }

        if (performance == null) {
            throw new InvalidInputException("`performance` must not be null.");
        }

        EventType eventType = findByName(eventTypeName);
        if (eventType == null) {
            throw new NotFoundException("Event type not found.");
        }

        return EventScoreDto.of(
            scoreService.calculate(eventType, performance)
        );
    }
}
