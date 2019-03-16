import {Component, OnInit} from '@angular/core';
import { Language,
         TranslationService } from 'angular-l10n';
import {TerraAlertComponent, TerraSelectBoxValueInterface} from "@plentymarkets/terra-components";
import {LoginService} from "../../core/service/rest/login/login.service";
import {WbsSitemapHelper} from "../../core/service/rest/sitemap/data/wbs-sitemap.helper";
import {Router} from "@angular/router";


@Component({
    selector: 'app-login',
    templateUrl: './app-login.component.html',
    styleUrls: ['./app-login.component.scss']
})
export class AppLoginComponent implements OnInit
{

    @Language()
    public lang:string;

    protected user:Object =
        {
            name: '',
            password: '',
            language: 'de'
        };

    protected languages:Array<TerraSelectBoxValueInterface> = [];

    protected username:string;
    protected password:string;

    protected languageCaption:string;

    constructor(private translation:TranslationService,
                private loginService:LoginService,
                private sitemapHelper:WbsSitemapHelper,
                private router:Router)
    {
    }

    public ngOnInit():void
    {
        this.initTranslations();
        // this.initLanguageValues();
    }

    private initTranslations():void
    {
        this.username = this.translation.translate('login.username', this.lang);
        this.password = this.translation.translate('login.password', this.lang);
        this.languageCaption = this.translation.translate('login.language', this.lang);
    }

    // private initLanguageValues():void
    // {
    //     this.languages =
    //         [
    //             {
    //                 caption: 'Deutsch',
    //                 value: 'de'
    //             },
    //             {
    //                 caption: 'English',
    //                 value: 'en'
    //             }
    //         ];
    // }

    protected login():void
    {
       this.loginService.login(this.user).subscribe(
           (result:string) =>
       {
           console.log(result);
           this.router.navigate(['/plugin/start']);
       },
       (error:any) =>
       {
           this.sitemapHelper.Bearer = error.error.text;
           this.router.navigate(['/plugin/start']);
       },
           () =>
           {
                this.router.navigate(['/plugin/start']);
           });
    }
}
