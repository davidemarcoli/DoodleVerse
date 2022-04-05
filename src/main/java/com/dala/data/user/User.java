package com.dala.data.user;

import com.dala.security.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
import javax.persistence.*;

@Entity
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;
    private String name;
    @JsonIgnore
    private String hashedPassword;
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;
    @Lob
    private String profilePictureUrl;
}
