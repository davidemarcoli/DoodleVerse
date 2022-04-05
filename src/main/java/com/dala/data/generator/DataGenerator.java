package com.dala.data.generator;

import com.dala.data.user.User;
import com.dala.data.user.UserRepository;
import com.dala.security.role.Role;
import com.dala.security.role.RoleRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
@Log4j2
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            log.info("Generating demo data");

            List<Role> roles = roleRepository.findAll();
            log.info(roles);

            Role userRole = roleRepository.findRoleByName("USER");

            if (userRole == null) {
                userRole = new Role();
                userRole.setName("USER");
                userRole = roleRepository.save(userRole);
            }

            Role adminRole = roleRepository.findRoleByName("ADMIN");

            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ADMIN");
                adminRole = roleRepository.save(adminRole);
            }

            log.info("... generating 2 User entities...");
            User user = new User();
            user.setName("Davide Marcoli");
            user.setUsername("davide");
            user.setHashedPassword(passwordEncoder.encode("davide"));
            user.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            user.setRoles(List.of(userRole));
            userRepository.save(user);
            User admin = new User();
            admin.setName("Lazar Petrović");
            admin.setUsername("lazar");
            admin.setHashedPassword(passwordEncoder.encode("lazar"));
            admin.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
//            admin.setRoles(Set.of(Role.USER, Role.ADMIN));
            admin.setRoles(List.of(adminRole));
            userRepository.save(admin);



            log.info("Generated demo data");
        };
    }

}