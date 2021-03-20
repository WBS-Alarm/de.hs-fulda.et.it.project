import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    Resolve,
    Router
} from '@angular/router';
import {
    L10nTranslationService
} from 'angular-l10n';
import { UsersService } from '../../core/service/rest/users/users.service';
import { Observable } from 'rxjs';

@Injectable()
export class StartUserResolver implements Resolve<Observable<any>>
{
    constructor(public translation:L10nTranslationService,
                public userService:UsersService,
                public router:Router)
    {
    }

    public resolve(route:ActivatedRouteSnapshot):Observable<any>
    {
        return this.userService.getCurrentUsers();
    }
}
