import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import User from "../models/User";


@Injectable({
    providedIn: 'root'
})
export class UserService {

    private apiUrl = 'http://localhost:8080/users';

    constructor(private http: HttpClient) {}

    register(user: User): Observable<any> {
        return this.http.post(`${this.apiUrl}/new`, user);
    }

}