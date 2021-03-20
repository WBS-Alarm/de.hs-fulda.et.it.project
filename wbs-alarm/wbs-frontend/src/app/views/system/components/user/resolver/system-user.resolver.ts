import { isNullOrUndefined } from 'util';
import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    Resolve,
    Router
} from '@angular/router';
import { UsersService } from '../../../../../core/service/rest/users/users.service';
import {
    L10nTranslationService
} from 'angular-l10n';
import { Observable } from 'rxjs';
import { SystemGlobalSettingsService } from '../../../system-global-settings.service';

@Injectable()
export class SystemUserResolver implements Resolve<Observable<any>>
{
    constructor(public translation:L10nTranslationService,
                public userService:UsersService,
                public systemGloabalSettingsService:SystemGlobalSettingsService,
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

        return this.userService.getOneUser(userId);
        // return this.systemGloabalSettingsService.getSingleUser(userId)
    }
}
