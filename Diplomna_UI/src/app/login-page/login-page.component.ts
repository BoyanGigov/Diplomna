import {Component, ElementRef, OnInit, AfterViewInit, ViewChild} from '@angular/core';
import {ActivatedRoute, RouterModule, Router} from '@angular/router';

import {MatInputModule} from '@angular/material/input';

import {LoginPageService} from './service/login-page.service';

@Component({
    selector: 'app-login-page',
    templateUrl: './login-page.component.html',
    styleUrls: ['./login-page.component.css'],
    providers: [LoginPageService]
})

export class LoginPageComponent {
    public isLoginFailed = false;
    public errorMessage = "";
    @ViewChild('username') username: ElementRef;
    @ViewChild('password') password: ElementRef;

    constructor(private loginPageService: LoginPageService, private route: ActivatedRoute, private router:Router) {
        console.log("constructor of LoginPageComponent");
    }

    login(username:string, password:string) {
        this.isLoginFailed = false; // visibly refresh for user in case it has failed
        this.loginPageService.login(this.username.nativeElement.value, this.password.nativeElement.value).subscribe(response => {
            console.log(response);
            if (response.error) {
                this.isLoginFailed = true;
                this.errorMessage = response.error;
            } else if (response.token) {
                this.isLoginFailed = false;
                this.errorMessage = "";
                this.router.navigate(['courses'], { relativeTo: this.route });
            }
        });
    }

}