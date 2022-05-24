import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HOST } from 'src/app/shared/components/pages/constant/http.constant';
import { SalesDTO } from '../dto/sale.dto';
import { BooksRequest } from '../request/sale.request';


@Injectable({
    providedIn: 'root'
})
export class BooksService {

    constructor(private http: HttpClient) { }

    public createSales(request: BooksRequest): Observable<boolean> {
      return this.http.post<boolean>(HOST + 'book', request);
    }

    public getSales(API_URL: string): Observable<[SalesDTO[]]>{
      return this.http.get<[SalesDTO[]]>(HOST + API_URL);
    }

    public updateSales(bookId: number, request: BooksRequest): Observable<boolean> {
      return this.http.put<boolean>(HOST + `book/${bookId}`, request);
    }

    public deleteSales(bookId: number): Observable<boolean>{
      return this.http.delete<boolean>(HOST + `book/${bookId}`);
    }

}
