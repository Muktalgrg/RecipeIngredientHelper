import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { RHIValidators } from '../validators/rhivalidators';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  isLoggedIn: boolean = false;
  // registerRequest: RegisterRequest = new RegisterRequest();
  

  constructor(private router: Router, 
              private authService: AuthService) { }

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      'firstName': new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(128), RHIValidators.notOnlyWhitespace]),
      'lastName': new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(128), RHIValidators.notOnlyWhitespace]),
      'email': new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(128), RHIValidators.notOnlyWhitespace]),
      'password': new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(64), RHIValidators.notOnlyWhitespace])
    });
  }

  get firstName(){
    return this.registerForm.get('firstName');
  }

  get lastName(){
    return this.registerForm.get('lastName');
  }

  get email(){
    return this.registerForm.get('email');
  }
  get password(){
    return this.registerForm.get('password');
  }


  onSubmit(){
    if(this.registerForm.invalid){
      this.registerForm.markAllAsTouched();
      return;
    }

    console.log(this.registerForm.value);
    this.authService.register(this.registerForm.value).subscribe(
      data =>{
        console.log(data);
        alert("User sucessfully registered!")
        // user registration successfull.
        // reload page or redirect to login page.
        this.reloadPage();
      },
      err =>{
        console.log("error msg from server: "+err.error.message);
        alert(err.error.message);
      }
    );

  }
  reloadPage(): void{
    window.location.reload();
  }

  

}
