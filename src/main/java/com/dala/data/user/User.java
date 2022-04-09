package com.dala.data.user;

import com.dala.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "application_user")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String name;

    @JsonIgnore
    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Lob
    private String profilePictureUrl;
}
