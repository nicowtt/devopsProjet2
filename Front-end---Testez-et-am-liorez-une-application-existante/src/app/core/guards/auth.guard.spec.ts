import { TestBed } from '@angular/core/testing';
import { Router, UrlTree } from '@angular/router';

import { authGuard } from './auth.guard';
import { AuthService } from '../service/auth.service';

describe('authGuard', () => {
  let authServiceMock: jest.Mocked<AuthService>;
  let routerMock: jest.Mocked<Router>;

  beforeEach(() => {
    authServiceMock = {
      isLoggedIn: jest.fn()
    } as any;

    routerMock = {
      createUrlTree: jest.fn()
    } as any;

    TestBed.configureTestingModule({
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock },
      ]
    });
  });

  it('should allow access when user is logged in', () => {
    // GIVEN
    authServiceMock.isLoggedIn.mockReturnValue(true);

    // WHEN
    const runGuard = TestBed.runInInjectionContext(() => authGuard({} as any, {} as any));

    // THEN
    expect(runGuard).toBe(true);
  });

  it('should redirect to /login when user is not logged in', () => {
    // GIVEN
    authServiceMock.isLoggedIn.mockReturnValue(false);
    const toLoginUrl = { toString: () => '/login' } as UrlTree;
    routerMock.createUrlTree.mockReturnValue(toLoginUrl);

    // WHEN
    const runGuard = TestBed.runInInjectionContext(() => authGuard({} as any, {} as any));

    // THEN
    expect(routerMock.createUrlTree).toHaveBeenCalledWith(['/login']);
    expect(runGuard).toBe(toLoginUrl);
  });
});
