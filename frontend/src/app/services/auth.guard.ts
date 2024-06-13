import { CanActivateFn, Router } from '@angular/router';
import { inject, Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
class PermissionsService {
  isLoggedIn(): boolean {
    const authToken = typeof window !== 'undefined' ? localStorage.getItem('token') : null;
    return (authToken !== null);
  }
}

export const authGuard: CanActivateFn = (route, state) => {
  const permissionsService = inject(PermissionsService);
  const router = inject(Router);

  if (!permissionsService.isLoggedIn()) {
    router.navigate(['/login']);
    return false;
  }
  return true;
};
