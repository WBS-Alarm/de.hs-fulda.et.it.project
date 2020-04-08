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

@Injectable()
export class SystemUserResolver implements Resolve<UserDataInterface>
{
    constructor(public translation:TranslationService,
                public userService:UsersService,
                public systemGloabalSettingsService:SystemGlobalSettingsService,
                public router:Router)
    {
    }

    public resolve(route:ActivatedRouteSnapshot):UserDataInterface
    {
        let userId:number = +route.params['userId'];

        if(isNullOrUndefined(userId) || isNaN(userId))
        {
            return;
        }

        return this.systemGloabalSettingsService.getSingleUser(userId)
    }
}
