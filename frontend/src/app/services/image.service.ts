import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  private apiUrl = 'http://localhost:8080/images';

  constructor(private http: HttpClient) { }

  getImageById(id: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${id}`, { responseType: 'blob' });
  }

  getImageByUserId(userId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/user/${userId}`, { responseType: 'blob' });
  }

  uploadImageForUser(userId: number, file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiUrl}/upload/${userId}`, formData);
  }

  uploadImageForRecipe(recipeId: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiUrl}/upload/recipe/${recipeId}`, formData);
  }

  deleteImage(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
