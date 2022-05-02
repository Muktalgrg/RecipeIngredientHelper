import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Recipe } from '../common/recipe';
import { RecipeService } from '../services/recipe.service';

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css']
})
export class RecipeDetailComponent implements OnInit {

  recipe: Recipe = new Recipe();

  constructor(private router: Router,
          private recipeService: RecipeService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(
      () =>{
        this.handleRecipeDetails();
      }
    )}

    handleRecipeDetails(){
      const recipeId: number = +this.route.snapshot.paramMap.get('id');
      this.recipeService.getRecipeById(recipeId).subscribe(
        data =>{
          this.recipe = data;
        }
      );

    }

    matchFinder(id: number){
      // console.log("id clicked: ",id);
      this.router.navigateByUrl(`/match/${id}`);
    }

}
