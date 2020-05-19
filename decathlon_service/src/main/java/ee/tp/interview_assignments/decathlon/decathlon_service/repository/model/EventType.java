package ee.tp.interview_assignments.decathlon.decathlon_service.repository.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "event_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventType {
    @SuppressWarnings("unused")
    public enum PerformanceDimension {
        TIME,
        DISTANCE
    }

    @Id
    private String name;
    @Column(nullable = false)
    private String displayName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PerformanceDimension performanceDimension;
    @Column(nullable = false)
    private int scoringPrecision;
    @Column(name = "scoring_parameter_a", nullable = false)
    private BigDecimal scoringParameterA;
    @Column(name = "scoring_parameter_b", nullable = false)
    private BigDecimal scoringParameterB;
    @Column(name = "scoring_parameter_c", nullable = false)
    private BigDecimal scoringParameterC;
    @Column(name = "scoring_unit_conversion_factor", nullable = false)
    private BigDecimal scoringUnitConversionFactor;
}
