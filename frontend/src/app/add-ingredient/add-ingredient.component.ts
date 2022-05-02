import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ingredient } from '../common/ingredient';
import { IngredientService } from '../services/ingredient.service';
import { RHIValidators } from '../validators/rhivalidators';


@Component({
  selector: 'app-add-ingredient',
  templateUrl: './add-ingredient.component.html',
  styleUrls: ['./add-ingredient.component.css']
})
export class AddIngredientComponent implements OnInit {

  ingredientForm: FormGroup;
  ingredientData: Ingredient;
  ingredientList: Ingredient[] =[];


  constructor(private fb: FormBuilder,
              private ingredientService: IngredientService,
              private httpClient: HttpClient) { }

  ngOnInit(): void {
     this.loadIngredient();



    this.ingredientForm = this.fb.group({
      name: ['', [Validators.required, RHIValidators.notOnlyWhitespace]],
      category: ['', Validators.required]
    })
  }

  get name(){
    return this.ingredientForm.get('name');
  }

  get category(){
    return this.ingredientForm.get('category');
  }

  onSubmit(){
    if(this.ingredientForm.invalid){
      this.ingredientForm.markAllAsTouched();
      return;
    }
    this.ingredientData = this.ingredientForm.value;

    this.ingredientService.saveIngredient(this.ingredientData).subscribe({
      next: response =>{
        alert("Data saved!");
        this.loadIngredient();
      },
      error: err =>{
        alert(err.error);
        console.log("error: ", err);
      }
    });
  }


  loadIngredient(){
    this.ingredientService.getAllIngredients().subscribe(
      data =>{
        this.ingredientList = data;
      }
    );
  }

}
