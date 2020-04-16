import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {WbsSitemapHelper} from "../../../core/service/rest/sitemap/data/wbs-sitemap.helper";
import {Observable} from "rxjs";

@Injectable()
export class ReportService
{
    public headers: HttpHeaders;

    constructor(public http: HttpClient,
                public sitemapHelper: WbsSitemapHelper)
    {

    }

    public listBestaendeZuTraeger(traegerId:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get('/wbs/report/'+ traegerId +'/bestand', {headers: this.headers})
    }
}