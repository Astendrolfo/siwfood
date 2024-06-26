import { inject } from "@angular/core";
import { CanActivateFn, Router, ActivatedRouteSnapshot } from "@angular/router";

export const authGuard: (protectedRoutes: string[]) => CanActivateFn = (protectedRoutes: string[]) => {
  return (route: ActivatedRouteSnapshot) => {
    const router = inject(Router);

    //Bisogna usare ActivatedRouteSnapshot per ottenere l'url successiva.

    /* Il guard viene attivato ancora prima di entrare nella componente, quindi se chiedessi l'url al router, mi
    restituirebbe l'url della componente precedente */

    const currentRoute = "/" + route.url.map(segment => segment.path).join();

    // Check del token senza andare in errore

    const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;

    if (token) {
      return true;
    } else if (protectedRoutes.includes(currentRoute)) {
      router.navigateByUrl("/login");
      return false;
    }
    return true;
  };
};

const protectedRoutes: string[] = ['/profile'];
export const canActivate = authGuard(protectedRoutes);
