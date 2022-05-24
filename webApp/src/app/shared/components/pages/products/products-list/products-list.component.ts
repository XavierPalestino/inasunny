import { Component, OnInit, ViewChild } from '@angular/core';

import { ProductResponse } from '../response/product.response';
import { ProductService } from '../service/product.service';
import { ProductDto } from '../dto/product.dto';
import { ProductComponent } from '../product/product.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

export interface DialogData {
  categoryId: number;
}

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss']
})
export class ProductsListComponent implements OnInit {

  product: MatTableDataSource<ProductDto>;
  displayedColumns: string[] = [
    'id',
    'productName',
    'productCode',
    'productBrand',
    'expirationDate',
    'productPrice',
    'productCost',
    'productQuantity',
    'actions'];
  productID: number;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private productService: ProductService,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getList();
  }

  public getList(): void {
    this.productService.getProduct().subscribe(
      (data: ProductResponse) => {
        console.log(data);
        this.product = new MatTableDataSource(data.categories);
        this.product.paginator = this.paginator;
        this.product.sort = this.sort;
      });
  }

  // tslint:disable-next-line:typedef
  public onCreate() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '60%';
    this.dialog.open(ProductComponent, dialogConfig);
  }

  // tslint:disable-next-line:typedef
  public onEdit(row) {
    this.productID = row;
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '60%';
    dialogConfig.data = {productID: this.productID};
    this.dialog.open(ProductComponent, dialogConfig);
  }

  // tslint:disable-next-line:typedef
  public onDelete(row) {
    this.productID = row;
    this.productService.deleteProduct(this.productID).subscribe(response => {
      alert(response ? 'Product Deleted' : 'Product deleted failed');
    });
  }

}
