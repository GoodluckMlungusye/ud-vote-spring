package com.goodamcodes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.goodamcodes.model.security.OTP;
import com.goodamcodes.model.security.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name="id")
    private UserInfo user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "college_id")
    @JsonBackReference
    private College college;

    private String registrationNumber;

    private Integer electionYear;

    private String imageUrl;

    @OneToMany(mappedBy = "voter", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Vote> votes;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private OTP otpCode;
}
