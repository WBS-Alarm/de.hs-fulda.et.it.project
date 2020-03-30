import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    Resolve,
    Router
} from '@angular/router';
import {
    TerraMultiCheckBoxValueInterface,
    TerraSimpleTableRowInterface
} from '@plentymarkets/terra-components';
import { TranslationService } from 'angular-l10n';
import { UsersService } from '../../../../core/service/rest/users/users.service';
import { AuthoritiesService } from '../../../../core/service/rest/authorities/authorities.service';
import { Observable } from 'rxjs';
import { isNullOrUndefined } from "util";
import { GroesseService } from '../../../../core/service/rest/groesse/groesse.service';

@Injectable()
export class SystemGroessenResolver implements Resolve<Observable<any>>
{
    constructor(public translation:TranslationService,
                public userService:UsersService,
                public groessenService:GroesseService,
                public authorityService:AuthoritiesService,
                public router:Router)
    {
    }

    public resolve(route:ActivatedRouteSnapshot):Observable<any>
    {
        let traegerId:number = +route.params['carrierId'];
        let categoryId:number = +route.params['categoryId'];

        if(isNullOrUndefined(traegerId) || isNaN(traegerId))
        {
            return;
        }


        return this.groessenService.getGroessenForKategorie(categoryId);
    }

}