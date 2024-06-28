import { Component } from '@angular/core';
import {NgClass, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    NgIf,
    FormsModule,
    NgClass
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  name: string = '';
  surname: string = '';
  dob: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  emailValid: boolean = true;

  constructor(private http: HttpClient) {}

  passwordsMatch(): boolean {
    return this.password === this.confirmPassword;
  }

  validateEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  register() {
    this.emailValid = this.validateEmail(this.email);

    if (this.passwordsMatch() && this.emailValid) {
      const userData = {
        nome: this.name,
        cognome: this.surname,
        dob: this.dob,
        email: this.email,
        password: this.password
      };

      this.http.post('http://localhost:8080/api/register', userData).subscribe(
        response => {
          console.log('Registrazione avvenuta con successo', response);
        },
        error => {
          console.error('Errore nella registrazione', error);
        }
      );
    } else {
      console.log('Form non valido, le password non combaciano o l\'email non è valida.');
    }
  }
}
