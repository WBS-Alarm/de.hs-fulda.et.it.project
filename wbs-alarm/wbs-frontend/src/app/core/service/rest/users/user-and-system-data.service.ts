import {
    Injectable,
    Injector
} from '@angular/core';
import { UsersService } from './users.service';
import { GlobalRegistryService } from '../../../global-registry/global-registry.service';
import { GetSitemapService } from '../sitemap/wbs-sitemap.service';
import { WbsSitemapHelper } from '../sitemap/data/wbs-sitemap.helper';


@Injectable()
export class UserAndSystemDataService
{
    public userService:UsersService;

    public sitemapService:GetSitemapService;

    constructor(injector:Injector,
                public globalRegistryService:GlobalRegistryService,
                public sitemapHelper:WbsSitemapHelper)
    {
        this.userService = injector.get(UsersService);
        this.sitemapService = injector.get(GetSitemapService);
    }

    public load():Promise<void | Error>
    {
        this.sitemapService.getSitemaps().subscribe((result:any):any =>
        {
            this.sitemapHelper.sitemaps = result;
        });

        return null;
    }

    public redirectToLoginPage():void
    {
        let segment:boolean = window.location.href.indexOf('login') >= 0;

        if(segment)
        {
            console.log(segment);
        }
        else
        {
            let url:string = window.location.origin + '/#/login';

            window.location.replace(url);
        }
    }
}
