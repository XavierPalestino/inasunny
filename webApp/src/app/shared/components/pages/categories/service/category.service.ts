import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HOST } from "src/app/shared/components/pages/constant/http.constant";
import { CategoryRequest } from "../request/category.request";
import { CategoryResponse } from "../response/category.response";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  public createCategory(request: CategoryRequest): Observable<boolean> {
    return this.http.post<boolean>(HOST + "category", request)
  }

  public getCategory(): Observable<CategoryResponse>{
    return this.http.get<CategoryResponse>(HOST + "category/list")
  }

  public updateCategory(categoryId: number, request: CategoryRequest): Observable<boolean> {
    return this.http.put<boolean>(HOST + `category/${categoryId}`, request)
  }

  public deleteCategory(categoryId: number): Observable<boolean>{
    return this.http.delete<boolean>(HOST + `category/${categoryId}`)
  }

}