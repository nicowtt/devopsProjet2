import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StudentDTO, StudentListDTO, StudentSaveDTO } from '../models/student.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private httpClient = inject(HttpClient);
  private authService = inject(AuthService);

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`
    });
  }

  createStudent(student: StudentSaveDTO): Observable<StudentDTO> {
    return this.httpClient.post<StudentDTO>('/api/createStudent', student, { headers: this.getHeaders() });
  }

  updateStudent(uuid: string, student: StudentSaveDTO): Observable<StudentDTO> {
    return this.httpClient.put<StudentDTO>(`/api/updateStudent/${uuid}`, student, { headers: this.getHeaders() });
  }

  getStudentByUuid(uuid: string): Observable<StudentDTO> {
    return this.httpClient.get<StudentDTO>(`/api/getStudent/${uuid}`, { headers: this.getHeaders() });
  }

  getStudents(): Observable<StudentListDTO[]> {
    return this.httpClient.get<StudentListDTO[]>('/api/getStudentList', { headers: this.getHeaders() });
  }

  deleteStudent(uuid: string): Observable<void> {
    return this.httpClient.delete<void>(`/api/deleteStudent/${uuid}`, { headers: this.getHeaders() });
  }
}
