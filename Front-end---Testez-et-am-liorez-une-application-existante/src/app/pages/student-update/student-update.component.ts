import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MaterialModule } from '../../shared/material.module';
import { StudentService } from '../../core/service/student.service';
import { StudentDTO } from '../../core/models/student.model';

@Component({
  selector: 'app-student-update',
  standalone: true,
  imports: [CommonModule, MaterialModule, ReactiveFormsModule],
  templateUrl: './student-update.component.html',
  styleUrl: './student-update.component.css'
})
export class StudentUpdateComponent implements OnInit {
  private studentService = inject(StudentService);
  private formBuilder = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private destroyRef = inject(DestroyRef);

  student: StudentDTO | null = null;
  updateForm: FormGroup = this.formBuilder.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
  });

  submitted = false;
  isLoading = false;
  isSaving = false;
  errorMessage = '';
  successMessage = '';

  get form() {
    return this.updateForm.controls;
  }

  ngOnInit(): void {
    const uuid = this.route.snapshot.paramMap.get('uuid');
    if (!uuid) {
      this.router.navigate(['/students']);
      return;
    }

    this.isLoading = true;
    this.studentService.getStudentByUuid(uuid)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (data) => {
          this.student = data;
          this.updateForm.patchValue({
            firstName: data.firstName,
            lastName: data.lastName,
            email: data.email,
          });
          this.isLoading = false;
        },
        error: () => {
          this.errorMessage = 'Failed to load student details.';
          this.isLoading = false;
        }
      });
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.updateForm.invalid || !this.student) return;

    this.isSaving = true;
    this.errorMessage = '';
    this.successMessage = '';

    const uuid = this.student.uuid;

    this.studentService.updateStudent(uuid, this.updateForm.value)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => {
          this.router.navigate(['/student', uuid]);
        },
        error: (err: HttpErrorResponse) => {
          this.errorMessage = err.error?.message ?? 'Failed to update student. Please try again.';
          this.isSaving = false;
          setTimeout(() => this.errorMessage = '', 3000);
        }
      });
  }

  goBack(): void {
    this.router.navigate(['/students']);
  }
}
