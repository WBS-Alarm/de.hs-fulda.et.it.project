import {Injectable} from "../../../../../../node_modules/@angular/core";
import {WbsSitemapHelper} from "../sitemap/data/wbs-sitemap.helper";
import {HttpClient, HttpHeaders} from "../../../../../../node_modules/@angular/common/http";
import {Observable} from "rxjs";
import {SystemCategoryInterface} from "../../../../views/system/components/categories/data/system-category.interface";

@Injectable()
export class CategoryService
{
    private headers: HttpHeaders;

    constructor(private http: HttpClient,
                private sitemapHelper: WbsSitemapHelper)
    {

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
}