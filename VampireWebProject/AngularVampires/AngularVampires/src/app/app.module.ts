import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { HomepageComponent } from './homepage/homepage.component';
import { MoviesComponent } from './movies/movies.component';
import { BooksComponent } from './books/books.component';
import { AllaboutComponent } from './allabout/allabout.component';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    MoviesComponent,
    BooksComponent,
    AllaboutComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot([]),
    FormsModule,
    AppRoutingModule,
    HttpModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
