package com.goodamcodes.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "elections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Election {

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
}
