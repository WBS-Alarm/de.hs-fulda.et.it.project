import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {NavigationEnd} from "@angular/router";
import {isNullOrUndefined} from "util";
import {MenuDataInterface} from "./data/menu-data.interface";
import {TranslationService} from "angular-l10n";

@Component({
    selector:      'navigation-bar',
    template:      require('./navigation-bar.component.html'),
    styles:        [require('./navigation-bar.component.scss')],
})
export class NavigationBarComponent implements OnInit
{
    protected isLoginActive:boolean;

    private readonly gravatarUrl:string = 'https://www.gravatar.com/avatar/';

    private menu:Array<MenuDataInterface> = [
    {
        name: this.translation.translate('start'),
        url: 'plugin/start',
        isVisible: true
    },
    {
        name: this.translation.translate('example'),
        url: 'plugin/example',
        isVisible: true
    }
];

    constructor(private router:Router,
                private translation:TranslationService)
    {

    }

    public ngOnInit():void
    {
        this.isLoginActive = this.router.isActive('login', true);

        this.subscribeToRouter();
    }

    protected get userImageLink():string
    {
        let url:string = this.gravatarUrl;

        url += '?d=mm';

        return url;
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
}
