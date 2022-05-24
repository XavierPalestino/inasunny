import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HOST } from 'src/app/shared/components/pages/constant/http.constant';
import { ProductRequest } from '../request/productRequest';
import { ProductResponse } from '../response/product.response';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  public createProduct(request: ProductRequest): Observable<boolean> {
    return this.http.post<boolean>(HOST + 'product', request);
  }

  public getProduct(): Observable<ProductResponse>{
    return this.http.get<ProductResponse>(HOST + 'product/list');
  }

  public updateProduct(productID: number, request: ProductRequest): Observable<boolean> {
    return this.http.put<boolean>(HOST + `product/${productID}`, request);
  }

  public deleteProduct(productID: number): Observable<boolean>{
    return this.http.delete<boolean>(HOST + `product/${productID}`);
  }

}
