import {
    Injectable,
    Injector
} from "@angular/core";
import { UsersService } from './users.service';
import { Observable } from "rxjs/Observable";
import { GlobalRegistryService } from '../../../global-registry/global-registry.service';


@Injectable()
export class UserAndSystemDataService
{
    private userService:UsersService;

    constructor(injector:Injector,
                private globalRegistryService:GlobalRegistryService)
    {
        this.userService = injector.get(UsersService);
    }

    public load():Promise<void | Error>
    {
        return Observable.combineLatest(
            this.userService.getCurrentUsers(),
            (userData) =>
            {
                if(userData)
                {
                    console.log('user has permissions');

                    this.globalRegistryService.setisLoggedIn(true)
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