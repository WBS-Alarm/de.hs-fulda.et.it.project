import { Component } from '@angular/core';
import {
    AlertService,
    TerraNodeTreeConfig
} from '@plentymarkets/terra-components';
import { CarrierService } from '../../../../../core/service/rest/carrier/carrier.service';
import {
    ActivatedRoute,
    Router
} from '@angular/router';
import { SystemGlobalSettingsService } from '../../../system-global-settings.service';
import { ExampleTreeData } from '../../../system.component';

@Component({
    // tslint:disable-next-line:component-selector
    selector:    'system-new-targetplace',
    templateUrl: './system-new-targetplace.component.html',
    styleUrls:   ['./system-new-targetplace.component.scss']
})
export class SystemNewTargetplaceComponent
{
    public newTargetplaceName:string = '';

    constructor(public carrierService:CarrierService,
                public route:ActivatedRoute,
                public alert:AlertService,
                public router:Router,
                public systemGlobalSettings:SystemGlobalSettingsService,
                public systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>)
    {

    }

    public save():void
    {
        this.carrierService.createTargetplace(this.systemGlobalSettings.getTraegerId(), this.newTargetplaceName)
            .subscribe((result:any):any =>
                {
                    this.alert.success('Der Zielort wurde angelegt!');

                    let targetplaceId:string;

                    this.carrierService.getTargetPlace(result.headers.get('Location')).subscribe((targetplace:any):any =>
                    {
                        this.systemGlobalSettings.setZielOrte([targetplace]);

                        this.systemTreeConfig.addChildToNodeById(this.systemTreeConfig.currentSelectedNode.id,
                            {
                                id:        'zielort' + targetplace.id,
                                name:      targetplace.name,
                                isVisible: true,
                                onClick:   ():void =>
                                           {
                                               this.router.navigateByUrl('plugin/system/carrier/' +
                                                                         this.systemGlobalSettings.getTraegerId() + '/targetplace/'
                                                                         + targetplace.id);
                                           }
                            }
                        );
                    });

                    this.newTargetplaceName = '';


                },
                (error:any):any =>
                {
                    this.alert.error('Der Zielort wurde nicht angelegt!');

                });
    }
}
