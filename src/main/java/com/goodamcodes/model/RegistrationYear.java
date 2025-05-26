package com.goodamcodes.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(
        name = "registration_years",
        uniqueConstraints = @UniqueConstraint(columnNames = {"year", "college_id"}),
        indexes = { @Index(name = "idx_year_college", columnList = "year, college_id")}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "registered_students", nullable = false)
    private Integer registeredStudents;

    @Column(name = "voted_students", nullable = false)
    private Integer votedStudents;

    @ManyToOne(optional = false)
    @JoinColumn(name = "college_id")
    private College college;

    @Transient
    public double getParticipationRating() {
        if (registeredStudents == null || registeredStudents == 0) return 0.0;
        return (votedStudents * 100.0) / registeredStudents;
    }

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
