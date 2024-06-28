import {RicettaModel} from '../models/ricetta.model'
import {Injectable, OnInit} from '@angular/core';
import {Observable, of} from 'rxjs';
import { HttpClient } from '@angular/common/http';


import {Ricette} from "../endpoints";

@Injectable({
  providedIn: 'root'
})

export class RicettaService{
  constructor(private http: HttpClient) { }

  public getById(id: number): Observable<RicettaModel> {
    return this.http.get<RicettaModel>(Ricette.getById + '/' + id);
  }

  public getListaRicette() : Observable<RicettaModel[]> {
    return this.http.get<RicettaModel[]>(Ricette.getList);
  }
}
