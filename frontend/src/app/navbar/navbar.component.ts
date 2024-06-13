import {Component, Input, OnInit} from '@angular/core';
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-navbar',
  standalone: true,
    imports: [
        NgIf
    ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit{
  isAuthenticated: boolean = false;
  username = '';

  ngOnInit(): void {
    // Controlla se l'utente è autenticato
    this.isAuthenticated = this.checkAuthentication();
    const retrieveUsername = typeof window !== 'undefined' ? localStorage.getItem('username') : null
    if (retrieveUsername !== null) {
      this.username = retrieveUsername.toString();
    }
  }

  // Funzione per controllare se l'utente è autenticato
  private checkAuthentication(): boolean {
    // Verifica se è presente un token nel localStorage
    const authToken = typeof window !== 'undefined' ? localStorage.getItem('token') : null;
    return (authToken !== null); // Restituisce true se è presente un token, altrimenti false
  }

  logout(): void {
    // Rimuovi il token dal localStorage
    localStorage.removeItem('token');
    this.isAuthenticated = false;
  }
}
