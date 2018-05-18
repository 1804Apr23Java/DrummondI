import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Vampire } from '../models/vampire.model';

import { Observable } from 'rxjs';
 

@Injectable()
export class VampireService {

    constructor() {}

    public fetchComments() {
        return `{
                    {
                        'name' : 'dave',
                        'email' : 'db@gmail.com',
                        'comment' : 'Hello'
                    },
                    {
                        'name' : 'dave',
                        'email' : 'db@gmail.com',
                        'comment' : 'Hello'
                    }
                }`;
    }

    private handleError(error: Response) {
        return Observable.throw(error.statusText);
    }
 }