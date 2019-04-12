import {Component} from "../../../../../../../node_modules/@angular/core";
import {CarrierService} from "../../../../../core/service/rest/carrier/carrier.service";
import {SystemGlobalSettingsService} from "../../../system-global-settings.service";
import {
    TerraAlertComponent,
    TerraNodeTreeConfig
} from "@plentymarkets/terra-components";
import { ExampleTreeData } from '../../../system.component';
import { Router } from '@angular/router';

@Component({
    selector: 'system-new-categories',
    template: require('./system-new-category.component.html'),
    styles:   [require('./system-new-category.component.scss')]
})
export class SystemNewCategoryComponent
{
    private categoryName:string;

    private alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    constructor(private carrierService:CarrierService,
                private systemGlobalSettings:SystemGlobalSettingsService,
                private nodeTreeConfig:TerraNodeTreeConfig<ExampleTreeData>,
                private router:Router)
    {

    }

    public save()
    {
        let traegerId:number = this.systemGlobalSettings.getTraegerId();

        this.carrierService.createCategory(traegerId, this.categoryName).subscribe(
            (result:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Kategorie wurde erstellt!',
                        type:             'success',
                        dismissOnTimeout: null,
                        identifier:       'categoryCreated'
                    }
                );

                this.nodeTreeConfig.addChildToNodeById(this.nodeTreeConfig.currentSelectedNode.id,
                    {
                        id:        'kategorie' + result.id,
                        name:      result.name,
                        isVisible: true,
                        onClick: ():void =>
                                   {
                                       this.router.navigateByUrl('plugin/system/carrier/' + traegerId + '/category/' + result.id)
                                   }
                    }
                );

                this.systemGlobalSettings.setKategorien([result]);

                this.categoryName = '';
            },
            (error:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Die Kategorie konnte nicht erstellt werden!',
                        type:             'danger',
                        dismissOnTimeout: null,
                        identifier:       'categoryNotCreated'
                    }
                )
            }
        )
    }
}