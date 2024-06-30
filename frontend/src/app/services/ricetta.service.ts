import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ricetta } from '../models/ricetta.model';
import {Ricette} from "../endpoints";
import {ImageService} from "./image.service";

@Injectable({
  providedIn: 'root'
})

export class RicettaService {

  private baseUrl = 'http://localhost:8080/api/ricette';

  constructor(private http: HttpClient, private imageService : ImageService) {
  }

  getAllRicette(): Observable<Ricetta[]> {
    return this.http.get<Ricetta[]>(this.baseUrl);
  }

  getRicetteMadeByUser(userId: number): Observable<Ricetta[]> {
    return this.http.get<Ricetta[]>(this.baseUrl + "/madeby/" + userId);
  }

  getRicettaById(id: number): Observable<Ricetta> {
    return this.http.get<Ricetta>(this.baseUrl + "/" + id);
  }

  deleteRicetta(id: number): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<void>(url);
  }

  modifyRicetta(ricetta: Ricetta, image: File): any {
    {
      const headers = new HttpHeaders({'Content-Type': 'application/json'});
      const body = JSON.stringify(ricetta);
      const url = `${this.baseUrl}/${ricetta.id}`;
      this.http.post(url, body, {headers})
        .subscribe({
          next: (response: any) => {
            console.log('Dati inviati con successo!');
            const idRicetta = response.id;
            if (image && idRicetta) {
              this.imageService.uploadImageForRecipe(idRicetta, image)
                .subscribe(response => {
                  console.log('Upload successful', response);
                });
            }
            return true;
          },
          error: (error) => {
            console.error('Errore durante l\'invio dei dati:', error);
            return false;
          }
        });
    }
  }
}
