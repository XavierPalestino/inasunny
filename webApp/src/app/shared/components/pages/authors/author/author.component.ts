import { Component, Inject, OnInit } from '@angular/core';
import { AppModule } from 'src/app/app.module';

import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { AuthorsService } from '../service/author.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from '../authors-list/authors-list.component';

export function dateValidator(): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const today = new Date().getTime();

    return control.value.getTime() > today 
      ? {invalidDate: 'You cannot use future dates' } 
      : null;
  }
}

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.scss']
})

export class AuthorComponent implements OnInit {

  minDate: Date;
  maxDate: Date;

  public form: FormGroup = new FormGroup({
    name: new FormControl('', Validators.required),
    firstSurname: new FormControl('', Validators.required),
    secondSurname: new FormControl(''),
    birthdate: new FormControl('', [Validators.required, dateValidator])
  })

  constructor(private authorsService: AuthorsService,
    private dialogRef: MatDialogRef<AppModule>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {

      const currentDate = new Date().getFullYear();
      this.minDate = new Date(currentDate - 572, 0, 1);
      this.maxDate = new Date(currentDate - 15, 0 , 0);

     }


  ngOnInit(): void {
  }

  public onSubmit(): void {
    if(!this.data) {
      this.authorsService.createAuthors({
        name: this.form.get("name").value,
        firstSurname: this.form.get("firstSurname").value,
        secondSurname: this.form.get("secondSurname").value,
        birthdate: this.form.get("birthdate").value
      })
      .subscribe(response => {
        alert(response ? "Author Created" : "Author creation failed")
      })
      this.form.reset();
      this.onClose();
    }
      else {
        this.authorsService.updateAuthors(this.data.authorId, {
          name: this.form.get("name").value,
          firstSurname: this.form.get("firstSurname").value,
          secondSurname: this.form.get("secondSurname").value,
          birthdate: this.form.get("birthdate").value
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

}
