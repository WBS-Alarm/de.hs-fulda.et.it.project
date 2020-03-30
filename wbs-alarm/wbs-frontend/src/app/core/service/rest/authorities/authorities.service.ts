import { Injectable } from '@angular/core';
import {
    HttpClient,
    HttpHeaders
} from '@angular/common/http';
import { WbsSitemapHelper } from '../sitemap/data/wbs-sitemap.helper';
import { Observable } from 'rxjs';

@Injectable()
export class AuthoritiesService
{
    public headers:HttpHeaders;

    constructor(public http:HttpClient,
                public sitemapHelper:WbsSitemapHelper)
    {

    }

    public getAuthorities():Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get(this.sitemapHelper.authorities(),
            {
                headers: this.headers
            })
    }

    public grantAuthorities(userId:number, authorityId:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        let url:string = this.sitemapHelper.grantAuthority().replace('{authorityId}', authorityId.toString());

        return this.http.post(url.replace('{benutzerId}', userId.toString()),
            {},
            {
                headers: this.headers
            });
    }

    public removeAuthorities(userId:number, authorityId:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        let url:string = this.sitemapHelper.grantAuthority().replace('{authorityId}', authorityId.toString());

        return this.http.delete(url.replace('{benutzerId}', userId.toString()),
            {
                headers: this.headers
            });
    }
}
