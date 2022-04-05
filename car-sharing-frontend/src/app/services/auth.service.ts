import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  URL = 'http://localhost:8080/api'

  constructor(private http: HttpClient) { }

  registerUser(user: User) {
    return this.http.post(`${URL}/create`, user)
  }
}
