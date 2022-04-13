import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GetUserResponse } from '../models/GetUserResponse';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = 'http://localhost:8080/api/user'

  constructor(private http: HttpClient) { }

  getUser(): Observable<GetUserResponse> {
    return this.http.get<GetUserResponse>(`${this.url}/get`)
  }

}
