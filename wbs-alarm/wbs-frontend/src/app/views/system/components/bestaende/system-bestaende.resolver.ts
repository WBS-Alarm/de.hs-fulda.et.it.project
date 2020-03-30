import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    Resolve,
    Router
} from '@angular/router';
import { TranslationService } from 'angular-l10n';
import { UsersService } from '../../../../core/service/rest/users/users.service';
import { SystemGlobalSettingsService } from '../../system-global-settings.service';
import { Observable } from 'rxjs';
import { SystemZielortInterface } from '../targetplaces/data/system-zielort.interface';
import { isNullOrUndefined } from "util";
import { BestaendeService } from '../../../../core/service/rest/bestaende/bestaende.serice';

@Injectable()
export class SystemBestaendeResolver implements Resolve<Observable<any>>
{
    constructor(public translation:TranslationService,
                public bestaendeService:BestaendeService,
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

        return this.bestaendeService.getBestaendeFuerZielort(targetPlaceId);
    }
}