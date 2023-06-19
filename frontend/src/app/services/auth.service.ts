import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtResponse } from '../common/jwt-response';
import { LoginRequest } from '../common/login-request';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // ROOT_URL:String = 'http://recipeingredienthelper-env.eba-kmynniwm.ap-southeast-2.elasticbeanstalk.com/';
  ROOT_URL:String = 'http://localhost:8080/';

  private AUTH_API = this.ROOT_URL+'rih/api/auth';
  private httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }; 

  constructor(private http: HttpClient) { }

  register(registerData: any): Observable<any>{
    return this.http.post<any>(this.AUTH_API+"/signup",registerData);
  }

  login(loginRequest: LoginRequest): Observable<JwtResponse>{
    return this.http.post<JwtResponse>(this.AUTH_API+'/signin', loginRequest, this.httpOptions);
  }



}
