package ee.tp.interview_assignments.decathlon.decathlon_service.service;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.dto.EventPointsDto;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.dto.EventTypeDto;
import ee.tp.interview_assignments.decathlon.decathlon_service.dao.EventTypeRepository;
import ee.tp.interview_assignments.decathlon.decathlon_service.dao.model.EventType;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventTypeService {
    private ModelMapper modelMapper;
    private EventTypeRepository repository;

    private Integer calculatePoints(EventType eventType, BigDecimal performance) {
        BigDecimal a = eventType.getScoringParameterA();
        BigDecimal b = eventType.getScoringParameterB();
        BigDecimal c = eventType.getScoringParameterC();

        BigDecimal baseDifference;
        if (eventType.getEnvironment() == EventType.Environment.FIELD) {
            baseDifference = performance.subtract(b);
        } else {
            baseDifference = b.subtract(performance);
        }

        return a.multiply(BigDecimalMath.pow(baseDifference, c, MathContext.UNLIMITED)).intValue();
    }

    public EventPointsDto calculatePoints(String eventTypeName, BigDecimal performance) {
        EventType eventType = repository.findByName(eventTypeName);
        // TODO: if eventType == null.
        // TODO: error handling.
        Integer points = calculatePoints(eventType, performance);
        return EventPointsDto.of(points);
    }

    public List<EventTypeDto> findAll() {
        return repository.findAll().stream()
            .map(eventType -> modelMapper.map(eventType, EventTypeDto.class))
            .collect(Collectors.toList());
    }
}
