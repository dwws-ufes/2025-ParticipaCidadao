import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";


@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private apiUrl = 'http://localhost:8080/users';
    private credentials: { email: string, password: string } | null = null;
    private currentUser: any = null;

    constructor(private http: HttpClient) { }

    login(email: string, password: string) {
        this.credentials = { email, password };
        const headers = new HttpHeaders({
            Authorization: 'Basic ' + btoa(`${email}:${password}`)
        });
        // Fetch user info after login
        return this.http.get(`${this.apiUrl}/auth/check`, { headers });
    }

    setCurrentUser(user: any) {
        this.currentUser = user;
    }

    getCurrentUser(): any {
        return this.currentUser;
    }

    getAuthHeaders(): HttpHeaders | null {
        if (!this.credentials) return null;
        return new HttpHeaders({
            Authorization: 'Basic ' + btoa(`${this.credentials.email}:${this.credentials.password}`)
        });
    }
}