import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { AuthService } from "./auth.service";
import { Observable } from "rxjs";
import User from "../models/User";


@Injectable({
    providedIn: 'root'
})
export class UserService {

    private apiUrl = 'http://localhost:8080/users';


    constructor(private http: HttpClient, private authService: AuthService) { }

    register(user: User): Observable<any> {
        return this.http.post(`${this.apiUrl}/new`, user);
    }

    updateUser(id: number, user: User): Observable<any> {
        const headers = this.authService.getAuthHeaders();
        if (!headers) throw new Error('Usuário não autenticado');
        return this.http.put(`${this.apiUrl}/${id}`, user, { headers });
    }

    getUsers(): Observable<any[]> {
    const headers = this.authService.getAuthHeaders();
    if (!headers) throw new Error('Not authenticated');
    return this.http.get<any[]>(this.apiUrl, { headers });
  }

}