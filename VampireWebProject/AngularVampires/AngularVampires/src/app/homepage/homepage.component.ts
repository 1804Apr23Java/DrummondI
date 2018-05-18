import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['../bookstrap.css',
              '../vampireStyles.css']
})
export class HomepageComponent implements OnInit {
  vampire = 'dracula';

  constructor() { }

  ngOnInit() {
  }

}
