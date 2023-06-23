package com.rih.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import java.util.Set;


@Builder
@Getter
@Setter
public class SignUpRequest {

    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;

    private String password;
}
