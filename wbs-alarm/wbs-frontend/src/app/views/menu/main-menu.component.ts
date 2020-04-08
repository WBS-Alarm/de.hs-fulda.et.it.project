import {
    Component,
    OnInit
} from '@angular/core';
import { Language } from 'angular-l10n';
import {
    TerraNodeTreeConfig
} from '@plentymarkets/terra-components';
import { Router } from '@angular/router';
import { TranslationService } from 'angular-l10n';

@Component({
    selector: 'main-menu',
    templateUrl: './main-menu.component.html'
})
export class MainMenuComponent implements OnInit
{
    @Language()
    public lang:string;

    constructor(public treeConfig:TerraNodeTreeConfig<{}>,
                public router:Router,
                public translation:TranslationService)
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

        // this.treeConfig.addNode({
        //     id:        'example',
        //     name:      this.translation.translate('example'),
        //     isVisible: true,
        //     isActive:  this.router.isActive('plugin/example', true),
        //     onClick:   ():void =>
        //                {
        //                    this.router.navigateByUrl('plugin/example');
        //                }
        // });
    }
}
