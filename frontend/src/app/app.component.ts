import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from './services/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  isLoggedIn: boolean = false;
  isLoggedOut: boolean = false;
  loggedInUserFirstName: string;

  
  roles: String[] = [];

  isAdmin:boolean = false;
  isUser: boolean = false;

  constructor(private route: ActivatedRoute, private tokenStorage: TokenStorageService, private router: Router){  
  }
  ngOnInit(): void {
    this.isLoggedIn = !! this.tokenStorage.getToken();
    if(this.isLoggedIn){
      const user = this.tokenStorage.getUser();
      this.loggedInUserFirstName = user.firstName;

      this.roles = user.roles;
      console.log("roles of logged user: ", this.roles);

      this.isAdmin = this.roles.includes('ROLE_ADMIN');
      this.isUser = this.roles.includes('ROLE_USER');
      // this.router.navigateByUrl("/home");

    }
  }

  logout(): void {
    this.tokenStorage.signOut();
    window.location.reload();
  }




}
