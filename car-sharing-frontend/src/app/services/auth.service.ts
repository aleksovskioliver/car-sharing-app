import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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
    return this.http.post(`${this.url}/authenticate`, req)
  }
}
