import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NavbarComponent} from "../navbar/navbar.component";

@Component({
  selector: 'app-profile',
  standalone: true,
    imports: [NavbarComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }
}
