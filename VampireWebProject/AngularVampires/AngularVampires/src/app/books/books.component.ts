import { Component, OnInit } from '@angular/core';
import { VampireService } from '../services/vampire.service';
import { Vampire } from '../models/vampire.model';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['../bookstrap.css',
              '../vampireStyles.css']
})
export class BooksComponent implements OnInit {

  constructor(private vampireService: VampireService) {

  }
  comment: string = this.vampireService.fetchComments();
  //public vampire: Vampire[];

  ngOnInit() {
  }

}
