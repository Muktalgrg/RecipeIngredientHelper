import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Recipe } from '../common/recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  private baseUrl = 'http://ec2-3-27-173-145.ap-southeast-2.compute.amazonaws.com:8081/rih';
  // private baseUrl = 'http://localhost:8080/rih';

  
  constructor(private httpClient: HttpClient) { }


  saveRecipe(formData: FormData): Observable<any>{
    return this.httpClient.post<FormData>(this.baseUrl+'/recipes/save', formData);
  }

  getRecipeList(): Observable<Recipe[]>{
    return this.httpClient.get<Recipe[]>(this.baseUrl+'/recipes/list');
  }

  getRecipeById(id: number): Observable<Recipe>{
    return this.httpClient.get<Recipe>(this.baseUrl+"/recipes/"+id);
  }

  getMatchedRecipe(ingredients: String):Observable<Recipe[]>{
    return this.httpClient.post<Recipe[]>(this.baseUrl+"/ingredients/match" ,ingredients);
  }

  
  createRecipeFormData(recipeData: Recipe, uploadedImage: any): FormData {
    console.log(recipeData+" "+ uploadedImage);
    const formData = new FormData();
    formData.append('name', recipeData.name);
    // formData.append('ingredients', JSON.stringify(recipeData.ingredients));
    formData.append('image', uploadedImage);
    formData.append('description', recipeData.description);

    console.log(formData);


    return formData;
  }



}
