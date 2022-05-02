package com.rih.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode
@Getter
@Setter
@Entity
@ToString
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false, unique = true)
    private String name;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(name="recipe_ingredient", joinColumns = @JoinColumn(name="recepie_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    @JsonIgnoreProperties("recipes")
    private Set<Ingredient> ingredients;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 128)
    private String photoUrl;

    public void add(Ingredient ingredient){
        if(ingredient != null){
            if(this.ingredients == null){
                this.ingredients = new HashSet<>();
            }
        }
        this.ingredients.add(ingredient);
        ingredient.getRecipes().add(this);
    }

    public void remove(Long ingredientId){
        Ingredient existingIngredient = this.ingredients.stream().filter(ingredient -> ingredient.getId() == ingredientId).findFirst().orElse(null);
        if(existingIngredient !=null )this.ingredients.remove(existingIngredient);
        existingIngredient.getRecipes().remove(this);

    }


}
