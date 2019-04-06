package com.alexvak.devconnectorrest.domain;

import com.alexvak.devconnectorrest.domain.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "profiles")
public class UserProfile extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 40)
    private String nickname;

    @Column(length = 100)
    private String company;

    @Column(length = 100)
    private String website;

    @Column(length = 100)
    private String location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @Lob
    private String bio;

    @Column(unique = true, length = 50)
    private String githubusername;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "profile_skills",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile", fetch = FetchType.EAGER)
    private Set<Experience> experiences = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
