import { Injectable }  from '../../../../../../node_modules/@angular/core';
import { WbsSitemapHelper }  from '../sitemap/data/wbs-sitemap.helper';
import { HttpClient } from '../../../../../../node_modules/@angular/common/http';
import { Observable }  from 'rxjs/Observable';


@Injectable()
export class LoginService
{


    constructor(private sitemapHelper:WbsSitemapHelper,
                private http:HttpClient)
    {

    }

    public login(user:any):Observable<any>
    {
        return this.http.post(this.sitemapHelper.getLogin(),
            {
                "username": user.name,
                "password": user.password
            });
    }
}
