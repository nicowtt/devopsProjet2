import { Component, DestroyRef, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MaterialModule } from '../../shared/material.module';
import { StudentService } from '../../core/service/student.service';

@Component({
  selector: 'app-student-create',
  standalone: true,
  imports: [CommonModule, MaterialModule, ReactiveFormsModule],
  templateUrl: './student-create.component.html',
  styleUrl: './student-create.component.css'
})
export class StudentCreateComponent {
  private studentService = inject(StudentService);
  private formBuilder = inject(FormBuilder);
  private router = inject(Router);
  private destroyRef = inject(DestroyRef);

  createForm: FormGroup = this.formBuilder.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
  });

  submitted = false;
  isLoading = false;
  errorMessage = '';

  get form() {
    return this.createForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.createForm.invalid) return;

    this.isLoading = true;
    this.errorMessage = '';

    this.studentService.createStudent(this.createForm.value)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.router.navigate(['/students']);
        },
        error: (err: HttpErrorResponse) => {
          this.errorMessage = err.error?.message ?? 'Failed to create student. Please try again.';
          this.isLoading = false;
          setTimeout(() => this.errorMessage = '', 3000);
        }
      });
  }

  onCancel(): void {
    this.submitted = false;
    this.createForm.reset();
  }

  onBackToStudents(): void {
    this.router.navigate(['/students']);
  }
}
