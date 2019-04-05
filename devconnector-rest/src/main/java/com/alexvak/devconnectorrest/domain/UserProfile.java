package com.alexvak.devconnectorrest.domain;

import com.alexvak.devconnectorrest.domain.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "profiles", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nickname")
})
public class UserProfile extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String nickname;

    private String company;
    private String website;
    private String location;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Status status;

    private String bio;
    private String githubusername;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "profile_skills",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills = new HashSet<>();

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
}
