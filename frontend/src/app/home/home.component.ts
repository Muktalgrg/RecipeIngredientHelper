import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
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

  constructor(private recipeService: RecipeService, private router: Router, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.recipeService.getRecipeList().subscribe(
      data =>{
        console.log(data);
        
        this.recipeList = data;


     
        
        this.recipeList.forEach(recipe =>{
          recipe["fileDb"].data = "data:"+recipe["fileDb"].type+";base64, "+recipe["fileDb"].data;
        });

        

      });

  }

  //Call this method in the image source, it will sanitize it.
  transform(image: string){
    return this.sanitizer.bypassSecurityTrustResourceUrl(image);
  }



  matchFinder(id: number){
    // console.log("id clicked: ",id);
    this.router.navigateByUrl(`/match/${id}`);
  }

}
