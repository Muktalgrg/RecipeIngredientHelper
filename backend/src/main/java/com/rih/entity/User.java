package com.rih.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128, nullable = false, unique = true)
    private String email;

    @JsonIgnoreProperties
    private String password;

    @Column(name="first_name", length = 45, nullable = false)
    private String firstName;

    @Column(name="last_name", length = 45, nullable = false)
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
            @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name="user_id"),
                inverseJoinColumns = @JoinColumn(name="role_id"))
    Set<Role> roles = new HashSet<>();

    public User() {
    }



    public void addRole(Role role){
        if(roles == null){
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }

    public void removeRole(Role role){
        if(this.roles.contains(role))
            this.roles.remove(role);
    }


}
