import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequestDTO } from '../models/login.model';
import { RegisterDTO } from '../models/register.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private httpClient: HttpClient) { }

  register(user: RegisterDTO): Observable<Object> {
    return this.httpClient.post('/api/register', user);
  }

  login(loginRequestDTO: LoginRequestDTO): Observable<string> {
    return this.httpClient.post('/api/login', loginRequestDTO, { responseType: 'text' });
  }
}
