import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    Resolve,
    Router
} from '@angular/router';
import { Observable } from 'rxjs';
import { TranslationService } from 'angular-l10n';
import { UsersService } from '../../core/service/rest/users/users.service';

@Injectable()
export class StartUserResolver implements Resolve<Observable<any>>
{
    constructor(public translation:TranslationService,
                public userService:UsersService,
                public router:Router)
    {
    }

    public resolve(route:ActivatedRouteSnapshot):Observable<any>
    {
        return this.userService.getCurrentUsers();
    }
}
