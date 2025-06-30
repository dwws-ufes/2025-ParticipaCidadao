import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class IssueService {
  private apiUrl = 'http://localhost:8080/issues';

  constructor(private http: HttpClient, private authService: AuthService) { }

  createIssue(issue: any): Observable<any> {
    const headers = this.authService.getAuthHeaders();
    if (!headers) throw new Error('Not authenticated');
    return this.http.post(`${this.apiUrl}/new`, issue, { headers });
  }

  getIssues(): Observable<any[]> {
    const headers = this.authService.getAuthHeaders();
    if (!headers) throw new Error('Not authenticated');
    return this.http.get<any[]>(this.apiUrl, { headers });
  }
}
