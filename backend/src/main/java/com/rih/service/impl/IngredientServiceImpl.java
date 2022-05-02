package com.rih.service.impl;

import com.rih.entity.Ingredient;
import com.rih.entity.Recipe;
import com.rih.exception.IngredientAlreadyExistException;
import com.rih.exception.IngredientNotFoundException;
import com.rih.exception.RecipeNotFoundException;
import com.rih.exception.ResourceNotFoundException;
import com.rih.repository.IngredientRepository;
import com.rih.repository.RecipeRepository;
import com.rih.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;
    private RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Ingredient findByName(String name) {
        return ingredientRepository.findByName(name)
                .orElseThrow(() -> new IngredientNotFoundException());
    }

    @Override
    public Ingredient saveIngredient(Ingredient ingredient){
        Optional<Ingredient> existingIngredient = ingredientRepository.findByName(ingredient.getName());
        if(!existingIngredient.isPresent()){
            return ingredientRepository.save(ingredient);
        }
        throw new IngredientAlreadyExistException();
    }

    @Override
    public void saveIngredients(Set<Ingredient> ingredients, Long recipeId){
        Optional<Recipe> recipe = this.recipeRepository.findById(recipeId);
        Recipe existingRecipe = null;
        if(recipe.isPresent()){
            existingRecipe = recipe.get();
        }else{
            throw new RecipeNotFoundException();
        }

        for (Ingredient ingredient: ingredients) {
            // if ingredient exists
            String ingredientName = ingredient.getName();
//            Ingredient existingIngredient = this.ingredientRepository.findByName(ingredientName).orElseThrow(() -> new IngredientNotFoundException());
            Optional<Ingredient> existingIngredient = this.ingredientRepository.findByName(ingredientName);

            if(existingIngredient.isPresent()){
                existingRecipe.add(existingIngredient.get());
                this.recipeRepository.save(existingRecipe);
            }else{
                existingRecipe.add(ingredient);
                this.ingredientRepository.save(ingredient);
            }
        }
    }


    /**
     * matching algorithm!
     * todo: optimize
     */
    public List<Recipe> ingredientMatcher(List<Ingredient> ingredients){
        List<String> matchList = new ArrayList<>();
        ingredients.forEach(ingredient -> {
            matchList.add(ingredient.getName());
        });

        List<Recipe> recipes = this.recipeRepository.findAll();
        Map<Recipe, Integer> recipeMap = new HashMap<>();

        for(int i = 0; i < recipes.size(); i++){
            int count = 0;
            Set<Ingredient> ingredientList = recipes.get(i).getIngredients();
            for(Ingredient ingredient: ingredientList){
                if(matchList.contains(ingredient.getName())){
                    count++;
                }
            }
            recipeMap.put(recipes.get(i), count);
        }

//        for(Map.Entry<Recipe, Integer> entry: recipeMap.entrySet()){
//            System.out.println("key: "+entry.getKey().getName()+", value: "+entry.getValue());
//        }



        List<Recipe> sortedRecipe = sortAndConvertToList(recipeMap);
//        System.out.println("After converting to arrayList:");
//        sortedRecipe.forEach(r-> System.out.println(r.getName()));
//        System.out.println("=====");
        return sortedRecipe;
    }

    private List<Recipe> sortAndConvertToList(Map<Recipe, Integer> recipes){
        Map<Recipe, Integer> sortedRecipe = recipes.entrySet()
                .stream()
                .sorted((i1, i2)-> i2.getValue().compareTo(i1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue)-> oldValue, LinkedHashMap::new
                ));

//        System.out.println("After sorting: ...");
//        for(Map.Entry<Recipe, Integer> entry: sortedRecipe.entrySet()){
//            System.out.println("key: "+entry.getKey()+", value: "+entry.getValue());
//        }

//        System.out.println("Now converting to arrayList");


        return new ArrayList<>(sortedRecipe.keySet());
    }

}





