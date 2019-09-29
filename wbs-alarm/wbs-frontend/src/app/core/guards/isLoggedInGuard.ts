import { Injectable } from "@angular/core";
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
        if(localStorage.getItem('accessToken'))
        {
            return true
        }
        else
        {
            this.router.navigate(['login']);
            return false;
        }
    }
}