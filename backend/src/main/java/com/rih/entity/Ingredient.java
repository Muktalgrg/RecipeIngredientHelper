package com.rih.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//import javax.persistence;


@Getter
@Setter
//@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@ToString
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @Column(length = 128, nullable = false)
    private ECatogery category;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST},
    mappedBy = "ingredients")
    @JsonIgnoreProperties("ingredients")
    @JsonIgnore
    private Set<Recipe> recipes = new HashSet<>();

    public Ingredient(String name, ECatogery category) {
        this.name = name;
        this.category = category;
    }

    //    private Recipe recipe;


}
