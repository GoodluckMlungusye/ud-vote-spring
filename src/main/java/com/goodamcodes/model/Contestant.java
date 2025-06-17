package com.goodamcodes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contestants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contestant {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name="id")
    private Student student;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    private String slogan;
    private String videoUrl;
}