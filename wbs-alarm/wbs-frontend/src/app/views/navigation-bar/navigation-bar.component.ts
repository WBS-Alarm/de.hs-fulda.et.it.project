import {
    AfterViewInit,
    Component,
    OnInit
} from "@angular/core";
import { Router } from "@angular/router";
import { NavigationEnd } from "@angular/router";
import { isNullOrUndefined } from "util";
import { MenuDataInterface } from "./data/menu-data.interface";
import { TranslationService } from "angular-l10n";
import { GlobalRegistryService } from '../../core/global-registry/global-registry.service';
import {AlertService} from "@plentymarkets/terra-components";
import {BehaviorSubject} from "rxjs";

@Component({
    selector: 'navigation-bar',
    templateUrl: './navigation-bar.component.html',
    styleUrls:   ['./navigation-bar.component.scss'],
})
export class NavigationBarComponent implements OnInit
{
    public isLoginActive:boolean;

    public readonly gravatarUrl:string = 'https://www.gravatar.com/avatar/';

    public menu:Array<MenuDataInterface> = [
        {
            name:      this.translation.translate('start'),
            url:       'plugin/start',
            isVisible: true
        },
        {
            name:      this.translation.translate('booking.booking'),
            url:       'plugin/booking',
            isVisible: true
        },
        {
            name:      this.translation.translate('system.system'),
            url:       'plugin/system',
            isVisible: true
        },
        {
            name: 'Berichte',
            url: 'plugin/reports',
            isVisible: true
        }
    ];

    constructor(public router:Router,
                public translation:TranslationService,
                public alert:AlertService,
                public globalRegistryService:GlobalRegistryService)
    {

    }

    public ngOnInit():void
    {
       this.isLoginActive = this.router.isActive('login', true) && !this.globalRegistryService.getIsLoggedIn();

        this.subscribeToRouter();
    }

    public toggleMenu():void
    {
        let open = document.getElementById('navbarSupportedContent').classList.contains('show');

        if(open)
        {
            document.getElementById('navbarSupportedContent').classList.remove('show');
            this.globalRegistryService.toggled$.next(false);
        }
        else
        {
            document.getElementById('navbarSupportedContent').classList.add('show');
            this.globalRegistryService.toggled$.next(true);
        }
    }

    public get userImageLink():string
    {
        let hash:string = this.globalRegistryService.getGravatarHash();

        let gravatarBaseUrl:string = 'https://www.gravatar.com/avatar/';

        let link:string = gravatarBaseUrl;

        if(!isNullOrUndefined(hash))
        {
            link += hash;
        }

        return link;
    }

    public subscribeToRouter():void
    {
        this.router.events.subscribe((value:any) =>
        {
            if(!isNullOrUndefined(value.url))
            {
                this.isLoginActive = value.url === '/login';
            }
        })
    }

    public navigate(url:string):void
    {
        this.router.navigateByUrl(url);
    }

       public logout():void
    {
        let today:Date = new Date(Date.now());

        today.setTime(today.getTime() - 1);

        let expires:string = 'expires=' + today.toUTCString();

        document.cookie = 'loginToken=' +';' + expires;

        this.globalRegistryService.setisLoggedIn(false);
        this.globalRegistryService.isLoginActive = false;

        this.alert.success('Sie werden ausgeloggt!');

        let body:HTMLElement = document.getElementById('body');
        let html:HTMLElement = document.getElementById('html');

        body.classList.remove('isLoggedIn');
        html.classList.remove('isLoggedIn');

        this.router.navigate(['login']);
    }

}
