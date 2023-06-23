package com.rih.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rih.entity.Ingredient;
import com.rih.entity.Recipe;
import com.rih.service.IngredientService;
import com.rih.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

//    public static final String IMAGE_FOLDER_LOCATION = System.getProperty("user.home") + "/RecipeIngredientHelper/frontend/src/assets/images";

    private RecipeService recipeService;


    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/list")
//    @PreAuthorize("hasAnyRole('USER', 'USER_ROLE', 'ROLE_USER', 'user', 'role_user')")
    public List<Recipe> getAllRecipe(){
    	
        return this.recipeService.getAllRecipe();
    }

    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable("id")Long id){
        return this.recipeService.getRecipeById(id);
    }

    /*
     * 
//    @PostMapping("/save")
//    @ResponseStatus(HttpStatus.CREATED)
    public Recipe saveRecipe(@RequestParam("name") String name,
//                           @RequestParam("ingredients")String ingredients,
                           @RequestParam(name = "image", required = false)MultipartFile multipartFile,
                           @RequestParam("description")String description) throws IOException {

//        Set<Ingredient> ingredientList = new ObjectMapper().readValue(ingredients, new TypeReference<Set<Ingredient>>(){});

        Recipe recipe = Recipe.builder().name(name.trim())
                .description(description.trim()).build();

        return this.recipeService.saveRecipe(recipe, multipartFile);
    }
    
    */
    
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Recipe saveRecipe1(@RequestParam("name") String name,
//                           @RequestParam("ingredients")String ingredients,
                           @RequestParam(name = "image", required = false)MultipartFile multipartFile,
                           @RequestParam("description")String description) throws IOException {

//        Set<Ingredient> ingredientList = new ObjectMapper().readValue(ingredients, new TypeReference<Set<Ingredient>>(){});
//    	ingredients are saved using different API.
    	
        Recipe recipe = Recipe.builder().name(name.trim())
                .description(description.trim()).build();
        
        

        return this.recipeService.saveRecipe1(recipe, multipartFile);
    }




}
