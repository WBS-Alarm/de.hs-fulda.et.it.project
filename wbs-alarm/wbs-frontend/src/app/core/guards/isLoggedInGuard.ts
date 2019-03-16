import { Injectable } from "@angular/core";
import { TerraAlertComponent } from '@plentymarkets/terra-components';
import { ActivatedRouteSnapshot,
    RouterStateSnapshot } from "@angular/router";
import { UsersService } from '../service/rest/users/users.service';

@Injectable()
export class IsLoggedInGuard
{
    private alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    constructor(private userService:UsersService)
    {

    }

    public canActivate(route:ActivatedRouteSnapshot, state:RouterStateSnapshot):boolean
    {
        if(localStorage.getItem('accessToken'))
        {
            this.userService.getCurrentUsers().subscribe(
                () =>
                {
                    console.log('erfolg');
                    return true;
                },
                () =>
                {
                    console.log('fehler');
                    return false;
                })
        }
        else
        {
            return false;
        }
    }

    public canActivateChild(childRoute:ActivatedRouteSnapshot, state:RouterStateSnapshot):boolean
    {
        return this.canActivate(childRoute, state);
    }
}