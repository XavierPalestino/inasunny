import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsersListComponent } from './shared/components/pages/users/users-list/users-list.component';
import { SalesListComponent } from './shared/components/pages/sales/sales-list/sales-list.component';
import { ProductsListComponent } from './shared/components/pages/products/products-list/products-list.component';



const routes: Routes = [
  { path: '', redirectTo: '/first', pathMatch: 'full' },
  { path: 'first', component:  UsersListComponent},
  { path: 'second', component:  SalesListComponent},
  { path: 'third', component: ProductsListComponent},
];
export const appRouting = RouterModule.forRoot(routes);
@NgModule({
  imports: [RouterModule.forRoot(routes),
  CommonModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
