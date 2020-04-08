import {
    Injectable,
    Injector
} from "@angular/core";
import { UsersService } from './users.service';
import { GlobalRegistryService } from '../../../global-registry/global-registry.service';
import {GetSitemapService} from "../sitemap/wbs-sitemap.service";
import {WbsSitemapHelper} from "../sitemap/data/wbs-sitemap.helper";
import {
    Observable,
    of
} from 'rxjs';
import { combineLatest } from 'rxjs/operators';


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
        this.sitemapService.getSitemaps().subscribe((result:any) =>
        {
            this.sitemapHelper.sitemaps = result;
        });

        return null;
        //return combineLatest(
        //    this.userService.getCurrentUsers(),
        //    (userData) =>
        //    {
        //        if(userData)
        //        {
        //            console.log('user has permissions');
        //
        //            this.globalRegistryService.setisLoggedIn(true);
        //            this.globalRegistryService.setGravatarHash(userData.gravatar);
        //        }
        //        else
        //        {
        //            this.redirectToLoginPage()
        //        }
        //    }
        //).catch((error:Error) =>
        //{
        //    this.redirectToLoginPage();
        //    return of(error);
        //}).toPromise()
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
            //let url:string = window.location.protocol +
            //                 '//' +
            //                 window.location.host.slice(window.location.host.indexOf('.') + 1);
            let url:string = window.location.origin + '/#/login';

            window.location.replace(url);
        }
    }
}