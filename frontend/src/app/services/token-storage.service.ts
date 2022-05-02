import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  private TOKEN_KEY = "auth-token";
  private USER_KEY = 'auth-user';
  
  storage: Storage = sessionStorage;

  constructor() { }

  signOut(){
    this.storage.clear();
    // window.sessionStorage.clear();
  }

  public saveToken(token: string): void{
    this.storage.removeItem(this.TOKEN_KEY);
    this.storage.setItem(this.TOKEN_KEY, token);
  }

  public getToken(): string | null{
    return this.storage.getItem(this.TOKEN_KEY);
  }

  public saveUser(user: any): void{
    this.storage.removeItem(this.USER_KEY);
    this.storage.setItem(this.USER_KEY, JSON.stringify(user));
    }

  public getUser(): any{
    const user = this.storage.getItem(this.USER_KEY);
    if(user){
      return JSON.parse(user);
    }
    return {};
  }



}
