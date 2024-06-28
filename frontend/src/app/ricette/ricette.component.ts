import { Component, OnInit } from '@angular/core';
import { Ricetta } from '../models/ricetta.model';
import { RicettaService } from '../services/ricetta.service';
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-ricette',
  templateUrl: './ricette.component.html',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  styleUrls: ['./ricette.component.css']
})
export class RicetteComponent implements OnInit {

  ricette!: Ricetta[];

  constructor(private ricettaService: RicettaService) { }

  ngOnInit(): void {
    this.caricaRicette();
  }

  caricaRicette(): void {
    this.ricettaService.getAllRicette()
      .subscribe(
        (data) => {
          this.ricette = data.map(ricetta => ({
            ...ricetta,
            immagineUrl: this.convertiBase64ToUrl(ricetta.immagine)
          }));
        },
        (error) => {
          console.error('Errore nel caricamento delle ricette:', error);
        }
      );
  }

  convertiBase64ToUrl(base64: string): string {
    const byteCharacters = atob(base64);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: 'image/jpeg' });
    return URL.createObjectURL(blob);
  }
}
