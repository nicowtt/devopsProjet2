import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MaterialModule } from '../../shared/material.module';
import { StudentService } from '../../core/service/student.service';
import { AuthService } from '../../core/service/auth.service';
import { StudentListDTO } from '../../core/models/student.model';

@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [CommonModule, MaterialModule, RouterModule],
  templateUrl: './student-list.component.html',
  styleUrl: './student-list.component.css'
})
export class StudentListComponent implements OnInit {
  private studentService = inject(StudentService);
  private authService = inject(AuthService);
  private destroyRef = inject(DestroyRef);
  private router = inject(Router);

  students: StudentListDTO[] = [];
  isLoading = false;
  errorMessage = '';

  displayedColumns = ['firstName', 'lastName', 'actions'];

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.studentService.getStudents()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (data) => {
          this.students = data;
          this.isLoading = false;
        },
        error: () => {
          this.errorMessage = 'Failed to load students. Please try again.';
          this.isLoading = false;
        }
      });
  }

  removeStudent(uuid: string, firstName: string, lastName: string): void {
    const confirmed = window.confirm(`Remove ${firstName} ${lastName}?`);
    if (!confirmed) return;

    this.studentService.deleteStudent(uuid)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.students = this.students.filter(s => s.uuid !== uuid);
        },
        error: () => {
          this.errorMessage = 'Failed to delete student. Please try again.';
        }
      });
  }

  logout(): void {
    this.authService.removeToken();
    this.router.navigate(['/login']);
  }
}
