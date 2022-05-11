import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HOST } from "src/app/shared/components/pages/constant/http.constant";
import { AuthorsRequest } from "../request/authors.request";
import { AuthorsResponse } from "../response/authors.response";


@Injectable({
  providedIn: 'root'
})
export class AuthorsService {

  constructor(private http: HttpClient) { }

  public createAuthors(request: AuthorsRequest): Observable<boolean> {
    return this.http.post<boolean>(HOST + "/author", request)
  }

  public getAuthors(): Observable<AuthorsResponse>{
    return this.http.get<AuthorsResponse>(HOST + "author/list")
  }

  public updateAuthors(authorId: number, request: AuthorsRequest): Observable<boolean> {
    return this.http.put<boolean>(HOST + `author/${authorId}`, request)
  }

  public deleteAuthors(authorId: number): Observable<boolean>{
    return this.http.delete<boolean>(HOST + `author/${authorId}`)
  }

  public getAuthorName(authorId: number): Observable<boolean> {
    return this.http.get<boolean>(HOST + `author/name/${authorId}`)
  }
  
}