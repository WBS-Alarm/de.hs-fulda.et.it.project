import {
    ActivatedRouteSnapshot,
    Resolve,
    Router
} from '@angular/router';
import { TranslationService } from 'angular-l10n';
import { isNullOrUndefined } from "util";
import { TerraMultiCheckBoxValueInterface } from '@plentymarkets/terra-components';
import { AuthoritiesService } from '../../../../../core/service/rest/authorities/authorities.service';
import { Observable } from 'rxjs';
import { UsersService } from '../../../../../core/service/rest/users/users.service';
import { Injectable } from '@angular/core';

@Injectable()
export class SystemAuthorityResolver implements Resolve<Array<TerraMultiCheckBoxValueInterface>>
{
    constructor(public translation:TranslationService,
                public userService:UsersService,
                public authorityService:AuthoritiesService,
                public router:Router)
    {
    }

    public resolve(route:ActivatedRouteSnapshot):Observable<any>
    {
        let userId:number = +route.params['userId'];

        if(isNullOrUndefined(userId) || isNaN(userId))
        {
            return;
        }

        let returnValue:Array<TerraMultiCheckBoxValueInterface> = []

            this.authorityService.getAuthorities().subscribe((result:any) =>
        {
            result._embedded.authorities.forEach((authority:any) =>
            {
                returnValue.push(
                    {
                        value:    authority.id,
                        caption:  authority.bezeichnung,
                        selected: false
                    }
                )
            });
        },
        (error:any) =>
        {
            console.log(error)
        })

        return this.authorityService.getAuthorities()
    }
}