import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    Resolve,
    Router
} from '@angular/router';
import {
    L10nTranslationService
} from 'angular-l10n';
import { Observable } from 'rxjs';
import { isNullOrUndefined } from 'util';
import { BestaendeService } from '../../../../core/service/rest/bestaende/bestaende.serice';

@Injectable()
export class SystemBestaendeResolver implements Resolve<Observable<any>>
{
    constructor(public translation:L10nTranslationService,
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
