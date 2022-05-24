import { Component, OnInit, ViewChild } from '@angular/core';

import { BooksService } from '../service/sale.service';
import { SalesDTO } from '../dto/sale.dto';
import { SaleComponent } from '../sale/sale.component';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

export interface DialogData {
  saleID: number;
}

@Component({
  selector: 'app-sales-list',
  templateUrl: './sales-list.component.html',
  styleUrls: ['./sales-list.component.scss']
})
export class SalesListComponent implements OnInit {

  sales: MatTableDataSource<SalesDTO[]>;
  displayedColumns: string[] = ['id', 'saleDate', 'salePrice', 'products', 'users', 'actions'];
  saleID: number;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private salesService: BooksService,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getSalesList();
  }

  public getSalesList(): void {
    this.salesService.getSales('/book?expand=product,users').subscribe(
      (data: [SalesDTO[]]) => {
      console.log(data);
      this.sales = new MatTableDataSource(data);
      this.sales.paginator =  this.paginator;
      this.sales.sort = this.sort;
    });
  }

  // tslint:disable-next-line:typedef
  public onCreate() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '40%';
    this.dialog.open(SaleComponent, dialogConfig);
  }

  // tslint:disable-next-line:typedef
  public onEdit(row) {
    this.saleID = row;
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '60%';
    dialogConfig.data = {saleID: this.saleID};
    this.dialog.open(SaleComponent, dialogConfig);
  }

  // tslint:disable-next-line:typedef
  public onDelete(row) {
    this.saleID = row;
    this.salesService.deleteSales(this.saleID).subscribe(response => {
      alert(response ? 'Sale Deleted' : 'Sale deleted failed');
    });
  }

}
