import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthorsListComponent } from './shared/components/pages/authors/authors-list/authors-list.component';
import { BooksListComponent } from './shared/components/pages/books/books-list/books-list.component';
import { CategoriesListComponent } from './shared/components/pages/categories/categories-list/categories-list.component';



const routes: Routes = [
  { path: '', redirectTo: '/first', pathMatch: 'full' },
  { path: 'first', component:  AuthorsListComponent},
  { path: 'second', component:  BooksListComponent},
  { path: 'third', component: CategoriesListComponent},
];
export const appRouting = RouterModule.forRoot(routes);
@NgModule({
  imports: [RouterModule.forRoot(routes),
  CommonModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
