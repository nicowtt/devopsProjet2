import { Routes } from '@angular/router';
import {RegisterComponent} from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { StudentListComponent } from './pages/student-list/student-list.component';
import { StudentDetailsComponent } from './pages/student-details/student-details.component';
import { StudentCreateComponent } from './pages/student-create/student-create.component';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'register',
    pathMatch: 'full'
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'students',
    component: StudentListComponent,
    canActivate: [authGuard]
  },
  {
    path: 'student/create',
    component: StudentCreateComponent,
    canActivate: [authGuard]
  },
  {
    path: 'student/:uuid',
    component: StudentDetailsComponent,
    canActivate: [authGuard]
  }
];
