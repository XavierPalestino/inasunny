import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HOST } from 'src/app/shared/components/pages/constant/http.constant';
import { UsersRequest } from '../request/users.request';
import { UsersResponse } from '../response/users.response';


@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private http: HttpClient) { }

  public createUsers(request: UsersRequest): Observable<boolean> {
    return this.http.post<boolean>(HOST + '/user', request);
  }

  public getUsers(): Observable<UsersResponse>{
    return this.http.get<UsersResponse>(HOST + 'user/list');
  }

  public updateUsers(userID: number, request: UsersRequest): Observable<boolean> {
    return this.http.put<boolean>(HOST + `user/${userID}`, request);
  }

  public deleteUsers(userID: number): Observable<boolean>{
    return this.http.delete<boolean>(HOST + `user/${userID}`);
  }

  public getUsername(userID: number): Observable<boolean> {
    return this.http.get<boolean>(HOST + `user/name/${userID}`);
  }

}
