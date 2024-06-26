import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private isAuthenticated: boolean = false;
  private username: string | null = null;
  private loginUrl = 'http://localhost:8080/login';

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = { username, password };

    return this.http.post<any>(this.loginUrl, body, { headers })
      .pipe(
        map(response => {
          return response;
        })
      );
  }

  logout() {
    // Simulazione di un processo di logout (rimozione del token, azzeramento delle variabili, ecc.)
    this.isAuthenticated = false;
    this.username = null;
  }

  isAuthenticatedUser(): boolean {
    return this.isAuthenticated;
  }

  getUsername(): string | null {
    return this.username;
  }
}
