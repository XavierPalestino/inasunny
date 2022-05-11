import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HOST } from "src/app/shared/components/pages/constant/http.constant";
import { BooksDTO } from "../dto/book.dto";
import { BooksRequest } from "../request/book.request";


@Injectable({
    providedIn: 'root'
})
export class BooksService {

    constructor(private http: HttpClient) { }

    public createBooks(request: BooksRequest): Observable<boolean> {
      return this.http.post<boolean>(HOST + "book", request)
    }
  
    public getBook(API_URL: string): Observable<[BooksDTO[]]>{
      return this.http.get<[BooksDTO[]]>(HOST + API_URL)
    }
  
    public updateBooks(bookId: number, request: BooksRequest): Observable<boolean> {
      return this.http.put<boolean>(HOST + `book/${bookId}`, request)
    }
  
    public deleteBooks(bookId: number): Observable<boolean>{
      return this.http.delete<boolean>(HOST + `book/${bookId}`)
    }

}