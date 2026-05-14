import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MaterialModule } from '../../shared/material.module';
import { StudentService } from '../../core/service/student.service';
import { StudentDTO } from '../../core/models/student.model';

@Component({
  selector: 'app-student-details',
  standalone: true,
  imports: [CommonModule, MaterialModule, RouterModule],
  templateUrl: './student-details.component.html',
  styleUrl: './student-details.component.css'
})
export class StudentDetailsComponent implements OnInit {
  private studentService = inject(StudentService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private destroyRef = inject(DestroyRef);

  student: StudentDTO | null = null;
  isLoading = false;
  errorMessage = '';

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
          this.isLoading = false;
        },
        error: () => {
          this.errorMessage = 'Failed to load student details.';
          this.isLoading = false;
        }
      });
  }

  goBack(): void {
    this.router.navigate(['/students']);
  }
}
