import {HttpHeaders} from "@angular/common/http";
import {isNullOrUndefined} from 'util';

export class WbsSitemapHelper
{
    private siteMaps:any;
    private bearer:string;
    public headers:HttpHeaders;

    constructor()
    {
       this.headers = new HttpHeaders({'Content-Type': 'application/hal+json'});
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
            this.setToHeader('Authorization', 'Bearer ' + localStorage.getItem('accessToken'));
        }

        return this.headers;
    }

    protected setToHeader(key:string, value:string):void
    {
        this.headers = this.headers.set(key, value);
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
            url = '/wbs/users/current';
        }

        return url;

    }

    public getLogout():string
    {
        return Object.values(this.siteMaps)[0]['logout'][0].href;
    }

    public getUsersForCarrier(carrierId:number):string
    {
        return Object.values(this.siteMaps)[0]['user'][0].href;
    }

    public getCarrier():string
    {
        return Object.values(this.siteMaps)[0]['traegerList'][0].href;
    }

    public getCarrierForId():string
    {
        return Object.values(this.siteMaps)[0]['traeger'][0].href;
    }

    public registerUser():string
    {
        return Object.values(this.siteMaps)[0]['registerUser'][0].href;
    }

    public user():string
    {
        return Object.values(this.siteMaps)[0]['benutzer'][0].href;
    }

    public zielort():string
    {
        return Object.values(this.siteMaps)[0]['zielort'][0].href;
    }

    public zielortLock():string
    {
        return Object.values(this.siteMaps)[0]['zielortLock'][0].href;
    }

    public kategorie():string
    {
        return Object.values(this.siteMaps)[0]['kategorie'][0].href;
    }

    public kategorieList():string
    {
        return Object.values(this.siteMaps)[0]['kategorieList'][0].href;
    }

    public authorities():string
    {
        return Object.values(this.siteMaps)[0]['authorities'][0].href;
    }

    public groesseForCategory():string
    {
        return Object.values(this.siteMaps)[0]['authorities'][0].href;
    }
}
