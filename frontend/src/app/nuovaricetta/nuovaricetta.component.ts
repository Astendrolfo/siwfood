import { Component } from '@angular/core';
import {Ricetta} from "../models/ricetta.model";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "../services/auth.service";
import {ImageService} from "../services/image.service";

@Component({
  selector: 'app-nuovaricetta',
  standalone: true,
  imports: [
    NgIf,
    FormsModule,
    NgForOf
  ],
  templateUrl: './nuovaricetta.component.html',
  styleUrl: './nuovaricetta.component.css'
})
export class NuovaricettaComponent {
  ricetta: Ricetta = {
    authorId: this.authService.getUserId() ?? 0,
    authorName: '',
    description: '',
    immagine1: '',
    immagine2: '',
    immagine3: '',
    listaIngredienti: [],
    title: '',
    immagineUrl: ''
  };
  idNuovaRicetta: number | null = null;
  charLimitExceeded: boolean = false;
  imageUrl: string | ArrayBuffer | null | undefined = null;
  selectedFile: File | null = null;
  formSubmitted: boolean = false;
  success: boolean = false;

  constructor(private http: HttpClient, protected authService: AuthService, private imageService: ImageService) {
  }

  limitCharacters(event: Event) {
    const maxChars = 100;
    const input = event.target as HTMLInputElement;
    if (input.value.length > maxChars) {
      input.value = input.value.substring(0, maxChars);
      this.ricetta.description = input.value;
      this.charLimitExceeded = true;
    } else {
      this.charLimitExceeded = false;
    }
  }

  onImageSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = (e) => {
        this.imageUrl = e.target?.result;
      };
      reader.readAsDataURL(file);
    }
  }

  aggiungiIngrediente() {
    this.ricetta.listaIngredienti.push({nome: '', quantita: ''});
  }

  rimuoviIngrediente(index: number) {
    this.ricetta.listaIngredienti.splice(index, 1);
  }

  submitForm() {
    this.formSubmitted = true;
    if (this.formIsValid()) {
      const url = 'http://localhost:8080/api/ricette/addricetta';
      const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
      const body = JSON.stringify(this.ricetta);

      this.http.post(url, body, { headers })
        .subscribe({
          next: (response: any) => {
            console.log('Dati inviati con successo!');
            this.success = true;
            this.idNuovaRicetta = response.id;
            console.log(this.idNuovaRicetta);
            if (this.selectedFile && this.idNuovaRicetta) {
              this.imageService.uploadImageForRecipe(this.idNuovaRicetta, this.selectedFile)
                .subscribe(response => {
                  console.log('Upload successful', response);
                });
            }
          },
          error: (error) => {
            console.error('Errore durante l\'invio dei dati:', error);
          }
        });
    } else {
      console.log('Form non valido, controlla i campi!');
    }
  }

  formIsValid() :boolean {
    return this.ricetta.title.trim() !== '' &&
      this.ricetta.description.trim() !== '' &&
      this.ricetta.listaIngredienti.every(ingrediente =>
        ingrediente.nome.trim() !== '' &&
        ingrediente.quantita !== null &&
        ingrediente.tipoQuantita !== '');
  }
}

