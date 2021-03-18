import { HttpClient } from '../../../../../../node_modules/@angular/common/http';
import { Injectable } from '../../../../../../node_modules/@angular/core';
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
