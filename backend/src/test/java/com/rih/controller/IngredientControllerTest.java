package com.rih.controller;

import com.rih.entity.ECatogery;
import com.rih.entity.Ingredient;
import com.rih.entity.Recipe;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class IngredientControllerTest {


    @Test
    public void test1(){

        List<String> matchList = Arrays.asList("tomato", "celery", "rice", "thyme", "garlic");

        Set<Ingredient> s1 = new HashSet<>();
        Ingredient i1 = Ingredient.builder()
                .name("tomato").category(ECatogery.vegetable).build();
        Ingredient i2 = Ingredient.builder()
                .name("avocado").category(ECatogery.vegetable).build();
        Ingredient i3 = Ingredient.builder()
                .name("rice").category(ECatogery.others).build();
        Ingredient i4 = Ingredient.builder()
                .name("garlic").category(ECatogery.vegetable).build();
        Ingredient i5 = Ingredient.builder()
                .name("thyme").category(ECatogery.vegetable).build();

        s1.add(i1);
        s1.add(i2);
        s1.add(i3);
        s1.add(i4);
        s1.add(i5);

        Recipe r1 = Recipe.builder()
                .name("recipe1")
                .description("someting")
                .photoUrl("something1")
                .ingredients(s1).build();

        // "avocado","sweet potato","badam", "garlic","rice"
        Set<Ingredient> s2 = new HashSet<>();
        Ingredient i11 = Ingredient.builder()
                .name("capsicum").category(ECatogery.vegetable).build();
        Ingredient i22 = Ingredient.builder()
                .name("avocado").category(ECatogery.vegetable).build();
        Ingredient i33 = Ingredient.builder()
                .name("rice").category(ECatogery.others).build();
        Ingredient i44 = Ingredient.builder()
                .name("celery").category(ECatogery.vegetable).build();
        Ingredient i55 = Ingredient.builder()
                .name("cauli").category(ECatogery.vegetable).build();

        s2.add(i11);
        s2.add(i22);
        s2.add(i33);
        s2.add(i44);
        s2.add(i55);

        Recipe r2 = Recipe.builder()
                .name("recipe2")
                .description("someting")
                .photoUrl("something1")
                .ingredients(s2).build();


        // nothing matching
        Set<Ingredient> s3 = new HashSet<>();
        Ingredient i111 = Ingredient.builder()
                .name("tomato1").category(ECatogery.vegetable).build();
        Ingredient i222 = Ingredient.builder()
                .name("avocado1").category(ECatogery.vegetable).build();
        Ingredient i333 = Ingredient.builder()
                .name("rice1").category(ECatogery.others).build();
        Ingredient i444 = Ingredient.builder()
                .name("garlic1").category(ECatogery.vegetable).build();
        Ingredient i555 = Ingredient.builder()
                .name("thyme1").category(ECatogery.vegetable).build();

        s3.add(i111);
        s3.add(i222);
        s3.add(i333);
        s3.add(i444);
        s3.add(i555);

        Recipe r3 = Recipe.builder()
                .name("recipe3")
                .description("someting")
                .photoUrl("something1")
                .ingredients(s3).build();

        // avocado,
        Set<Ingredient> s4 = new HashSet<>();
        Ingredient i1111 = Ingredient.builder()
                .name("carrot").category(ECatogery.vegetable).build();
        Ingredient i2222 = Ingredient.builder()
                .name("avocado").category(ECatogery.vegetable).build();
        Ingredient i3333 = Ingredient.builder()
                .name("onion").category(ECatogery.others).build();
        Ingredient i4444 = Ingredient.builder()
                .name("parsley").category(ECatogery.vegetable).build();
        Ingredient i5555 = Ingredient.builder()
                .name("cabbage").category(ECatogery.vegetable).build();

        s4.add(i1111);
        s4.add(i2222);
        s4.add(i3333);
        s4.add(i4444);
        s4.add(i5555);

        Recipe r4 = Recipe.builder()
                .name("recipe4")
                .description("someting")
                .photoUrl("something1")
                .ingredients(s4).build();

        // avocado,
        Set<Ingredient> s5 = new HashSet<>();
        Ingredient i11111 = Ingredient.builder()
                .name("corriender").category(ECatogery.vegetable).build();
        Ingredient i22222 = Ingredient.builder()
                .name("avocado").category(ECatogery.vegetable).build();
        Ingredient i33333 = Ingredient.builder()
                .name("rice").category(ECatogery.others).build();
        Ingredient i44444 = Ingredient.builder()
                .name("honey").category(ECatogery.vegetable).build();
        Ingredient i55555 = Ingredient.builder()
                .name("ketchup").category(ECatogery.vegetable).build();

        s5.add(i11111);
        s5.add(i22222);
        s5.add(i33333);
        s5.add(i44444);
        s5.add(i55555);

        Recipe r5 = Recipe.builder()
                .name("recipe5")
                .description("someting")
                .photoUrl("something1")
                .ingredients(s5).build();

        Map<Recipe, Integer> myMap = new HashMap<>();
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(r1);
        recipes.add(r2);
        recipes.add(r3);
        recipes.add(r4);
        recipes.add(r5);

//        recipes.forEach(recipe -> {
//            recipe.getIngredients().forEach(r-> System.out.println(r.getName()));
//        });
//
//        System.out.println("-------------------------------------------");

        for (int i = 0; i < recipes.size(); i++){
            int count = 0;
            Set<Ingredient> ingredientList = recipes.get(i).getIngredients();
            for(Ingredient ingredient : ingredientList){
                if(matchList.contains(ingredient.getName())){
                    System.out.println("match found for: "+ingredient.getName());
                    count++;
                }
            }
            myMap.put(recipes.get(i), count);

            System.out.println();
        }

        System.out.println("---------------");

        for(Map.Entry<Recipe, Integer> entry: myMap.entrySet()){
            Recipe key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key.getName()+" count: "+value);
        }

       Map<Recipe, Integer> sortedRecipe =  myMap.entrySet()
               .stream()
                .sorted((l1,l2)-> l2.getValue().compareTo(l1.getValue()))

                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new
                ));

        List<Recipe> l1 = new ArrayList<>(sortedRecipe.keySet());
        l1.forEach(System.out::println);

//        List<Recipe> sortedRecipe =   myMap.entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByValue())
//                .collect(Collectors.toList(
//                        new LinkedList<Recipe>()
//                ));
////                .map()
////                .forEach(System.out::println);




    }

}