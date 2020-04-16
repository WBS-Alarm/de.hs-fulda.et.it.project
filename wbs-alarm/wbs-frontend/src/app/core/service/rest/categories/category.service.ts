import {Injectable} from "../../../../../../node_modules/@angular/core";
import {WbsSitemapHelper} from "../sitemap/data/wbs-sitemap.helper";
import {HttpClient, HttpHeaders} from "../../../../../../node_modules/@angular/common/http";
import {Observable} from "rxjs";
import {SystemCategoryInterface} from "../../../../views/system/components/categories/data/system-category.interface";

@Injectable()
export class CategoryService
{
    public headers: HttpHeaders;

    constructor(public http: HttpClient,
                public sitemapHelper: WbsSitemapHelper)
    {

    }

    public getCategory(categoryId:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get('/wbs/kategorie/' + categoryId,
            {
                headers: this.headers
            });
    }

    public getCategories(traegerId:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get(this.sitemapHelper.kategorieList().replace('{traegerId}', traegerId.toString()),
            {
                headers: this.headers
            });
    }

    public deleteCategory(category:SystemCategoryInterface):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.delete(this.sitemapHelper.kategorie().replace('{id}', category.id.toString()),
            {
                headers: this.headers
            })
    }

    public editCategory(category:SystemCategoryInterface):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.put(this.sitemapHelper.kategorie().replace('{id}', category.id.toString()),
            {
                name: category.name
            },
            {
                headers: this.headers
            })
    }

    public getGroesseForCategory(kategorieId:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get(this.sitemapHelper.groesseForCategory().replace('{kategorieId}', kategorieId.toString()),
            {
                headers: this.headers
            })
    }
}