import {Ricetta} from '../models/Ricetta'
import {Injectable, OnInit} from '@angular/core';
import {Observable, of} from 'rxjs';
import { HttpClient } from '@angular/common/http';


import {Ricette} from "../endpoints";

@Injectable({
  providedIn: 'root'
})

export class RicettaService{
  constructor(private http: HttpClient) { }

  public getById(id: number): Observable<Ricetta> {
    return this.http.get<Ricetta>(Ricette.getById + '/' + id);
  }

  public getListaRicette() : Observable<Ricetta[]> {
    return this.http.get<Ricetta[]>(Ricette.getList);
  }
}
