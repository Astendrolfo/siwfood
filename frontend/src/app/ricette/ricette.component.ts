import { Component, OnInit } from '@angular/core';
import { Ricetta } from '../models/ricetta.model';
import { RicettaService } from '../services/ricetta.service';
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {ImageLoaderService} from "../services/image.loader.service";

@Component({
  selector: 'app-ricette',
  templateUrl: './ricette.component.html',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    NgOptimizedImage,
  ],
  styleUrls: ['./ricette.component.css']
})
export class RicetteComponent implements OnInit {

  ricette!: Ricetta[];

  constructor(private ricettaService: RicettaService, private imageLoaderService: ImageLoaderService) { }

  ngOnInit(): void {
    this.caricaRicette();
  }

  caricaRicette(): void {
    this.ricettaService.getAllRicette()
      .subscribe(
        (data) => {
          this.ricette = data.map(ricetta => ({
            ...ricetta,
            immagineUrl: this.loadImage(ricetta.immagine1)
          }));
        },
        (error) => {
          console.error('Errore nel caricamento delle ricette:', error);
        }
      );
  }

  loadImage(image :string) {
    return this.imageLoaderService.convertiBase64ToUrl(image);
  }
}
