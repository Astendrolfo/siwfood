import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public isAuthenticated(): boolean {
    const authToken = typeof window !== 'undefined' ? localStorage.getItem('token') : null;
    return (authToken !== null);
  }
}
