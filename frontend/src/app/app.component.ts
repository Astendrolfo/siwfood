import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RicettaService } from "./services/ricettasvc";
import { Ricetta } from "./models/ricetta";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgForOf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent implements OnInit {
  constructor(private serveRicette: RicettaService) { }

  title = 'siwfood';
  ricettaCorrente?: Ricetta;
  listaRicetteCorrente?: Ricetta[];

  ngOnInit(): void {
    this.serveRicette.getById(1).subscribe({
      next: data => { this.ricettaCorrente = data; },
      error: err => { console.log("Errore:", err); },
      complete: () => { console.log("Richiesta completata."); }
    });

    this.serveRicette.getListaRicette().subscribe({
      next: data => { this.listaRicetteCorrente = data; },
      error: err => { console.log("Errore:", err); },
      complete: () => { console.log("Richiesta completata."); }
    });
  }
}
