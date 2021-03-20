import {
    Component,
    OnInit
} from '@angular/core';
import {
    L10nTranslationService
} from 'angular-l10n';
import {
    AlertService,
    TerraSelectBoxValueInterface
} from '@plentymarkets/terra-components';
import { LoginService } from '../../core/service/rest/login/login.service';
import { WbsSitemapHelper } from '../../core/service/rest/sitemap/data/wbs-sitemap.helper';
import { Router } from '@angular/router';
import { GlobalRegistryService } from '../../core/global-registry/global-registry.service';
import { UsersService } from '../../core/service/rest/users/users.service';
import {
    MatDialog,
    MatDialogRef
} from '@angular/material/dialog';
import { ResetPasswordDialogComponent } from './reset-password/reset-password-dialog.component';
import { isNullOrUndefined } from 'util';


@Component({
    selector:    'app-login',
    templateUrl: './app-login.component.html',
    styleUrls:   ['./app-login.component.scss']
})
export class AppLoginComponent implements OnInit
{
    public user:any =
        {
            name:     '',
            password: '',
            language: 'de'
        };

    public languages:Array<TerraSelectBoxValueInterface> = [];

    public username:string;
    public password:string;

    public languageCaption:string;

    constructor(public translation:L10nTranslationService,
                public loginService:LoginService,
                public alert:AlertService,
                public globalRegistryService:GlobalRegistryService,
                public sitemapHelper:WbsSitemapHelper,
                public userService:UsersService,
                public dialog:MatDialog,
                public router:Router)
    {
    }

    public ngOnInit():void
    {
        this.globalRegistryService.isLoginActive = true;

        this.initTranslations();
        // this.initLanguageValues();

        // Get the input field
        let input:HTMLElement = document.getElementById('password');

        // Execute a function when the user releases a key on the keyboard
        input.addEventListener('keyup', (event:any):any =>
        {
            // Number 13 is the 'Enter' key on the keyboard
            if(event.keyCode === 13)
            {
                // Cancel the default action, if needed
                event.preventDefault();
                // Trigger the button element with a click
                document.getElementById('login-button').click();
            }
        });
    }

    public initTranslations():void
    {
        this.username = this.translation.translate('login.username');
        this.password = this.translation.translate('login.password');
        this.languageCaption = this.translation.translate('login.language');
    }

    public login():void
    {
        this.loginService.login(this.user).subscribe(
            (result:string):any =>
            {
                this.alert.success('Sie werden eingeloggt');


                let today:Date = new Date(Date.now());

                today.setTime(today.getTime() + 1 * 24 * 60 * 60 * 1000);

                let expires:string = 'expires=' + today.toUTCString();

                document.cookie = 'loginToken=' + result + ';' + expires;
                this.sitemapHelper.Bearer = result;
                this.globalRegistryService.setisLoggedIn(true);
                this.router.navigate(['plugin',
                                      'start']);

                this.globalRegistryService.isLoginActive = false;

                this.userService.getCurrentUsers().subscribe(
                    (resultUser:any):any =>
                    {
                        this.globalRegistryService.setGravatarHash(resultUser.gravatar);
                        this.globalRegistryService.currentUser = resultUser;
                    }
                );
            },
            (error:any):any =>
            {
                console.log(error);

                this.alert.error('Falscher Benutzername oder Passwort');
            });
    }

    public resetPassword():void
    {

        const resetPasswordDialog:MatDialogRef<ResetPasswordDialogComponent> = this.dialog.open(ResetPasswordDialogComponent,
            {autoFocus: true});


        resetPasswordDialog.afterClosed().subscribe((username:string):any =>
        {
            if(!isNullOrUndefined(username) && username.length > 0)
            {
                this.loginService.resetPassword(username).subscribe((result:any):any =>
                {
                    this.alert.success('Sie haben eine E-Mail mit einem Link zum Erstellen eines neuen Passworts erhalten!');
                });
            }
        });


    }
}
