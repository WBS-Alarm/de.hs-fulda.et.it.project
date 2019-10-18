import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
    HttpClient,
    HttpHeaders
} from '@angular/common/http';
import { WbsSitemapHelper } from '../sitemap/data/wbs-sitemap.helper';

@Injectable()
export class TransaktionService
{
    private headers:HttpHeaders;

    constructor(private http:HttpClient,
                private sitemapHelper:WbsSitemapHelper)
    {
    }

    public postTransaktion(traegerId:number, buchungen:{ von:number, nach:number, positions:Array<{ groesse:number, anzahl:number }>}):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.post(this.sitemapHelper.transaktionForTraeger().replace('{traegerId}', traegerId.toString()),
            {
                von: buchungen.von,
                nach: buchungen.nach,
                positions: buchungen.positions
            },
            {
                headers: this.headers
            })
    }

    public getTransaktionenForTraeger(traegerId:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get(this.sitemapHelper.transaktionForTraeger().replace('{traegerId}', traegerId.toString()),
            {
                headers: this.headers
            }
        )
    }

}