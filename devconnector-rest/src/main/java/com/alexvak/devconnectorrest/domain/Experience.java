package com.alexvak.devconnectorrest.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String company;
    private String location;

    @Column(nullable = false)
    private LocalDate startFrom;
    private LocalDate endAt;
    private Boolean currentJob;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private UserProfile profile;


}
