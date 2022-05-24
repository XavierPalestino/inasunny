import { Component, Inject, OnInit } from '@angular/core';
import { AppModule } from 'src/app/app.module';

import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { UsersService } from '../service/user.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from '../users-list/users-list.component';

export function dateValidator(): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const today = new Date().getTime();

    return control.value.getTime() > today
      ? {invalidDate: 'You cannot use future dates' }
      : null;
  };
}

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})

export class UserComponent implements OnInit {

  minDate: Date;
  maxDate: Date;

  public form: FormGroup = new FormGroup({
    username: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    phoneNumber: new FormControl(0, Validators.required),
  });

  constructor(private usersService: UsersService,
              private dialogRef: MatDialogRef<AppModule>,
              @Inject(MAT_DIALOG_DATA) public data: DialogData) {

      const currentDate = new Date().getFullYear();
      this.minDate = new Date(currentDate - 572, 0, 1);
      this.maxDate = new Date(currentDate - 15, 0 , 0);

     }


  ngOnInit(): void {
  }

  public onSubmit(): void {
    if (!this.data) {
      this.usersService.createUsers({
        username: this.form.get('username').value,
        name: this.form.get('name').value,
        phoneNumber: this.form.get('phoneNumber').value,
      })
      .subscribe(response => {
        alert(response ? 'User Created' : 'User creation failed');
      });
      this.form.reset();
      this.onClose();
    }
      else {
        this.usersService.updateUsers(this.data.userID, {
          username: this.form.get('username').value,
          name: this.form.get('name').value,
          phoneNumber: this.form.get('phoneNumber').value,
        })
        .subscribe(response => {
          alert(response ? 'User Updated' : 'User updated failed');
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
