import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';

@Injectable()
export class LoginPageService {

    private baseConfig = { withCredentials: false, headers: { 'Content-Type': 'application/x-www-form-urlencoded'} };
    base_url: string = 'https://aptitude-learn.w3c.fmi.uni-sofia.bg';

    constructor(private httpClient: HttpClient) {
        console.log("service constructor");
        console.log("this.base_url " + this.base_url);

    }

    login(username:string, password:string): Observable<any> {
         let loginUrl: string = this.base_url + "/login/token.php?service=moodle_mobile_app";
         console.log("loginUrl to: " + loginUrl);
         let payload = "username="+username+"&password="+password;
         return this.httpClient.post<any>(loginUrl, payload, this.baseConfig);
    }

}