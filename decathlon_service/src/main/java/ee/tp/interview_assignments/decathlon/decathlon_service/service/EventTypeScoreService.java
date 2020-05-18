package ee.tp.interview_assignments.decathlon.decathlon_service.service;

import ch.obermuhlner.math.big.BigDecimalMath;
import ee.tp.interview_assignments.decathlon.decathlon_service.repository.model.EventType;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.exception.InvalidInputException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class EventTypeScoreService {
    public int getScore(EventType type, BigDecimal performance) {
        if (performance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidInputException("`performance` must be non-negative.");
        }

        // Could use IAAF scoring tables to reduce this to a simple lookup.
        // Scoring tables are authoritative so no need to worry about precision/rounding.

        int precision = type.getScoringPrecision();
        MathContext mc = new MathContext(precision, RoundingMode.HALF_DOWN);

        performance = performance.round(mc);

        BigDecimal a = type.getScoringParameterA();
        BigDecimal b = type.getScoringParameterB();
        BigDecimal c = type.getScoringParameterC();

        BigDecimal conversionFactor = type.getScoringUnitConversionFactor();
        BigDecimal convertedPerformance = performance.divide(conversionFactor, performance.scale(), RoundingMode.HALF_DOWN);

        BigDecimal thresholdDiff;
        if (type.getPerformanceDimension() == EventType.PerformanceDimension.DISTANCE) {
            thresholdDiff = convertedPerformance.subtract(b);
        } else {
            thresholdDiff = b.subtract(convertedPerformance);
        }

        if (thresholdDiff.compareTo(BigDecimal.ZERO) < 0) {
            // If contestant cannot reach threshold, they are awarded 0 points.
            // https://fivethirtyeight.com/features/the-scoring-for-the-decathlon-and-heptathlon-favors-running-over-throwing
            return 0;
        }

        BigDecimal progressiveDiff = BigDecimalMath.pow(thresholdDiff, c, mc);
        return a.multiply(progressiveDiff, mc).intValue();
    }
}
