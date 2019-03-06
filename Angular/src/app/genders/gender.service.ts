import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Http } from '@angular/http';
import 'rxjs/Rx';

const BASE_URL = environment.apiBase + "generos/";

@Injectable()
export class GenderService {

    constructor(private http:Http) { }

    getGrafic(){
        return this.http.get(BASE_URL + "grafico").map(
            response => response.json()
        );
    };
}