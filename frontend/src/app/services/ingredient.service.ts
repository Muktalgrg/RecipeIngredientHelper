import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Ingredient } from '../common/ingredient';

@Injectable({
  providedIn: 'root'
})
export class IngredientService {
  private baseUrl = 'http://ec2-3-27-173-145.ap-southeast-2.compute.amazonaws.com:8081/rih';
  // private baseUrl = 'http://localhost:8080/rih';


  constructor(private httpClient: HttpClient) { }


  getAllIngredients(): Observable<Ingredient[]>{
    return this.httpClient.get<Ingredient[]>(this.baseUrl+'/ingredients');
  }

  saveIngredient(ingredientData: Ingredient):Observable<Ingredient>{
    return this.httpClient.post<Ingredient>(this.baseUrl+'/ingredients/save',ingredientData);

  } 

  // @PostMapping("/save/{recipeId}/ingredient")
  saveIngredients(ingredients: String, recipeId: number): Observable<any>{
    return this.httpClient.post<String>(this.baseUrl+`/ingredients/save/${recipeId}/ingredient`, ingredients);
  }




}
