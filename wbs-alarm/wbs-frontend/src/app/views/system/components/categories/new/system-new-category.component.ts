import {Component} from "@angular/core";
import {CarrierService} from "../../../../../core/service/rest/carrier/carrier.service";
import {SystemGlobalSettingsService} from "../../../system-global-settings.service";
import {
    AlertService,
    TerraAlertComponent,
    TerraNodeTreeConfig
} from "@plentymarkets/terra-components";
import { ExampleTreeData } from '../../../system.component';
import { Router } from '@angular/router';
import {CategoryService} from "../../../../../core/service/rest/categories/category.service";

@Component({
    selector: 'system-new-categories',
    templateUrl: './system-new-category.component.html',
    styleUrls:   ['./system-new-category.component.scss']
})
export class SystemNewCategoryComponent
{
    public categoryName:string;

    constructor(public carrierService:CarrierService,
                public categoryService:CategoryService,
                public systemGlobalSettings:SystemGlobalSettingsService,
                public alert:AlertService,
                public nodeTreeConfig:TerraNodeTreeConfig<ExampleTreeData>,
                public router:Router)
    {

    }

    public save()
    {
        let traegerId:number = this.systemGlobalSettings.getTraegerId();

        this.carrierService.createCategory(traegerId, this.categoryName).subscribe(
            (result:any) =>
            {
                this.alert.success('Kategorie wurde erstellt!');

                let kategorieId:any = result.headers.get('Location').split('/wbs/kategorie/')[1];

                this.nodeTreeConfig.addChildToNodeById(this.nodeTreeConfig.currentSelectedNode.id,
                    {
                        id:        'kategorie' + kategorieId,
                        name:      this.categoryName,
                        isVisible: true,
                        onClick: ():void =>
                                   {
                                       this.router.navigateByUrl('plugin/system/carrier/' + traegerId + '/category/' + kategorieId)
                                   }
                    }
                );

                this.categoryService.getCategory(kategorieId).subscribe((result:any) =>
                {
                    this.systemGlobalSettings.setKategorien([result]);
                });

                this.categoryName = '';
            },
            (error:any) =>
            {
                this.alert.error('Die Kategorie konnte nicht erstellt werden!');
            }
        )
    }
}