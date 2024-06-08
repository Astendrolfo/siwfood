import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    const url = 'http://localhost:8080/users/me';
    this.http.get<any>(url).subscribe(
      response => {
      },
      error => {
        console.error('Errore nella richiesta GET', error);
      }
    );
  }
}
