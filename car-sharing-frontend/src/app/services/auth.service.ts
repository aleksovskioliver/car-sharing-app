import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { AuthenticationRequest } from '../models/AuthenticationRequest';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  url = 'http://localhost:8080'

  constructor(private http: HttpClient) { }

  registerUser(user: User) {
    return this.http.post(`${this.url}/api/user/create`, user)
  }

  loginUser(req: AuthenticationRequest) {
    return this.http.post(`${this.url}/authenticate`, req).pipe(
      tap(it => this.setSession(it)),
      catchError(error => throwError(() => new Error(error.error)))
    )
    
  }

  logout() {
    localStorage.removeItem("id_token");
  }

  private setSession(authResult: any) {
    localStorage.setItem('id_token', authResult.jwt);
  }
}
