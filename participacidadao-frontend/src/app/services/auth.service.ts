import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";


@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private apiUrl = 'http://localhost:8080/users';
    private credentials: { email: string, password: string } | null = null;

    constructor(private http: HttpClient) { }

    login(email: string, password: string) {
        this.credentials = { email, password };
        const headers = new HttpHeaders({
            Authorization: 'Basic ' + btoa(`${email}:${password}`)
        });
        return this.http.get(`${this.apiUrl}/auth/check`, { headers });
    }

    getAuthHeaders(): HttpHeaders | null {
        if (!this.credentials) return null;
        return new HttpHeaders({
            Authorization: 'Basic ' + btoa(`${this.credentials.email}:${this.credentials.password}`)
        });
    }
}