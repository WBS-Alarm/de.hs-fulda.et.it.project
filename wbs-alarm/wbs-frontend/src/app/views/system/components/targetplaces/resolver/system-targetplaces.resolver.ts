import { isNullOrUndefined } from 'util';
import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    Resolve,
    Router
} from '@angular/router';
import { UsersService } from '../../../../../core/service/rest/users/users.service';
import { TranslationService } from 'angular-l10n';
import { Observable } from 'rxjs';
import { SystemGlobalSettingsService } from '../../../system-global-settings.service';
import { CarrierService } from '../../../../../core/service/rest/carrier/carrier.service';

@Injectable()
export class SystemTargetplacesResolver implements Resolve<Observable<any>>
{
    constructor(public translation:TranslationService,
                public userService:UsersService,
                public carrierService:CarrierService,
                public systemGloabalSettingsService:SystemGlobalSettingsService,
                public router:Router)
    {
    }

    public resolve(route:ActivatedRouteSnapshot):Observable<any>
    {
        let targetPlaceId:number = +route.params['targetplaceId'];

        if(isNullOrUndefined(targetPlaceId) || isNaN(targetPlaceId))
        {
            return;
        }

        return this.carrierService.getTargetPlaceById(targetPlaceId);
    }
}
