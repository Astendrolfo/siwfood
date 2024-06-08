import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  isAuthenticated: boolean = false;

  constructor(private router: Router) { }

  ngOnInit(): void {
    // Controlla se l'utente è autenticato
    this.isAuthenticated = this.checkAuthentication();
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
    this.isAuthenticated = this.checkAuthentication();
  }
}
