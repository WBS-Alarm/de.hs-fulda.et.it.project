import { Injectable } from "@angular/core";
import { HttpClient,
    HttpHeaders } from "@angular/common/http";
import { WbsSitemapHelper } from '../sitemap/data/wbs-sitemap.helper';
import { Observable } from "rxjs/Observable";
import {UserDataInterface} from "./user-data.interface";

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

    public getAllUsersForCarrier(carrierId:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get(this.sitemapHelper.getUsersForCarrier(carrierId), {
            headers: this.headers
        })
    }

    public registerUser(traegerId:number, user:UserDataInterface):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        console.log(this.sitemapHelper.registerUser().replace('{id}', traegerId.toString()));

        return this.http.post(this.sitemapHelper.registerUser().replace('{traegerId}', traegerId.toString()),
            {
                username: user.name,
                password: user.password
            },
            {
                headers: this.headers
            })
    }
}