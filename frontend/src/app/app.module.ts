import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';

import { AddIngredientComponent } from './add-ingredient/add-ingredient.component';
import { AddRecipeComponent } from './add-recipe/add-recipe.component';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { RecipeDetailComponent } from './recipe-detail/recipe-detail.component';
import { RecipeFinderComponent } from './recipe-finder/recipe-finder.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { AuthInterceptor } from './services/auth.interceptor';


const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'recipes/:id', component: RecipeDetailComponent},
  {path: 'addRecipe', component: AddRecipeComponent},
  {path: 'addIngredient', component: AddIngredientComponent},
  {path: 'match/:id', component: RecipeFinderComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: '**', redirectTo: '/home', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    AddRecipeComponent,
    AddIngredientComponent,
    HomeComponent,
    RecipeDetailComponent,
    RecipeFinderComponent,
    RegisterComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    CommonModule,
    RouterModule.forRoot(routes)
    
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi:true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
