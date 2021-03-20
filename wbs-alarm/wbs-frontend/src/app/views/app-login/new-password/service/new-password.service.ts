
import { WbsSitemapHelper } from '../../../../core/service/rest/sitemap/data/wbs-sitemap.helper';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable()
export class NewPasswordService
{
    constructor(public sitemapHelper:WbsSitemapHelper,
                public http:HttpClient)
    {

    }

    public newPassword(password:any):Observable<any>
    {
        return this.http.post('wbs/public/reset-password',
            {
                'token':    password.token,
                'password': password.password
            },
            {
                responseType: 'text'
            }
        );
    }
}
