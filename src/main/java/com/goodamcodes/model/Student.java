package com.goodamcodes.model;

import com.goodamcodes.model.security.OTP;
import com.goodamcodes.model.security.UserInfo;
import jakarta.persistence.*;
import lombok.*;

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
    private College college;

    private String registrationNumber;

    private int electionYear;

    private String imageUrl;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private OTP otpResetCode;
}
