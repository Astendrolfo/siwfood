import {Component, OnInit} from '@angular/core';
import {Router, RouterLink, RouterOutlet} from "@angular/router";
import {NgIf} from "@angular/common";
import {HttpClient, HttpRequest} from "@angular/common/http";
import {NavbarComponent} from "../navbar/navbar.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NgIf,
    RouterLink,
    RouterOutlet,
    NavbarComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent{
  isAuthenticated: boolean = false;
  username = '';

  constructor(private router: Router, private http: HttpClient) { }
}
