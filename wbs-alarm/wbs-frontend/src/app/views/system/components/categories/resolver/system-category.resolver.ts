import {Injectable} from "../../../../../../../node_modules/@angular/core";
import {ActivatedRouteSnapshot, Router} from "../../../../../../../node_modules/@angular/router";
import {SystemCarrierInterface} from "../../carrier/data/system-carrier.interface";
import {UsersService} from "../../../../../core/service/rest/users/users.service";
import {SystemGlobalSettingsService} from "../../../system-global-settings.service";
import {isNullOrUndefined} from "util";
import {TranslationService} from "angular-l10n";
import {SystemCategoryInterface} from "../data/system-category.interface";

@Injectable()
export class SystemCategoryResolver
{
    constructor(public translation:TranslationService,
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

        return this.systemGloabalSettingsService.getSingleCategory(categoryId)
    }
}