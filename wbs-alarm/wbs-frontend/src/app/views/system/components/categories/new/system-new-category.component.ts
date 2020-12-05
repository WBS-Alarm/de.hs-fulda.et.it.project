import { Component } from '@angular/core';
import { CarrierService } from '../../../../../core/service/rest/carrier/carrier.service';
import { SystemGlobalSettingsService } from '../../../system-global-settings.service';
import {
    AlertService,
    TerraNodeTreeConfig
} from '@plentymarkets/terra-components';
import { ExampleTreeData } from '../../../system.component';
import { Router } from '@angular/router';
import { CategoryService } from '../../../../../core/service/rest/categories/category.service';

@Component({
    // tslint:disable-next-line:component-selector
    selector:    'system-new-categories',
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

    public save():void
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
                        onClick:   ():void =>
                                   {
                                       this.router.navigateByUrl('plugin/system/carrier/' + traegerId + '/category/' + kategorieId);
                                   }
                    }
                );

                this.categoryService.getCategory(kategorieId).subscribe((resultCategories:any) =>
                {
                    this.systemGlobalSettings.setKategorien([resultCategories]);
                });

                this.categoryName = '';
            },
            (error:any) =>
            {
                this.alert.error('Die Kategorie konnte nicht erstellt werden! ' + error.error.message);
            }
        );
    }
}
