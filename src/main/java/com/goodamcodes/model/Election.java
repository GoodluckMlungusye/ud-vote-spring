package com.goodamcodes.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.goodamcodes.util.TimeZones;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.*;
import java.util.List;

@Entity
@Table(name = "elections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Election {

    private static final ZoneId TIME_ZONE = TimeZones.TANZANIA_ZONE;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Category> categories;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Instant getStartInstant() {
        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
        ZonedDateTime zonedStart = startDateTime.atZone(TIME_ZONE);
        return zonedStart.toInstant();
    }

    public Instant getEndInstant() {
        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);
        ZonedDateTime zonedEnd = endDateTime.atZone(TIME_ZONE);
        return zonedEnd.toInstant();
    }

    public boolean hasStarted() {
        return Instant.now().isAfter(getStartInstant());
    }

    public boolean hasEnded() {
        return Instant.now().isAfter(getEndInstant());
    }

}
