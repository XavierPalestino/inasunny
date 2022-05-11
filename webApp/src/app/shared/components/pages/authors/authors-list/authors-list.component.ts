import { Component, OnInit, ViewChild } from '@angular/core';

import { AuthorsService } from '../service/author.service';
import { AuthorsResponse } from '../response/authors.response';
import { AuthorDTO } from '../dto/author.dto';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { AuthorComponent } from '../author/author.component';

export interface DialogData {
  authorId: number;
}

@Component({
  selector: 'app-authors-list',
  templateUrl: './authors-list.component.html',
  styleUrls: ['./authors-list.component.scss']
})
export class AuthorsListComponent implements OnInit {

  authors: MatTableDataSource<AuthorDTO>;
  displayedColumns: string[] = ['id','name', 'firstName', 'secondName', 'birthday', 'actions'];
  authorId: number;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private authorsService: AuthorsService,
    private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getAuthorsList()
  }

  public getAuthorsList(): void {
    this.authorsService.getAuthors().subscribe((data: AuthorsResponse) =>{
      console.log(data);
      this.authors = new MatTableDataSource(data.authors);
      this.authors.paginator = this.paginator;
      this.authors.sort = this.sort;
    })
  }

  public onCreate() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "60%";
    this.dialog.open(AuthorComponent,dialogConfig);
  }

  public onEdit(row) {
    this.authorId = row;
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "60%";
    dialogConfig.data = {authorId: this.authorId}
    this.dialog.open(AuthorComponent,dialogConfig);
  }

  public onDelete(row) {
    this.authorId = row;
    this.authorsService.deleteAuthors(this.authorId).subscribe(response => {
      alert(response ? "Author Deleted" : "Author deleted failed")
    });
  }
}
