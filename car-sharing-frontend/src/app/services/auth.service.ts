import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
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
    return this.http.post(`${this.url}/authenticate`, req).subscribe({
      next: res => {
        this.setSession(res)
        console.log(res)
      }
    })
  }

  private setSession(authResult: any) {
    localStorage.setItem('id_token', authResult.jwt);
  }

  logout() {
    localStorage.removeItem("id_token");
  }
}
