package ee.tp.interview_assignments.decathlon.decathlon_service.dao.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventType {
    @SuppressWarnings("unused")
    public enum Environment {
        TRACK,
        FIELD
    }

    @SuppressWarnings("unused")
    public enum PerformanceUnit {
        TIME,
        SHORT_DISTANCE,
        LONG_DISTANCE
    }

    @Id
    private String name;
    @Column(nullable = false)
    private String displayName;
    @Column(nullable = false)
    private Environment environment;
    @Column(nullable = false)
    private PerformanceUnit performanceUnit;
    @Column(nullable = false)
    private BigDecimal scoringParameterA;
    @Column(nullable = false)
    private BigDecimal scoringParameterB;
    @Column(nullable = false)
    private BigDecimal scoringParameterC;
}
