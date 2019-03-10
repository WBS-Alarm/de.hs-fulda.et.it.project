import {Component, OnInit} from '@angular/core';
import { Language,
         TranslationService } from 'angular-l10n';
import {TerraSelectBoxValueInterface} from "@plentymarkets/terra-components";

@Component({
    selector: 'app-login',
    templateUrl: './app-login.component.html',
    styleUrls: ['./app-login.component.scss']
})
export class AppLoginComponent implements OnInit {

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

    constructor(private translation:TranslationService)
    {
    }

    public ngOnInit():void
    {
        this.initTranslations();
        this.initLanguageValues();
    }

    private initTranslations():void
    {
        this.username = this.translation.translate('login.username', this.lang);
        this.password = this.translation.translate('login.password', this.lang);
        this.languageCaption = this.translation.translate('login.language', this.lang);
    }

    private initLanguageValues():void
    {
        this.languages =
            [
                {
                    caption: 'Deutsch',
                    value: 'de'
                },
                {
                    caption: 'English',
                    value: 'en'
                }
            ];
    }
}
