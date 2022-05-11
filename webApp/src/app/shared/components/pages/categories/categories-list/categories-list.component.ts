import { Component, OnInit, ViewChild } from '@angular/core';

import { CategoryResponse } from '../response/category.response';
import { CategoryService } from '../service/category.service';
import { CategoryDTO } from '../dto/category.dto';
import { CategoryComponent } from '../category/category.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

export interface DialogData {
  categoryId: number;
}

@Component({
  selector: 'app-categories-list',
  templateUrl: './categories-list.component.html',
  styleUrls: ['./categories-list.component.scss']
})
export class CategoriesListComponent implements OnInit {

  category: MatTableDataSource<CategoryDTO>;
  displayedColumns: string[] = ['id','name','actions'];
  categoryId: number;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private categoryService: CategoryService,
    private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getList()
  }

  public getList(): void {
    this.categoryService.getCategory().subscribe(
      (data: CategoryResponse) => {
        console.log(data);
        this.category = new MatTableDataSource(data.categories);
        this.category.paginator = this.paginator;
        this.category.sort = this.sort;
      });
  }

  public onCreate() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "60%";
    this.dialog.open(CategoryComponent,dialogConfig);
  }

  public onEdit(row) {
    this.categoryId = row;
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "60%";
    dialogConfig.data = {categoryId: this.categoryId}
    this.dialog.open(CategoryComponent,dialogConfig);
  }

  public onDelete(row) {
    this.categoryId = row;
    this.categoryService.deleteCategory(this.categoryId).subscribe(response => {
      alert(response ? "Category Deleted" : "Category deleted failed")
    });
  }

}
