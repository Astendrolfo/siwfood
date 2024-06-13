import {inject} from "@angular/core";
import {CanActivateFn, Router} from "@angular/router";

export const authGuard: (protectedRoutes: string[]) => CanActivateFn = (protectedRoutes: string[]) => {
  return () => {
    const router = inject(Router);
    const currentRoute = router.url;
    console.log(currentRoute);
    const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null
    if (token) return true; // Ho un token, accedo alla pagina e verifico il token.
    else if (protectedRoutes.includes(currentRoute)) {
        router.navigateByUrl("/login").then();
      }
    console.log(router.url)
    return false;

  };
}
const protectedRoutes: string[] = ['/home', '/profile'];
export const canActivate = authGuard(protectedRoutes);
