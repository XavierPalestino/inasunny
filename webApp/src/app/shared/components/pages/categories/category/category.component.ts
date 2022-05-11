import { Component, Inject, OnInit } from '@angular/core';
import { AppModule } from 'src/app/app.module';

import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../service/category.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from '../categories-list/categories-list.component';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {

  public form: FormGroup = new FormGroup({
    name: new FormControl('', Validators.required)
  })

  constructor(private categoryService: CategoryService,
    private dialogRef: MatDialogRef<AppModule>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) { }

  ngOnInit(): void {
  }

  public onSubmit(): void {
    if(!this.data) {
      this.categoryService.createCategory({
        name: this.form.get("name").value
      })
      .subscribe(response => {
        alert(response ? "Category Created" : "Category creation failed")
      })
      this.form.reset();
      this.onClose();
    }
    else {
      this.categoryService.updateCategory(this.data.categoryId, {
        name: this.form.get("name").value
      })
      .subscribe(response => {
        alert(response ? "Category Updated" : "Category updated failded")
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

}
