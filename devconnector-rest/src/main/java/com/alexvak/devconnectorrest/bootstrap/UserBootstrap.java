package com.alexvak.devconnectorrest.bootstrap;

import com.alexvak.devconnectorrest.domain.Skill;
import com.alexvak.devconnectorrest.domain.Status;
import com.alexvak.devconnectorrest.domain.User;
import com.alexvak.devconnectorrest.domain.UserProfile;
import com.alexvak.devconnectorrest.repository.ProfileRepository;
import com.alexvak.devconnectorrest.repository.SkillRepository;
import com.alexvak.devconnectorrest.repository.StatusRepository;
import com.alexvak.devconnectorrest.repository.UserRepository;
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

    public UserBootstrap(UserRepository userRepository, PasswordEncoder passwordEncoder,
                         StatusRepository statusRepository, ProfileRepository profileRepository,
                         SkillRepository skillRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.statusRepository = statusRepository;
        this.profileRepository = profileRepository;
        this.skillRepository = skillRepository;
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
            profileRepository.save(userProfile);

            newUser.setUserProfile(userProfile);

            userRepository.save(newUser);

        }

    }
}
