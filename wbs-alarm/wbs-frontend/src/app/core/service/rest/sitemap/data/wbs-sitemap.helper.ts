import { HttpHeaders } from "@angular/common/http";
import { isNullOrUndefined } from 'util';

export class WbsSitemapHelper
{
    private siteMaps:any;
    private bearer:string;
    public headers:HttpHeaders;

    constructor()
    {
       this.headers = new HttpHeaders({'Content-Type': 'application/json'});
    }

    public set Bearer(value:string)
    {
        this.bearer = value;
    }

    public get Bearer():string
    {
        return this.bearer;
    }

    public set sitemaps(value:any)
    {
        this.siteMaps = value;
    }

    public get sitemaps():any
    {
        return this.siteMaps;
    }

    public setAuthorization():HttpHeaders
    {
        if(localStorage.getItem('accessToken'))
        {
            this.setToHeader('Authorization', localStorage.getItem('accessToken'));
        }

        return this.headers;
    }

    protected setToHeader(key:string, value:string):void
    {
        this.headers.set(key, value);
    }

    public getLogin():string
    {
        return Object.values(this.siteMaps)[0]['userLogin'][0].href;
    }

    public getCurrentUsers():string
    {
        let url:string;

        if(!isNullOrUndefined(this.siteMaps))
        {
            url = Object.values(this.siteMaps)[0]['current'][0].href;
        }
        else
        {
            url = '/users/current';
        }

        return url;

    }

    public getLogout():string
    {
        return Object.values(this.siteMaps)[0]['logout'][0].href;
    }
}
