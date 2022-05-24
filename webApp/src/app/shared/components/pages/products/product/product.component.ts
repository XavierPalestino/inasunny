import { Component, Inject, OnInit } from '@angular/core';
import { AppModule } from 'src/app/app.module';

import {AbstractControl, FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import { ProductService } from '../service/product.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from '../products-list/products-list.component';

export function dateValidator(): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const today = new Date();
    const minDate = new Date();
    minDate.setDate(new Date().getDate() + 10);

    return minDate > today
      ? {invalidDate: 'La caducidad del producto debe ser mayor a 10 dias' }
      : null;
  };
}

@Component({
  selector: 'app-category',
  templateUrl: './products.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  minDate: Date;

  public form: FormGroup = new FormGroup({
    productName: new FormControl('', Validators.required),
    productCode: new FormControl('', Validators.required),
    productBrand: new FormControl('', Validators.required),
    expirationDate: new FormControl('', [Validators.required, dateValidator]),
    productPrice: new FormControl('', Validators.required),
    productCost: new FormControl('', Validators.required),
    productQuantity: new FormControl('', Validators.required)
  });

  constructor(private productService: ProductService,
              private dialogRef: MatDialogRef<AppModule>,
              @Inject(MAT_DIALOG_DATA) public data: DialogData) {
    this.minDate = new Date();
    this.minDate.setDate(this.minDate.getDate() + 10);
  }

  // @ts-ignore
  ngOnInit(): void {
  }

  public onSubmit(): void {
    if (!this.data) {
      this.productService.createProduct({
        productName: this.form.get('productName').value,
        productCode: this.form.get('productCode').value,
        productBrand: this.form.get('productBrand').value,
        expirationDate: this.form.get('expirationDate').value,
        productPrice: this.form.get('productPrice').value,
        productCost: this.form.get('productCost').value,
        productQuantity: this.form.get('productQuantity').value
      })
      .subscribe(response => {
        alert(response ? 'Product Created' : 'Product creation failed');
      });
      this.form.reset();
      this.onClose();
    }
    else {
      this.productService.updateProduct(this.data.categoryId, {
        productName: this.form.get('productName').value,
        productCode: this.form.get('productCode').value,
        productBrand: this.form.get('productBrand').value,
        expirationDate: this.form.get('expirationDate').value,
        productPrice: this.form.get('productPrice').value,
        productCost: this.form.get('productCost').value,
        productQuantity: this.form.get('productQuantity').value
      })
      .subscribe(response => {
        alert(response ? 'Product Updated' : 'Product updated failed');
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

}
