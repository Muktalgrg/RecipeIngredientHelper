package com.rih.service;

import com.rih.entity.Ingredient;
import com.rih.entity.Recipe;

import java.util.List;
import java.util.Set;

public interface IngredientService {

    Ingredient findByName(String name);
    Ingredient saveIngredient(Ingredient ingredient);
    void saveIngredients(Set<Ingredient> ingredients, Long recipeId);

    public List<Recipe> ingredientMatcher(List<Ingredient> ingredients);

}
