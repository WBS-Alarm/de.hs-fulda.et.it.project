import { Injectable } from "@angular/core";
import { HttpClient,
    HttpHeaders } from "@angular/common/http";
import { WbsSitemapHelper } from '../sitemap/data/wbs-sitemap.helper';
import { Observable } from "rxjs/Observable";

@Injectable()
export class UsersService
{
    private headers:HttpHeaders;

    constructor(private http:HttpClient,
                private sitemapHelper:WbsSitemapHelper)
    {

    }

    public getCurrentUsers():Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();


        return this.http.get(this.sitemapHelper.getCurrentUsers(), {
            headers: this.headers
        })
    }


}