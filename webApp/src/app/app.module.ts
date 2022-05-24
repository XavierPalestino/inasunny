import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { SaleComponent } from './shared/components/pages/sales/sale/sale.component';
import { ProductComponent } from './shared/components/pages/products/product/product.component';
import { UserComponent } from './shared/components/pages/users/user/user.component';
import { UsersListComponent } from './shared/components/pages/users/users-list/users-list.component';
import { SalesListComponent } from './shared/components/pages/sales/sales-list/sales-list.component';
import { ProductsListComponent } from './shared/components/pages/products/products-list/products-list.component';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatGridListModule } from '@angular/material/grid-list';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule} from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { HttpClientModule } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatIconModule } from '@angular/material/icon';
import { MatSortModule } from '@angular/material/sort';
import { MatDialogModule } from '@angular/material/dialog';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTabsModule } from '@angular/material/tabs';





@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    UsersListComponent,
    SaleComponent,
    SalesListComponent,
    ProductComponent,
    ProductsListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    MatGridListModule,
    BrowserAnimationsModule,
    MatDatepickerModule,
    FormsModule,
    ReactiveFormsModule,
    MatGridListModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    HttpClientModule,
    MatTableModule,
    MatGridListModule,
    MatPaginatorModule,
    MatButtonToggleModule,
    MatIconModule,
    MatSortModule,
    MatDialogModule,
    MatToolbarModule,
    MatTabsModule
  ],
  bootstrap: [AppComponent,
    UserComponent,
    UsersListComponent,
    SaleComponent,
    SalesListComponent,
    ProductComponent,
    ProductsListComponent],
  entryComponents: []
})
export class AppModule { }
