import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    Router
} from '@angular/router';
import { UsersService } from '../../../../../core/service/rest/users/users.service';
import { SystemGlobalSettingsService } from '../../../system-global-settings.service';
import { isNullOrUndefined } from 'util';
import {
    L10nTranslationService
} from 'angular-l10n';
import { SystemCategoryInterface } from '../data/system-category.interface';


@Injectable()
export class SystemCategoryResolver
{
    constructor(public translation:L10nTranslationService,
                public userService:UsersService,
                public systemGloabalSettingsService:SystemGlobalSettingsService,
                public router:Router)
    {
    }

    public resolve(route:ActivatedRouteSnapshot):SystemCategoryInterface
    {
        let categoryId:number = +route.params['categoryId'];

        if(isNullOrUndefined(categoryId) || isNaN(categoryId))
        {
            return;
        }

        return this.systemGloabalSettingsService.getSingleCategory(categoryId);
    }
}
