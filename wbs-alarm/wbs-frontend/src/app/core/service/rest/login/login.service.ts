import { Injectable } from '../../../../../../node_modules/@angular/core';
import { WbsSitemapHelper } from '../sitemap/data/wbs-sitemap.helper';
import {
    HttpClient,
    HttpHeaders
} from '../../../../../../node_modules/@angular/common/http';
import { Observable } from 'rxjs';


@Injectable()
export class LoginService
{
    public headers:HttpHeaders;

    constructor(public sitemapHelper:WbsSitemapHelper,
                public http:HttpClient)
    {
        // Fix lint
    }

    public login(user:any):Observable<any>
    {
        return this.http.post(this.sitemapHelper.getLogin(),
            {
                'username': user.name,
                'password': user.password
            },
            {
                responseType: 'text'
            }
        );
    }

    public logout():Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        console.log(this.headers);

        return this.http.get(this.sitemapHelper.getLogout(),
            {
                headers: this.headers
            });
    }

    public resetPassword(username:string):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.post('/wbs/public/forgot/' + username,
            {},
            {
                headers: this.headers
            });
    }
}
