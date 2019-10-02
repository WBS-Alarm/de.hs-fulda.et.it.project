import {
    EventEmitter,
    Injectable,
    Output
} from "@angular/core";
import { TerraAlertComponent } from '@plentymarkets/terra-components';
import {
    ActivatedRouteSnapshot,
    Router,
    RouterStateSnapshot
} from "@angular/router";
import { UsersService } from '../service/rest/users/users.service';
import { GlobalRegistryService } from '../global-registry/global-registry.service';

@Injectable()
export class IsLoggedInGuard
{
    private alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    constructor(private globalRegistryService:GlobalRegistryService,
                private router:Router)
    {

    }

    public canActivate(route:ActivatedRouteSnapshot, state:RouterStateSnapshot):boolean
    {
        //if(localStorage.getItem('accessToken') && this.globalRegistryService.getIsLoggedIn())
        let body:HTMLElement = document.getElementById('body');
        let html:HTMLElement = document.getElementById('html');

        if(localStorage.getItem('accessToken'))
        {
            if(!this.globalRegistryService.getIsLoggedIn())
            {
                this.globalRegistryService.setisLoggedIn(true);
            }
            body.classList.add('isLoggedIn');
            html.classList.add('isLoggedIn');
            return true
        }
        else
        {
            body.classList.remove('isLoggedIn');
            html.classList.remove('isLoggedIn');
            this.router.navigate(['login']);
            return false;
        }
    }
}