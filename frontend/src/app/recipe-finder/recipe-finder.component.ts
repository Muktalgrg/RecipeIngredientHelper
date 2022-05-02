import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Ingredient } from '../common/ingredient';
import { Recipe } from '../common/recipe';
import { RecipeService } from '../services/recipe.service';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-recipe-finder',
  templateUrl: './recipe-finder.component.html',
  styleUrls: ['./recipe-finder.component.css']
})
export class RecipeFinderComponent implements OnInit {
  selectedRecipe: Recipe = new Recipe();
  matchedRecipe: Recipe[] = [];
  ingredientList: String[] = [];

  constructor(private route: ActivatedRoute,
              private recipeService: RecipeService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(
      () =>{
        this.setRecipe();
      }
      );
      
    }
    setRecipe(){
      const recipeId: number = +this.route.snapshot.paramMap.get('id');
      this.recipeService.getRecipeById(recipeId).subscribe(
        data =>{
          this.selectedRecipe = data;
          this.setMatchedRecipe();
        // console.log(this.selectedRecipe);
      }
    );
  }

  setMatchedRecipe(){
    this.recipeService.getMatchedRecipe(JSON.stringify(this.selectedRecipe.ingredients)).subscribe(
      data =>{
        this.matchedRecipe = data;
        console.log("data", data);
        this.setIngredientList();
      }
    );
  }

  setIngredientList(){
    this.selectedRecipe.ingredients.forEach( ingredient =>{
      this.ingredientList.push(ingredient.name);
    });

    this.matchedRecipe.forEach(recipe =>{
      recipe.ingredients.forEach(ingredient => {
        if(!this.ingredientList.includes(ingredient.name)){
          this.ingredientList.push(ingredient.name);
        }
        // else{
        //   console.log(this.ingredientList+" :contains: ", ingredient.name);
        // }

      });
    });


  }

  removeItem(ingredient: String){
    const index =  this.ingredientList.findIndex(i => i === ingredient);
    this.ingredientList.splice(index, 1);
    // this.setIngredientList();

  }

  downloadList(){
    let shoppingList = '';
    this.ingredientList.forEach(ingredient => {
      shoppingList += ingredient +'\n';
    });
    console.log(shoppingList);


    const blob = new Blob([shoppingList], { type: 'text/json; charset=utf-8' });
    const url = window.URL.createObjectURL(blob);
    window.open(url);
    // window.location.reload();
    fileSaver.saveAs(blob, 'shoppingList.txt');


  }


}
