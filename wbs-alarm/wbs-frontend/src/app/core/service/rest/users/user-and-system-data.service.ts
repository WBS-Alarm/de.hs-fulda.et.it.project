import {
    Injectable,
    Injector
} from "@angular/core";
import { UsersService } from './users.service';
import { Observable } from "rxjs/Observable";
import { GlobalRegistryService } from '../../../global-registry/global-registry.service';
import {GetSitemapService} from "../sitemap/wbs-sitemap.service";
import {WbsSitemapHelper} from "../sitemap/data/wbs-sitemap.helper";


@Injectable()
export class UserAndSystemDataService
{
    private userService:UsersService;

    private sitemapService:GetSitemapService;

    constructor(injector:Injector,
                private globalRegistryService:GlobalRegistryService,
                private sitemapHelper:WbsSitemapHelper)
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

        return Observable.combineLatest(
            this.userService.getCurrentUsers(),
            (userData) =>
            {
                if(userData)
                {
                    console.log('user has permissions');

                    this.globalRegistryService.setisLoggedIn(true);
                    this.globalRegistryService.setGravatarHash(userData.gravatar);
                }
                else
                {
                    this.redirectToLoginPage()
                }
            }
        ).catch((error:Error) =>
        {
            this.redirectToLoginPage();
            return Observable.of(error);
        }).toPromise()
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