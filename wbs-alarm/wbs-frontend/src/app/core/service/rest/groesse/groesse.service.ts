import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders
} from '@angular/common/http';
import { WbsSitemapHelper } from '../sitemap/data/wbs-sitemap.helper';
import { Observable } from 'rxjs';


@Injectable()
export class GroesseService
{
    public headers:HttpHeaders;

    constructor(public http:HttpClient,
                public sitemapHelper:WbsSitemapHelper)
    {

    }

    public getGroessenForKategorie(categoryId:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        let url:string = this.sitemapHelper.groesseList().replace('{kategorieId}', categoryId.toString());

        return this.http.get(url,
            {
                headers: this.headers
            });
    }

    public addGroesseForTraeger(categoryId:number, groesse:any):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        let url:string = this.sitemapHelper.groesseList().replace('{kategorieId}', categoryId.toString());

        return this.http.post(url, {
                name:           groesse.name,
                bestandsgrenze: groesse.bestandsgrenze
            },
            {
                headers: this.headers,
                observe: 'response'
            });

    }

    public getSingleGroesse(url:string):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get(url, {headers: this.headers});
    }

    public updateGroesse(groesse:any):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.put('wbs/groesse/' + groesse.id, {
                name:           groesse.name,
                bestandsgrenze: groesse.bestandsgrenze
            },
            {
                headers: this.headers
            });
    }

    public deleteGroesse(groesse:any):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.delete('wbs/groesse/' + groesse.id,
            {
                headers: this.headers
            });
    }
}
