package com.rih.repository;

import com.rih.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Modifying
    @Query("update Recipe r set r.photoUrl = :photoUrl WHERE r.id = :recipeId")
    void setRecipePhotoUrl(@Param("photoUrl") String photoUrl, @Param("recipeId") Long recipeId);

    Optional<Recipe> findByName(String name);

}
