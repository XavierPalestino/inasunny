import { Component, Inject, OnInit } from '@angular/core';
import { AppModule } from 'src/app/app.module';

import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { BooksService } from '../service/sale.service';
import { UsersService } from '../../users/service/user.service';
import { UserDto } from '../../users/dto/user.dto';
import { ProductService } from '../../products/service/product.service';
import { UsersResponse } from '../../users/response/users.response';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from '../sales-list/sales-list.component';
import { MatTableDataSource } from '@angular/material/table/table-data-source';
import { ProductDto } from '../../products/dto/product.dto';
import { ProductResponse } from '../../products/response/product.response';

export function dateValidator(): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const today = new Date().getTime();

    return control.value.getTime() > today
      ? {invalidDate: 'You cannot use future dates' }
      : null;
  };
}

@Component({
  selector: 'app-book',
  templateUrl: './sale.component.html',
  styleUrls: ['./sale.component.scss']
})
export class SaleComponent implements OnInit {

  usersList: UserDto[];
  productsList: ProductDto[];
  minDate: Date;
  maxDate: Date;

  public form: FormGroup = new FormGroup({
    saleDate: new FormControl('', [Validators.required, dateValidator]),
    salePrice: new FormControl(0, [Validators.required, Validators.min(100)]),
    products: new FormControl('', Validators.required),
    users: new FormControl('', Validators.required)
  });

  constructor(private salesService: BooksService,
              private usersService: UsersService,
              private productsService: ProductService,
              private dialogRef: MatDialogRef<AppModule>,
              @Inject(MAT_DIALOG_DATA) public data: DialogData) {

      const currentDate = new Date().getFullYear();
      this.minDate = new Date(currentDate - 572, 0, 1);
      this.maxDate = new Date(currentDate - 15, 0 , 0);

     }

  ngOnInit(): void {
    this.getUsersList();
    this.getProductsList();
  }

  public onSubmit(): void {
    if (!this.data) {
      this.salesService.createSales({
        saleDate: this.form.get('datePublication').value,
        salePrice: this.form.get('price').value,
        products: this.form.get('categories').value,
        users: this.form.get('authors').value
      })
      .subscribe(response => {
        alert(response ? 'Sale Created' : 'Sale creation failed');
      });
      this.form.reset();
      this.onClose();
    }
    else {
      this.salesService.updateSales( this.data.saleID, {
        saleDate: this.form.get('datePublication').value,
        salePrice: this.form.get('price').value,
        products: this.form.get('categories').value,
        users: this.form.get('authors').value
      })
      .subscribe(response => {
        alert(response ? 'Sale Updated' : 'Sale updated failed');
      });
      this.onClose();
    }
  }

  // tslint:disable-next-line:typedef
  onClose() {
    this.form.reset();
    this.dialogRef.close();
  }

  // tslint:disable-next-line:typedef
  onClear() {
    this.form.reset();
  }

  public getUsersList(): void {
    this.usersService.getUsers().subscribe((data: UsersResponse) => {
      console.log(data.users);
      this.usersList = data.users;
    });
  }

  public getProductsList(): void {
    this.productsService.getProduct().subscribe((data: ProductResponse) => {
      console.log(data.categories);
      this.productsList = data.categories;
    });
  }

}
