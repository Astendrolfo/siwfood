import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ricetta } from '../models/ricetta.model';

@Injectable({
  providedIn: 'root'
})

export class RicettaService {

  private baseUrl = 'http://localhost:8080/api/ricette';

  constructor(private http: HttpClient) { }

  getAllRicette(): Observable<Ricetta[]> {
    return this.http.get<Ricetta[]>(this.baseUrl);
  }
}
