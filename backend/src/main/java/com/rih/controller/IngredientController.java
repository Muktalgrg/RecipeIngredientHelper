package com.rih.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.rih.entity.ECatogery;
import com.rih.entity.Ingredient;
import com.rih.entity.Message;
import com.rih.entity.Recipe;
import com.rih.repository.IngredientRepository;
import com.rih.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private IngredientRepository ingredientRepository;
    private IngredientService ingredientService;

    public IngredientController(IngredientRepository ingredientRepository, IngredientService ingredientService) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientService = ingredientService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Ingredient> getAllIngredient(){
        System.out.println("inside get all ingredients");
        return this.ingredientRepository.findAll();
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Ingredient saveIngredient(@RequestBody Ingredient ingredient){
        System.out.println("recived data: "+ ingredient);

        return this.ingredientService.saveIngredient(ingredient);
    }

    @PostMapping("/save/{recipeId}/ingredient")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveIngredients(@PathVariable("recipeId")Long recipeId, @RequestBody String ingredients) throws JsonProcessingException {
        Set<Ingredient> ingredientList = new ObjectMapper().readValue(ingredients, new TypeReference<Set<Ingredient>>(){});


//        System.out.println(ingredientList+" id: "+recipeId);
        ingredientList.forEach(System.out::println);

        this.ingredientService.saveIngredients(ingredientList, recipeId);

    }

    @PostMapping ("/match")
    @ResponseStatus(HttpStatus.OK)
    public List<Recipe> findIngredientMatch(@RequestBody String ingredients) throws JsonProcessingException {
        List<Ingredient> ingredientList = new ObjectMapper().readValue(ingredients, new TypeReference<List<Ingredient>>(){});


        List<Recipe> sortedRecipe = this.ingredientService.ingredientMatcher(ingredientList);


        System.out.println("----");
        List<Recipe> maximumMatchedRecipe = new ArrayList<>();
        for(int i = 1; i < 3; i++){ // ignoring 1st recipe which will be itself.
            if(sortedRecipe.size() < i)
                break;
            maximumMatchedRecipe.add(sortedRecipe.get(i));
        }
        return maximumMatchedRecipe;

    }

}
