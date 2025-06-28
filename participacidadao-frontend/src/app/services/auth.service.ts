import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";


@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private apiUrl = 'http://localhost:8080/users';

    constructor(private http: HttpClient) {}

    login(email: string, password: string) {
        const headers = new HttpHeaders({
            Authorization: 'Basic ' + btoa(`${email}:${password}`)
        });

        return this.http.get(`${this.apiUrl}/auth/check`, { headers })
    }
}