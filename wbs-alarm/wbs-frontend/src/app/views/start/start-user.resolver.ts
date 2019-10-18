import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    Resolve,
    Router
} from '@angular/router';
import { Observable } from 'rxjs';
import { TranslationService } from 'angular-l10n';
import { BestaendeService } from '../../core/service/rest/bestaende/bestaende.serice';
import { isNullOrUndefined } from "util";
import { UsersService } from '../../core/service/rest/users/users.service';

@Injectable()
export class StartUserResolver implements Resolve<Observable<any>>
{
    constructor(private translation:TranslationService,
                private userService:UsersService,
                private router:Router)
    {
    }

    public resolve(route:ActivatedRouteSnapshot):Observable<any>
    {
        return this.userService.getCurrentUsers();
    }
}