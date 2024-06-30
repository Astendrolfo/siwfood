import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Location, NgForOf, NgIf} from '@angular/common';
import { RicettaService } from '../services/ricetta.service';
import { Ricetta } from '../models/ricetta.model';
import {ImageLoaderService} from "../services/image.loader.service";
import {FormsModule, NgForm} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-ricettadetail',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule
  ],
  templateUrl: './ricettadetail.component.html',
  styleUrl: './ricettadetail.component.css'
})
export class RicettadetailComponent implements OnInit{
  ricettaModificata: Ricetta = {
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
  ricetta!: Ricetta;
  editMode = false;
  formSubmitted : boolean = false;
  success: boolean = false;
  tipiQuantita: string[] = ['Grammi', 'Chilogrammi', 'Litri', 'Millilitri', 'Cucchiai', ' '];
  imagePreview: string | ArrayBuffer | null = null;
  selectedFile : any;


  constructor(
    private route: ActivatedRoute,
    private ricettaService: RicettaService,
    private location: Location,
    private imageLoaderService : ImageLoaderService,
    private authService : AuthService
  ) {}


  ngOnInit(): void {
    this.getRicettaId();
  }

  getRicettaId(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam !== null && idParam !== undefined) {
      const id = +idParam;
      if (!isNaN(id)) {
        this.caricaRicetta(id);
      } else {
        console.error('L\'ID non è un numero valido');
      }
    } else {
      console.error('L\'ID non è presente nei parametri della route');
    }
  }

  caricaRicetta(id: number): void {
    this.ricettaService.getRicettaById(id)
      .subscribe(
        (data) => {
          this.ricetta = {
            ...data,
            immagineUrl: this.loadImage(data.immagine1)
          };

          this.ricettaModificata = { ...this.ricetta };
        },
        (error) => {
          console.error('Errore nel caricamento della ricetta:', error);
        }
      );
  }

  loadImage(image :string) {
    return this.imageLoaderService.convertiBase64ToUrl(image);
  }

  toggleEditMode(): void {
    this.editMode = !this.editMode;
  }

  isAuthor(): boolean {
    // Verifica se l'utente corrente è l'autore della ricetta
    // Implementa la tua logica qui, ad esempio controllando l'id dell'autore
    const currentUserId = 1; // Sostituisci con l'id dell'utente corrente autenticato
    return this.ricetta?.authorId === currentUserId;
  }

  saveChanges(): void {
    console.log('Modifiche salvate:', this.ricetta);
    this.editMode = false;
    this.ricettaService.modifyRicetta(this.ricettaModificata, this.selectedFile);
  }

  cancelEdit(): void {
    this.editMode = false;
    this.getRicettaId();
  }

  addIngredient(): void {
    if (this.ricetta) {
      this.ricetta.listaIngredienti.push({ nome: '', quantita: '', tipoQuantita: '' });
    }
  }

  removeIngredient(index: number): void {
    if (this.ricetta && this.ricetta.listaIngredienti.length > index) {
      this.ricetta.listaIngredienti.splice(index, 1);
    }
  }

  isFormValid(form: NgForm): boolean {
    if (!form) {
      return false;
    }
    for (const controlName of Object.keys(form.controls)) {
      const control = form.controls[controlName];
      if (control.invalid || control.value === '') {
        return false;
      }
    }
    return true;
  }

  onImageSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      this.selectedFile = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
        this.ricettaModificata.immagine1 = reader.result as string;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }
}
