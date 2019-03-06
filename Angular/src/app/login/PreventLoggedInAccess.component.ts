import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivate } from '@angular/router';
import {Observable} from 'rxjs/Observable';

import { Http, RequestOptions, Headers } from '@angular/http';
import 'rxjs/Rx';

const headers = new Headers({
    'X-Requested-With': 'XMLHttpRequest'
});

const options = new RequestOptions({ withCredentials: true, headers });

@Injectable()
export class PreventLoggedInAccess implements CanActivate {

    constructor(private http: Http, private router: Router) { }

    canActivate() {
        return this.http.get("https://localhost:8443/api/login", options).map(e => {
            if (e) {
                this.router.navigate(['/']);
                return false;
            }
        }).catch(() => {            
            return Observable.of(true);
        });
    }
} 