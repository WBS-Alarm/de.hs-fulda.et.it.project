import {HttpClient, HttpHeaders} from "../../../../../../node_modules/@angular/common/http";
import {Injectable} from "../../../../../../node_modules/@angular/core";
import {WbsSitemapHelper} from "../sitemap/data/wbs-sitemap.helper";
import {Observable} from "rxjs";

@Injectable()
export class CarrierService
{
    private headers: HttpHeaders;

    constructor(private http: HttpClient,
                private sitemapHelper: WbsSitemapHelper)
    {

    }

    public getCarriers():Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get(this.sitemapHelper.getCarrier(),
            {
            headers: this.headers
        })
    }

    public createCarrier(newCarrier:string):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.post(this.sitemapHelper.getCarrier(),
            {
                name: newCarrier
            },
            {
                headers: this.headers
            })
    }

}