import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterOutlet, RouterLink, RouterLinkActive, Router} from '@angular/router';
import {NgForOf} from "@angular/common";
import {HomeComponent} from "./home/home.component";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HomeComponent, CommonModule, RouterOutlet, RouterLink, RouterLinkActive, NgForOf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent implements OnInit {
  isAuthenticated: boolean = false;
  username = '';

  title = 'siwfood';

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit(): void {
  }
}
