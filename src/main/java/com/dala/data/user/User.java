package com.dala.data.user;

import com.dala.security.Role;
import com.dala.data.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Set;
import javax.persistence.*;

@Entity
@Data
@Table(name = "application_user")
public class User extends AbstractEntity {

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
