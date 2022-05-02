import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Role } from '../common/role';
import { AuthService } from '../services/auth.service';
import { TokenStorageService } from '../services/token-storage.service';
import { RHIValidators } from '../validators/rhivalidators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  signForm: FormGroup;
  isLoggedIn: boolean = false;
  isLoginFailed: boolean = false;
  roles: Set<Role> = new Set<Role>();

  loggedInUserFirstName: string;

  constructor(private authService: AuthService,
    private router: Router,
    private tokenStorage: TokenStorageService) { 
this.signForm = new FormGroup({
      'email': new FormControl('', [Validators.required, Validators.minLength(9), Validators.maxLength(128), RHIValidators.notOnlyWhitespace ]),
      'password': new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(128), RHIValidators.notOnlyWhitespace ])
  })
}

ngOnInit(): void {
  // this.isLoggedIn = !! this.tokenStorage.getToken();
  // if(this.isLoggedIn){
  //   this.router.navigateByUrl("/users");
  //   // const user = this.tokenStorage.getUser();
  //   // this.loggedInUserFirstName = user.firstName;
  //   // // this.roles = user.roles;

  // }

  if(this.tokenStorage.getToken()){
    this.isLoggedIn = true;
    this.roles = this.tokenStorage.getUser().roles;
  }
}

get email(){
  return this.signForm.get('email');
}

get password(){
  return this.signForm.get('password');
}

onSubmit(): void{
  console.log("inside login button")
  this.authService.login(this.signForm.value).subscribe(
    data =>{
      console.log("data: ", data);
      this.tokenStorage.saveToken(data.token);
      this.tokenStorage.saveUser(data);
      this.isLoginFailed = false;
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
      // this.router.navigateByUrl("/home");
      this.reloadPage();
      

    },
    err =>{
      this.modelBodymessage = err.error.message;
      this.openPopup();
      

    }
  );
  
}

reloadPage(): void{
  window.location.reload();
}
      /**
     *  code for bootstrap model
     */
       modelHeaderMessage = "Message";
       modelBodymessage = "";
       displayStyle = "none";
       redirectFlag: boolean = false;
    
       openPopup() {
         this.displayStyle = "block";
        //  this.router.navigateByUrl("/users");
         
       }
       closePopup(redirectFlag) {
         this.displayStyle = "none";
         this.signForm.reset();
       }
  
       // bootstrap model ends here



}
