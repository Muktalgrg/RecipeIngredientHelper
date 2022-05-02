import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Recipe } from '../common/recipe';
import { RecipeService } from '../services/recipe.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  recipeList: Recipe[] = [];

  constructor(private recipeService: RecipeService, private router: Router) { }

  ngOnInit(): void {
    this.recipeService.getRecipeList().subscribe(
      data =>{
        console.log(data);
        this.recipeList = data;
      });


  }

  matchFinder(id: number){
    // console.log("id clicked: ",id);
    this.router.navigateByUrl(`/match/${id}`);
  }

}
