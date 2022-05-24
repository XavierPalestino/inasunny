import { Component, OnInit, ViewChild } from '@angular/core';

import { UsersService } from '../service/user.service';
import { UsersResponse } from '../response/users.response';
import { UserDto } from '../dto/user.dto';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { UserComponent } from '../user/user.component';

export interface DialogData {
  userID: number;
}

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss']
})
export class UsersListComponent implements OnInit {

  users: MatTableDataSource<UserDto>;
  displayedColumns: string[] = ['id', 'username', 'name', 'phoneNumber', 'actions'];
  userID: number;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private usersService: UsersService,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getUsersList();
  }

  public getUsersList(): void {
    this.usersService.getUsers().subscribe((data: UsersResponse) => {
      console.log(data);
      this.users = new MatTableDataSource(data.users);
      this.users.paginator = this.paginator;
      this.users.sort = this.sort;
    });
  }

  // tslint:disable-next-line:typedef
  public onCreate() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '60%';
    this.dialog.open(UserComponent, dialogConfig);
  }

  // tslint:disable-next-line:typedef
  public onEdit(row) {
    this.userID = row;
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '60%';
    dialogConfig.data = {userID: this.userID};
    this.dialog.open(UserComponent, dialogConfig);
  }

  // tslint:disable-next-line:typedef
  public onDelete(row) {
    this.userID = row;
    this.usersService.deleteUsers(this.userID).subscribe(response => {
      alert(response ? 'Author Deleted' : 'Author deleted failed');
    });
  }
}
