package com.rih.entity;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    //    private String type = "Bearer";
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;

}
