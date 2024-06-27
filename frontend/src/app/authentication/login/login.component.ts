import {Component, OnInit} from '@angular/core';
import { FormsModule } from "@angular/forms";
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent{
  username: string = '';
  password: string = '';
  message: string = '';
  success: boolean = false;
  requested: boolean = false;
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  login() {
    this.authService.login(this.username, this.password).subscribe(
      response => {
        this.message = response.message;
        this.success = response.success;
        this.requested = true;
        if (this.success) {
          localStorage.setItem('token', response.token);
          localStorage.setItem('username', response.username);
          setTimeout(() => {
            this.router.navigate(['/']).then(r => this.success && this.requested);
          }, 2000);
        }
      },
      error => {
        this.errorMessage = error.error.message;
        this.requested = true;
        this.success = false;
      }
    );
  }
}
