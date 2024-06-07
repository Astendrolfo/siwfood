import {inject} from "@angular/core";
import {CanActivateFn, Router} from "@angular/router";

export const authGuard: (protectedRoutes: string[]) => CanActivateFn = (protectedRoutes) => {
  return () => {
    const router = inject(Router);
    const currentRoute = router.url;
    const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null
    if (token !== null) return true; // Ho un token, accedo alla pagina e verifico il token.
    else {
      /*
      --> Route protetta e non ho il Token --> vai al login
       */
      if (protectedRoutes.includes(currentRoute)) {
        router.navigateByUrl("/login").then();
      }
      else return false;
    }
    return false;
  };
}
const protectedRoutes: string[] = ['/dashboard', '/profile'];
export const canActivate = authGuard(protectedRoutes);
