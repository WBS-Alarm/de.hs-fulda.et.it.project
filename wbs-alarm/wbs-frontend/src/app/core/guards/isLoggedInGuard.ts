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
    public alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    constructor(public globalRegistryService:GlobalRegistryService,
                public userService:UsersService,
                public router:Router)
    {

    }

    public canActivate(route:ActivatedRouteSnapshot, state:RouterStateSnapshot):boolean
    {
        let body:HTMLElement = document.getElementById('body');
        let html:HTMLElement = document.getElementById('html');

        if(this.globalRegistryService.getCookie('loginToken').length > 0)
        {
            if(!this.globalRegistryService.getIsLoggedIn())
            {
                this.globalRegistryService.setisLoggedIn(true);
            }

            this.globalRegistryService.isLoginActive = false;

            body.classList.add('isLoggedIn');
            html.classList.add('isLoggedIn');

            return true;
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