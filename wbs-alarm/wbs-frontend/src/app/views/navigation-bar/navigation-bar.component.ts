import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {NavigationEnd} from "@angular/router";
import {isNullOrUndefined} from "util";

@Component({
    selector:      'navigation-bar',
    template:      require('./navigation-bar.component.html'),
    styles:        [require('./navigation-bar.component.scss')],
})
export class NavigationBarComponent implements OnInit
{
    protected isLoginActive:boolean;

    private readonly gravatarUrl:string = 'https://www.gravatar.com/avatar/';

    constructor(private router:Router)
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

}
