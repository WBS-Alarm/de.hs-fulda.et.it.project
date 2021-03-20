import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable()
export class GetSitemapService
{
    constructor(public http:HttpClient)
    {
        // Fix Lint
    }

    public getSitemaps():Observable<any>
    {
        return this.http.get('/wbs/public/sitemap');
    }
}
