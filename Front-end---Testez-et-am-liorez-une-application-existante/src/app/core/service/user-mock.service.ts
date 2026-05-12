import {RegisterDTO} from '../models/register.model';
import {Observable, of} from 'rxjs';


export class UserMockService {

  register(user: RegisterDTO): Observable<Object> {
    return of();
  }
}
