import {
    HttpClient,
    HttpHeaders
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { WbsSitemapHelper } from '../sitemap/data/wbs-sitemap.helper';
import { SystemZielortInterface } from '../../../../views/system/components/targetplaces/data/system-zielort.interface';
import { SystemCarrierInterface } from '../../../../views/system/components/carrier/data/system-carrier.interface';
import { Observable } from 'rxjs';

@Injectable()
export class CarrierService
{
    public headers:HttpHeaders;

    constructor(public http:HttpClient,
                public sitemapHelper:WbsSitemapHelper)
    {
        // Fix lint
    }

    public getCarriers():Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get(this.sitemapHelper.getCarrier(),
            {
                headers: this.headers
            });
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
            });
    }

    public updateCarrier(carrier:SystemCarrierInterface):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.put(this.sitemapHelper.getCarrierForId().replace('{id}', carrier.id.toString()),
            {
                name: carrier.name
            },
            {
                headers: this.headers
            });
    }

    public deleteCarrier(carrier:SystemCarrierInterface):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.delete(this.sitemapHelper.getCarrierForId().replace('{id}', carrier.id.toString()),
            {
                headers: this.headers
            });
    }

    public getDetailsForCarrier(id:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get(this.sitemapHelper.getCarrierForId().replace('{id}', id.toString()),
            {
                headers: this.headers
            });
    }

    public createTargetplace(carrierId:number, targetplaceName:string):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.post(this.sitemapHelper.getCarrierForId().replace('{id}', carrierId.toString()) + '/zielorte',
            {
                name: targetplaceName
            },
            {
                headers: this.headers,
                observe: 'response'
            });
    }

    public updateTargetplace(targetPlace:SystemZielortInterface):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.put(this.sitemapHelper.zielort().replace('{id}', targetPlace.id.toString()),
            {
                name: targetPlace.name
            },
            {
                headers: this.headers
            });
    }

    public deleteTargetplace(targetPlace:SystemZielortInterface):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.delete(this.sitemapHelper.zielort().replace('{id}', targetPlace.id.toString()),
            {
                headers: this.headers
            });
    }

    public lockTargetplace(targetplace:SystemZielortInterface):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.post(this.sitemapHelper.zielortLock().replace('{id}', targetplace.id.toString()),
            {},
            {
                headers: this.headers
            });
    }

    public listZielorteForTraeger(traegerId:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get(this.sitemapHelper.zielortForTraeger().replace('{traegerId}', traegerId.toString()),
            {
                headers: this.headers
            });
    }

    public createCategory(carrierId:number, categoryName:string):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.post(this.sitemapHelper.kategorieList().replace('{traegerId}', carrierId.toString()),
            {
                name: categoryName
            },
            {
                headers: this.headers,
                observe: 'response'
            });
    }

    public getTargetPlace(url:string):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get(url, {headers: this.headers});
    }

    public getTargetPlaceById(id:number):Observable<any>
    {
        this.headers = this.sitemapHelper.setAuthorization();

        return this.http.get('/wbs/zielort/' + id, {headers: this.headers});
    }
}
