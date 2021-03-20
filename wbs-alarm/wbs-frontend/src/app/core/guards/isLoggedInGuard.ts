import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    Router,
    RouterStateSnapshot
} from '@angular/router';
import { UsersService } from '../service/rest/users/users.service';
import { GlobalRegistryService } from '../global-registry/global-registry.service';
import { AlertService } from '@plentymarkets/terra-components';

@Injectable()
export class IsLoggedInGuard
{
    constructor(public globalRegistryService:GlobalRegistryService,
                public userService:UsersService,
                public alert:AlertService,
                public router:Router)
    {
        // Fix lint
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
