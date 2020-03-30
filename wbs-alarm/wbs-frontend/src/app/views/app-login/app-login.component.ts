import {Component, OnInit} from '@angular/core';
import {Language, TranslationService} from 'angular-l10n';
import {TerraAlertComponent, TerraSelectBoxValueInterface} from "@plentymarkets/terra-components";
import {LoginService} from "../../core/service/rest/login/login.service";
import {WbsSitemapHelper} from "../../core/service/rest/sitemap/data/wbs-sitemap.helper";
import {Router} from "@angular/router";
import {GlobalRegistryService} from '../../core/global-registry/global-registry.service';
import {UsersService} from '../../core/service/rest/users/users.service';


@Component({
    selector: 'app-login',
    templateUrl: './app-login.component.html',
    styleUrls: ['./app-login.component.scss']
})
export class AppLoginComponent implements OnInit
{
    public alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    @Language()
    public lang:string;

    public user:any =
        {
            name: '',
            password: '',
            language: 'de'
        };

    public languages:Array<TerraSelectBoxValueInterface> = [];

    public username:string;
    public password:string;

    public languageCaption:string;

    constructor(public translation:TranslationService,
                public loginService:LoginService,
                public globalRegistryService:GlobalRegistryService,
                public sitemapHelper:WbsSitemapHelper,
                public userService:UsersService,
                public router:Router)
    {
    }

    public ngOnInit():void
    {
        this.globalRegistryService.isLoginActive = true;

        this.initTranslations();
        // this.initLanguageValues();

        // Get the input field
        let input = document.getElementById('password');

        // Execute a function when the user releases a key on the keyboard
        input.addEventListener('keyup', function(event) {
            // Number 13 is the "Enter" key on the keyboard
            if (event.keyCode === 13) {
                // Cancel the default action, if needed
                event.preventDefault();
                // Trigger the button element with a click
                document.getElementById('login-button').click();
            }
        });
    }

    public initTranslations():void
    {
        this.username = this.translation.translate('login.username', this.lang);
        this.password = this.translation.translate('login.password', this.lang);
        this.languageCaption = this.translation.translate('login.language', this.lang);
    }

    // public initLanguageValues():void
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

    public login():void
    {
       this.loginService.login(this.user).subscribe(
           (result:string) =>
       {
           this.alert.addAlert({
               msg:              'Sie werden eingeloggt',
               type:             'success',
               dismissOnTimeout: null,
               identifier:       'login'
           });


           let today:Date = new Date(Date.now());

           today.setTime(today.getTime() + 1*24*60*60*1000);

           let expires:string = 'expires=' + today.toUTCString();

           document.cookie = 'loginToken=' + result +  ';' + expires;
           this.sitemapHelper.Bearer = result;
           this.globalRegistryService.setisLoggedIn(true);
           this.router.navigate(['plugin', 'start']);

           this.globalRegistryService.isLoginActive = false;

           this.userService.getCurrentUsers().subscribe(
               (result:any) =>
               {
                   this.globalRegistryService.setGravatarHash(result.gravatar)
                   this.globalRegistryService.currentUser = result;
               }
           )
       },
       (error:any) =>
       {
           console.log(error);

           this.alert.addAlert({
               msg:              'Falscher Benutzername oder Passwort',
               type:             'danger',
               dismissOnTimeout: null,
               identifier:       'loginError'
           });
       });
    }
}
