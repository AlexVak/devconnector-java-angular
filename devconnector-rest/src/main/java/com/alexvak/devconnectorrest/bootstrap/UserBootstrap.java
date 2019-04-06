package com.alexvak.devconnectorrest.bootstrap;

import com.alexvak.devconnectorrest.domain.*;
import com.alexvak.devconnectorrest.repository.*;
import com.timgroup.jgravatar.Gravatar;
import com.timgroup.jgravatar.GravatarDefaultImage;
import com.timgroup.jgravatar.GravatarRating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@Profile({"default", "dev"})
public class UserBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StatusRepository statusRepository;
    private final ProfileRepository profileRepository;
    private final SkillRepository skillRepository;
    private final ExperienceRepository experienceRepository;

    public UserBootstrap(UserRepository userRepository, PasswordEncoder passwordEncoder,
                         StatusRepository statusRepository, ProfileRepository profileRepository,
                         SkillRepository skillRepository, ExperienceRepository experienceRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.statusRepository = statusRepository;
        this.profileRepository = profileRepository;
        this.skillRepository = skillRepository;
        this.experienceRepository = experienceRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (!userRepository.findByEmail("pfhh48u@gmail.com").isPresent()) {
            Gravatar gravatar = new Gravatar(200, GravatarRating.PARENTAL_GUIDANCE_SUGGESTED, GravatarDefaultImage.MYSTERY_MAN);
            User user = new User();
            user.setName("Alex");
            user.setEmail("pfhh48u@gmail.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setAvatar(gravatar.getUrl("pfhh48u@gmail.com"));
            User newUser = userRepository.save(user);

            Skill skill1 = new Skill("Java");
            Skill skill2 = new Skill("SQL");
            Skill skill3 = new Skill("Spring");
            Set<Skill> skillSet = new HashSet<>();
            skillSet.add(skill1);
            skillSet.add(skill2);
            skillSet.add(skill3);

            skillSet.forEach(skillRepository::save);

            Status userStatus = statusRepository.findByName("Software Engineer").get();

            UserProfile userProfile = new UserProfile();
            userProfile.setNickname("alexvak");
            userProfile.setStatus(userStatus);
            userProfile.setSkills(skillSet);
            userProfile.setUser(newUser);

            Experience experience = new Experience();
            experience.setTitle("Job1");
            experience.setCompany("Company1");
            LocalDate localDate = LocalDate.now();
            experience.setStartFrom(localDate.minusYears(2));
            experience.setEndAt(localDate.minusDays(31));
            experience.setCurrentJob(false);
            experience.setDescription("Job1 in Company1");
            experience.setProfile(userProfile);

            Experience experience2 = new Experience();
            experience2.setTitle("Job2");
            experience2.setCompany("Company2");
            experience2.setStartFrom(localDate.minusYears(5));
            experience2.setEndAt(localDate.minusYears(4));
            experience2.setCurrentJob(false);
            experience2.setDescription("Job2 in Company2");
            experience2.setProfile(userProfile);

            Set<Experience> experiences = new HashSet<>(Arrays.asList(experience, experience2));

            experiences.forEach(experienceRepository::save);

            userProfile.setExperiences(experiences);

            profileRepository.save(userProfile);

            newUser.setUserProfile(userProfile);

            userRepository.save(newUser);

        }

    }
}
