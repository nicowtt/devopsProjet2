import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MaterialModule } from '../../shared/material.module';
import { UserService } from '../../core/service/user.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { AuthService } from '../../core/service/auth.service';
import { delay } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, MaterialModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  private userService = inject(UserService);
  private formBuilder = inject(FormBuilder);
  private authService = inject(AuthService);
  private destroyRef = inject(DestroyRef);
  private router = inject(Router);

  loginForm: FormGroup = new FormGroup({});
  submitted = false;
  isLoading: boolean = false;
  loginSuccess: boolean = false;
  errorMessage: string = '';

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      login: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  get form() {
    return this.loginForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    this.errorMessage = ''; // reset
    if (this.loginForm.invalid) return;

    this.isLoading = true;

    const loginRequestDTO = {
      login: this.form['login'].value,
      password: this.form['password'].value,
    };

    this.userService.login(loginRequestDTO)
      .pipe(
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: (token) => {
          this.isLoading = false;
          this.authService.saveToken(token);
          this.loginSuccess = true;
          // this.router.navigate(['/home']);
        },
        error: (err) => {
          this.isLoading = false;
          if (err.status === 401) {
            this.errorMessage = 'Invalid login or password';
          } else {
            this.errorMessage = 'An error occurred, please try again';
          }

          setTimeout(() => {
            this.errorMessage = '';
          }, 2000);
        }
      });
  }

  onReset(): void {
    this.submitted = false;
    this.loginForm.reset();
  }
}
