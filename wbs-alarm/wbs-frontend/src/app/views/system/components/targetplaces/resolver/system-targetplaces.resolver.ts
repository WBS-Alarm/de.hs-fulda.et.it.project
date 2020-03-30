import { isNullOrUndefined } from "util";
import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    Resolve,
    Router
} from '@angular/router';
import { UserDataInterface } from '../../../../../core/service/rest/users/user-data.interface';
import { UsersService } from '../../../../../core/service/rest/users/users.service';
import { TranslationService } from "angular-l10n";
import { Observable } from "rxjs";
import { SystemGlobalSettingsService } from '../../../system-global-settings.service';
import {SystemZielortInterface} from "../data/system-zielort.interface";

@Injectable()
export class SystemTargetplacesResolver implements Resolve<SystemZielortInterface>
{
    constructor(public translation:TranslationService,
                public userService:UsersService,
                public systemGloabalSettingsService:SystemGlobalSettingsService,
                public router:Router)
    {
    }

    public resolve(route:ActivatedRouteSnapshot):SystemZielortInterface
    {
        let targetPlaceId:number = +route.params['targetplaceId'];

        if(isNullOrUndefined(targetPlaceId) || isNaN(targetPlaceId))
        {
            return;
        }

        return this.systemGloabalSettingsService.getSingleZielort(targetPlaceId)
    }
}