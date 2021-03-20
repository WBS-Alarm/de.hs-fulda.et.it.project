import {
    Component,
    OnInit
} from '@angular/core';
import {
    L10nTranslationService,
    Language,
} from 'angular-l10n';
import { TerraNodeTreeConfig } from '@plentymarkets/terra-components';
import { Router } from '@angular/router';

@Component({
    // tslint:disable-next-line:component-selector
    selector:    'main-menu',
    templateUrl: './main-menu.component.html'
})
export class MainMenuComponent implements OnInit
{
    @Language()
    public lang:string;

    constructor(public treeConfig:TerraNodeTreeConfig<{}>,
                public router:Router,
                public translation:L10nTranslationService)
    {
    }

    public ngOnInit():void
    {
        this.treeConfig.addNode({
            id:        'start',
            name:      this.translation.translate('start'),
            isVisible: true,
            isActive:  this.router.isActive('plugin/start', true),
            onClick:   ():void =>
                       {
                           this.router.navigateByUrl('plugin/start');
                       }
        });
    }
}
