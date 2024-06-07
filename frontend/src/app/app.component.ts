import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { RicettaService } from "./services/ricettasvc";
import { Ricetta } from "./models/ricetta";
import {NgForOf} from "@angular/common";
import {HomeComponent} from "./home/home.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HomeComponent, CommonModule, RouterOutlet, RouterLink, RouterLinkActive, NgForOf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent implements OnInit {
  constructor(private serveRicette: RicettaService) { }

  title = 'siwfood';

  ngOnInit(): void {
  }
}
