package com.dala.data.generator;

import com.dala.data.person.PersonRepository;
import com.dala.security.Role;
import com.dala.data.user.User;
import com.dala.data.user.UserRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.util.Collections;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
@Log4j2
public class DataGenerator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        return args -> {

            log.info("Generating demo data");
            log.info("... generating User entities...");

            if (!hasUser("davide")) {
                User user = new User();
                user.setName("Davide Marcoli");
                user.setUsername("davide");
                user.setHashedPassword(passwordEncoder.encode("davide"));
                user.setProfilePictureUrl(
                        "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
                user.setRoles(Collections.singleton(Role.USER));
                userRepository.save(user);
            }

            if (!hasUser("lazar")) {
                User admin = new User();
                admin.setName("Lazar Petrović");
                admin.setUsername("lazar");
                admin.setHashedPassword(passwordEncoder.encode("lazar"));
                admin.setProfilePictureUrl(
                        "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
//            admin.setRoles(Set.of(Role.USER, Role.ADMIN));
                admin.setRoles(Collections.singleton(Role.ADMIN));
                userRepository.save(admin);
            }

            log.info("Generated demo data");

            if (personRepository.count() < 10) {
                personRepository.saveAll(FakeGenerator.getInstance().generateRandomPersons(10));
            }
        };
    }

    public boolean hasUser(String username) {
        return userRepository.findByUsername(username) != null;
    }

}