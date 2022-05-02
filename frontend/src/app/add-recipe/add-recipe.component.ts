import { HttpClient } from '@angular/common/http';
import { Component, IterableDiffers, OnInit } from '@angular/core';
import { Form, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Ingredient } from '../common/ingredient';
import { Recipe } from '../common/recipe';
import { IngredientService } from '../services/ingredient.service';

import { RecipeService } from '../services/recipe.service';
import { RHIValidators } from '../validators/rhivalidators';

@Component({
  selector: 'app-add-recipe',
  templateUrl: './add-recipe.component.html',
  styleUrls: ['./add-recipe.component.css']
})
export class AddRecipeComponent implements OnInit {

  recipeForm: FormGroup;
  recipeData: Recipe = new Recipe();

  imagePath: string;
  displayImage: any;
  uploadedImage: any;


  constructor(private httpClient: HttpClient,
              private recipeService: RecipeService,
              private ingredientService: IngredientService,
              private router: Router,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.recipeForm = new FormGroup({
      'name': new FormControl('', [Validators.required, Validators.minLength(2),Validators.maxLength(128), RHIValidators.notOnlyWhitespace]),
      'ingredients': new FormArray([]),
      'category': new FormArray([]),
      'description': new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(65535), RHIValidators.notOnlyWhitespace])

    });

    this.onAddIngredients();

    // console.log(this.recipeData.ingredients);
    
    
  }
  
  get name(){
    return this.recipeForm.get('name');
  }
  get ingredients(){
    return this.recipeForm.get('ingredients') as FormArray;
  }

  get category(){
    return this.recipeForm.get('category') as FormArray;
  }

  get description(){
    return this.recipeForm.get('description');
  }
  
  
  onAddIngredients(){
    const control = new FormControl(null, [Validators.required, Validators.minLength(2),Validators.maxLength(128), RHIValidators.notOnlyWhitespace ]);
    const dropdownControl = new FormControl('', Validators.required);
    // (<FormArray>this.recipeForm.get('ingredients')).push(control);
    this.ingredients.push(control);
    this.category.push(dropdownControl);
  }

  onFileChanged(event){
    console.log(event);
    const files = event.target.files;
    const fileSize = files[0].size;
    this.uploadedImage = event.target.files[0];
    // console.log("uploaded image: ",this.uploadedImage);

    if(fileSize > 2097152){ //2Mb
      alert("Please upload file less than 2 MB");
      return;
    }
    if(files.length === 0){
      return;
    }

    // const mimeType = files[0].type;
    // if(mimeType.match(/image\/*/) == null){
    //   this.imageMessage = "Only images are supported.";
    //   return;
    // }

    const reader = new FileReader();
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) =>{
      this.displayImage = reader.result;
    }
  }

  onSubmit(){
    console.log("into submit");
    if(this.recipeForm.invalid){
      console.log("error");
      this.recipeForm.markAllAsTouched();
      return;
    }

    this.recipeData.name = this.name.value;
    this.recipeData.description = this.description.value;

    let ingredientList: Ingredient[] = [];
  
    for(let i =0; i < this.ingredients.value.length; i++){
      let ingredient = new Ingredient(this.ingredients.value[i], this.category.value[i]);
      ingredientList.push(ingredient);
    }
    // this.recipeData.ingredients = ingredientList;


    // this.recipeData = this.recipeForm.value;
    console.log('recipeData: ', this.recipeData);

    const imageFormData : FormData = this.recipeService.createRecipeFormData(this.recipeData, this.uploadedImage);

    console.log("image form data: ", imageFormData);


    this.recipeService.saveRecipe(imageFormData).subscribe({
      next: response =>{
        // console.log(response);
        // console.log("response id : ", response.id);
        this.ingredientService.saveIngredients(JSON.stringify(ingredientList), response.id).subscribe({
          next: response =>{
            this.recipeForm.reset();
            this.router.navigateByUrl('/home');
          },
          error: err =>{
            alert(err.error);
          }
        });
      },
      error: err =>{
        alert(err.error);
      }
    });
    



  }

}
