import {HttpClient} from "../../../../../../node_modules/@angular/common/http";
import {Injectable} from "../../../../../../node_modules/@angular/core";
import {Observable} from "rxjs/Observable";

@Injectable()
export class GetSitemapService
{
    constructor(private http:HttpClient)
    {

    }

    public getSitemaps():Observable<any>
    {
        return this.http.get('/public/sitemap');
    }
}
