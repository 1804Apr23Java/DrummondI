// Modules
import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Components
import { HomepageComponent } from './homepage/homepage.component';
import { AllaboutComponent } from './allabout/allabout.component';
import { MoviesComponent } from './movies/movies.component';
import { BooksComponent } from './books/books.component';

export const routes: Routes = [
 { path: '', component: HomepageComponent },
 { path: 'homepage', component: HomepageComponent },
 { path: 'books', component: BooksComponent },
 { path: 'movies', component: MoviesComponent },
 { path: 'allabout', component: AllaboutComponent },
];

@NgModule({
 imports: [ RouterModule.forRoot(routes, {useHash: true}) ],
 exports: [ RouterModule ]
})
export class AppRoutingModule {}