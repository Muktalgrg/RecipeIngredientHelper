package com.rih.service;

import com.rih.entity.Ingredient;
import com.rih.entity.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface RecipeService {

    Recipe saveRecipe(Recipe recipe, MultipartFile multipartFile) throws IOException;
    Recipe saveRecipe1(Recipe recipe, MultipartFile multipartFile) throws IOException;
    List<Recipe> getAllRecipe();

    Recipe getRecipeById(Long id);
}
