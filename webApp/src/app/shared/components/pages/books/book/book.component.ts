import { Component, Inject, OnInit } from '@angular/core';
import { AppModule } from 'src/app/app.module';

import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { BooksService } from '../service/book.service';
import { AuthorsService } from '../../authors/service/author.service';
import { AuthorDTO } from '../../authors/dto/author.dto';
import { CategoryService } from '../../categories/service/category.service';
import { AuthorsResponse } from '../../authors/response/authors.response';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from '../books-list/books-list.component';
import { MatTableDataSource } from '@angular/material/table/table-data-source';
import { CategoryDTO } from '../../categories/dto/category.dto';
import { CategoryResponse } from '../../categories/response/category.response';

export function dateValidator(): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const today = new Date().getTime();

    return control.value.getTime() > today 
      ? {invalidDate: 'You cannot use future dates' } 
      : null;
  }
}

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent implements OnInit {
  
  authorsList: AuthorDTO[];
  categoriesList: CategoryDTO[];
  minDate: Date;
  maxDate: Date;

  public form: FormGroup = new FormGroup({
    title: new FormControl('', Validators.required),
    pages: new FormControl('', [Validators.required, Validators.min(50)]),
    isbn: new FormControl('', [Validators.required, Validators.min(13)]),
    price: new FormControl('', [Validators.required, Validators.min(100)]),
    summary: new FormControl('', Validators.required),
    editorial: new FormControl('', Validators.required),
    datePublication: new FormControl('', [Validators.required, dateValidator]),
    authors: new FormControl('', Validators.required),
    categories: new FormControl('', Validators.required)
  })

  constructor(private booksService: BooksService,
    private authorsService: AuthorsService,
    private categoriesService: CategoryService,
    private dialogRef: MatDialogRef<AppModule>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {

      const currentDate = new Date().getFullYear();
      this.minDate = new Date(currentDate - 572, 0, 1);
      this.maxDate = new Date(currentDate - 15, 0 , 0);

     }

  ngOnInit(): void {
    this.getAuthorsList()
    this.getCategoriesList();
  }

  public onSubmit(): void {
    if(!this.data) {
      this.booksService.createBooks({
        title: this.form.get("title").value,
        pages: this.form.get("pages").value,
        isbn:  this.form.get("isbn").value,
        price: this.form.get("price").value,
        summary: this.form.get("summary").value,
        editorial: this.form.get("editorial").value,
        datePublication: this.form.get("datePublication").value,
        authors: this.form.get("authors").value,
        categories: this.form.get("categories").value,
      })
      .subscribe(response => {
        alert(response ? "Book Created" : "Author creation failed")
      })
      this.form.reset();
      this.onClose();
    }
    else {
      this.booksService.updateBooks( this.data.bookId, { 
        title: this.form.get("title").value,
        pages: this.form.get("pages").value,
        isbn:  this.form.get("isbn").value,
        price: this.form.get("price").value,
        summary: this.form.get("summary").value,
        editorial: this.form.get("editorial").value,
        datePublication: this.form.get("datePublication").value,
        authors: this.form.get("authors").value,
        categories: this.form.get("categories").value,
      })
      .subscribe(response => {
        alert(response ? "Author Updated" : "Author updated failed")
      })
      this.onClose();
    }
  }

  onClose() {
    this.form.reset();
    this.dialogRef.close();
  }

  onClear() {
    this.form.reset();
  }

  public getAuthorsList(): void {
    this.authorsService.getAuthors().subscribe((data: AuthorsResponse) =>{
      console.log(data.authors);
      this.authorsList = data.authors;
    })
  }

  public getCategoriesList(): void {
    this.categoriesService.getCategory().subscribe((data: CategoryResponse) =>{
      console.log(data.categories);
      this.categoriesList = data.categories;
    })
  }

}
