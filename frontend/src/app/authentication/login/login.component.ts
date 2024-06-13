import {Component, OnInit} from '@angular/core';
import { FormsModule } from "@angular/forms";
import { LoginService } from '../../services/login.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import {NavbarComponent} from "../../navbar/navbar.component";
import {AuthService} from "../../services/authservice";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    NavbarComponent
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

  constructor(private loginService: LoginService, private router: Router, private authService : AuthService) { }

  login() {
    this.loginService.login(this.username, this.password).subscribe(
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
