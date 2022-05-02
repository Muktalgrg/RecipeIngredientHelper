package com.rih.service.impl;

import com.rih.entity.Ingredient;
import com.rih.entity.Recipe;
import com.rih.exception.RecipeAlreadyExistException;
import com.rih.exception.RecipeNotFoundException;
import com.rih.repository.RecipeRepository;
import com.rih.service.RecipeService;
import com.rih.utility.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public static final String IMAGE_FOLDER_LOCATION = System.getProperty("user.home") + "/spring projects/RecipeIngredientHelper/frontend/src/assets/images/recipe";

    @Override
    @Transactional
    public Recipe saveRecipe(Recipe recipe, MultipartFile multipartFile) throws IOException {
        if(this.recipeRepository.findByName(recipe.getName()).isPresent()){
            throw new RecipeAlreadyExistException();
        }
        if(multipartFile != null){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            Recipe savedRecipe = this.recipeRepository.save(recipe);
            Long recipeId = savedRecipe.getId();
            String fileLocation = "/assets/images/recipe/"+recipeId+"/"+fileName;

            String uploadDir = IMAGE_FOLDER_LOCATION+ "/"+recipeId;
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            savedRecipe.setPhotoUrl(fileLocation);
            this.recipeRepository.save(savedRecipe);
            return savedRecipe;
        }
        return this.recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getAllRecipe() {
        return this.recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return this.recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException());
    }

}
