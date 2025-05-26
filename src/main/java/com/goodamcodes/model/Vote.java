package com.goodamcodes.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "votes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="contestant_id")
    private Contestant contestant;

    @ManyToOne(optional=false)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(optional=false)
    @JoinColumn(name="voter_id")
    private Student voter;

    @ManyToOne(optional=false)
    @JoinColumn(name="election_id")
    private Election election;

    @CreationTimestamp
    private Instant castAt;
}
