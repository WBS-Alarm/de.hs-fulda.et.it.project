import {
    Component,
    OnInit
} from "@angular/core";
import { Router } from "@angular/router";
import { NavigationEnd } from "@angular/router";
import { isNullOrUndefined } from "util";
import { MenuDataInterface } from "./data/menu-data.interface";
import { TranslationService } from "angular-l10n";
import { GlobalRegistryService } from '../../core/global-registry/global-registry.service';

@Component({
    selector: 'navigation-bar',
    template: require('./navigation-bar.component.html'),
    styles:   [require('./navigation-bar.component.scss')],
})
export class NavigationBarComponent implements OnInit
{
    protected isLoginActive:boolean;

    private readonly gravatarUrl:string = 'https://www.gravatar.com/avatar/';

    private menu:Array<MenuDataInterface> = [
        {
            name:      this.translation.translate('start'),
            url:       'plugin/start',
            isVisible: true
        },
        {
            name:      this.translation.translate('example'),
            url:       'plugin/example',
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
        }
    ];

    constructor(private router:Router,
                private translation:TranslationService,
                private globalRegistryService:GlobalRegistryService)
    {

    }

    public ngOnInit():void
    {
        this.isLoginActive = this.router.isActive('login', true);

        this.subscribeToRouter();
    }

    protected get userImageLink():string
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

    private subscribeToRouter():void
    {
        this.router.events.subscribe((value:any) =>
        {
            if(!isNullOrUndefined(value.url))
            {
                this.isLoginActive = value.url === '/login';
            }
        })
    }

    protected navigate(url:string):void
    {
        this.router.navigateByUrl(url);
    }

    protected logout():void
    {
        console.log('Logout successful!')
    }
}
