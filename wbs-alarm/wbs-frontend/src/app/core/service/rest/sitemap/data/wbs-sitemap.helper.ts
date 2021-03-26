import { HttpHeaders } from '@angular/common/http';
import { isNullOrUndefined } from 'util';
import { Injectable } from '@angular/core';

@Injectable()
export class WbsSitemapHelper
{
    public siteMaps:any;
    public bearer:string;
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
        let bearer:string;

        bearer = this.getCookie('loginToken');

        if(bearer)
        {
            this.setToHeader('Authorization', 'Bearer ' + bearer);
        }

        return this.headers;
    }

    public setToHeader(key:string, value:string):void
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
        return Object.values(this.siteMaps)[0]['groesseList'][0].href;
    }

    public zielortForTraeger():string
    {
        return Object.values(this.siteMaps)[0]['zielortList'][0].href;
    }

    public transaktionForTraeger():string
    {
        return Object.values(this.siteMaps)[0]['transaktionList'][0].href;
    }

    public grantAuthority():string
    {
        return Object.values(this.siteMaps)[0]['grantAuthority'][0].href;
    }

    public groesseList():string
    {
        return Object.values(this.siteMaps)[0]['groesseList'][0].href;
    }

    public groesse():string
    {
        return Object.values(this.siteMaps)[0]['groesse'][0].href;
    }

    public bestandList():string
    {
        return Object.values(this.siteMaps)[0]['bestandList'][0].href;
    }

    public bestand():string
    {
        return Object.values(this.siteMaps)[0]['bestand'][0].href;
    }

    /* tslint:disable */
    public getCookie(name:string)
    {
        let cookiename = name + '=';
        let decodedCookie = decodeURIComponent(document.cookie);
        let ca = decodedCookie.split(';');
        for(var i = 0; i < ca.length; i++)
        {
            var c = ca[i];
            while(c.charAt(0) == ' ')
            {
                c = c.substring(1);
            }
            if(c.indexOf(cookiename) == 0)
            {
                return c.substring(cookiename.length, c.length);
            }
        }
        return '';
    }
    /* tslint:enable */
}
