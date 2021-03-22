import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-link',
  templateUrl: './link.component.html'
})
export class LinkComponent implements OnInit {

  @Input() route: string;
  @Input() param: string;
  @Input() label: string;
  routeLink: string;

  constructor() {
    if (!this.param) {
      this.param = '';
    }
  }

  ngOnInit(): void {
  }

}