import { Component } from '@angular/core';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})

export class TopBarComponent {

    isShowNavigation() {
        // default page is login page and should allow navigation only through login
        return document.location.href != document.baseURI;
    }

}