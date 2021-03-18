import {
    Inject,
    Injectable
} from '@angular/core';
import {
    L10N_CONFIG,
    L10nConfigRef,
    L10nLoader
} from 'angular-l10n';

/**
 * @description This might be used to dynamically load the configuration data. The configuration must be completed before invoking the load
 *     method of L10nLoader.
 * @link https://robisim74.github.io/angular-l10n/spec/configuration/#dynamic-settings)
 */
@Injectable({
    providedIn: 'root'
})
export class LocalizationLoader
{
    constructor(public l10nLoader:L10nLoader,
                @Inject(L10N_CONFIG) public config:L10nConfigRef)
    {
    }

    public load():Promise<any>
    {
        this.config.locale.language = this.getLanguage();
        return this.l10nLoader.load();
    }

    public getLanguage():string
    {
        let langInLocalStorage:string = localStorage.getItem('plentymarkets_lang_');
        let lang:string = null;

        if(langInLocalStorage !== null)
        {
            lang = langInLocalStorage;
        }
        else
        {
            lang = navigator.language.slice(0, 2).toLocaleLowerCase();

            if(lang !== 'de' && lang !== 'en')
            {
                lang = 'en';
            }

            localStorage.setItem('plentymarkets_lang_', lang);
        }

        return lang;
    }
}
