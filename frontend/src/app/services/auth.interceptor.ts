import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private TOKEN_HEADER_KEY = 'Authorization';

  constructor(private token: TokenStorageService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq = req;
    const token = this.token.getToken();
    if(token != null){
      authReq = req.clone({headers: req.headers.set(this.TOKEN_HEADER_KEY, 'Bearer '+token)});
    }
    return next.handle(authReq);
  }
}
