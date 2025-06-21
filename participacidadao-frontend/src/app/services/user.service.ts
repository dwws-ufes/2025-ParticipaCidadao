import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

export interface User {
    name: string;
    email: string;
    password: string;
    cpf: string;
    birthDate: string;
    role: string;
}

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private apiUrl = 'http://localhost:8080/users';

    constructor(private http: HttpClient) {}

    register(user: User): Observable<User> {
        return this.http.post<any>(`${this.apiUrl}/new`, user);
    }

}