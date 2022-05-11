import { Component, OnInit, ViewChild } from '@angular/core';

import { BooksService } from '../service/book.service';
import { BooksDTO } from '../dto/book.dto';
import { BookComponent } from '../book/book.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

export interface DialogData {
  bookId: number;
}

@Component({
  selector: 'app-books-list',
  templateUrl: './books-list.component.html',
  styleUrls: ['./books-list.component.scss']
})
export class BooksListComponent implements OnInit {

  books: MatTableDataSource<BooksDTO[]>;
  displayedColumns: string[] = ['id','title','pages','isbn','price','editorial','releaseDate','authors','categories','actions'];
  bookId: number;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private booksService: BooksService,
    private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getBooksList()
  }

  public getBooksList(): void {
    this.booksService.getBook('/book?expand=categories,authors').subscribe(
      (data: [BooksDTO[]]) => {
      console.log(data);
      this.books = new MatTableDataSource(data);
      this.books.paginator =  this.paginator;
      this.books.sort = this.sort;
    })
  }

  public onCreate() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "60%";
    dialogConfig.height = "100%";
    this.dialog.open(BookComponent,dialogConfig);
  }

  public onEdit(row) {
    this.bookId = row;
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "60%";
    dialogConfig.data = {bookId: this.bookId}
    this.dialog.open(BookComponent,dialogConfig);
  }

  public onDelete(row) {
    this.bookId = row;
    this.booksService.deleteBooks(this.bookId).subscribe(response => {
      alert(response ? "book Deleted" : "book deleted failed")
    });
  }

}
