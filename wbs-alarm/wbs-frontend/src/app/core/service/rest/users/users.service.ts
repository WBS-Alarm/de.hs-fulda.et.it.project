import { Injectable } from "@angular/core";
import { HttpClient,
    HttpHeaders } from "@angular/common/http";
import { WbsSitemapHelper } from '../sitemap/data/wbs-sitemap.helper';
import { Observable } from "rxjs";
import {UserDataInterface} from "./user-data.interface";

@Injectable()
export class UsersService
{
    private headers:HttpHeaders;

    constructor(private http:HttpClient,
                private sitemapHelper:WbsSitemapHelper)
    {

    }

    public getOneUser(benutzerId:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get('/wbs/benutzer/' + benutzerId.toString(), {
            headers: this.headers
        })
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

        return this.http.post(this.sitemapHelper.registerUser().replace('{traegerId}', traegerId.toString()),
            {
                username: user.username,
                password: user.password
            },
            {
                headers: this.headers
            })
    }

    public editUser(userId:number, user:UserDataInterface):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.put(this.sitemapHelper.user().replace('{id}', userId.toString()),
            {
                mail: user.mail,
                einkaeufer: user.einkaeufer
            },
            {
                headers: this.headers
            })
    }

    public deleteUser(userId:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.delete(this.sitemapHelper.user().replace('{id}', userId.toString()),
            {
                headers: this.headers
            })
    }
}