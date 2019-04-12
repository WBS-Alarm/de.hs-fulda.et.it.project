import {Component} from "../../../../../../../node_modules/@angular/core";
import {
    TerraAlertComponent,
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
    selector: 'system-new-targetplace',
    template: require('./system-new-targetplace.component.html'),
    styles:   [require('./system-new-targetplace.component.scss')]
})
export class SystemNewTargetplaceComponent
{
    private newTargetplaceName:string = '';

    private alert:TerraAlertComponent = TerraAlertComponent.getInstance();


    constructor(private carrierService:CarrierService,
                private route:ActivatedRoute,
                private router:Router,
                private systemGlobalSettings:SystemGlobalSettingsService,
                private systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>)
    {

    }

    private save():void
    {
        this.carrierService.createTargetplace(this.systemGlobalSettings.getTraegerId(), this.newTargetplaceName)
            .subscribe((result:any) =>
                {
                    this.alert.addAlert({
                        msg:              'Der Zielort wurde angelegt!',
                        type:             'success',
                        dismissOnTimeout: null,
                        identifier:       'targetPlaceCreated'
                    });

                    this.newTargetplaceName = '';

                    this.systemGlobalSettings.setZielOrte([result]);

                    this.systemTreeConfig.addChildToNodeById(this.systemTreeConfig.currentSelectedNode.id,
                        {
                            id:        'zielort' + result.id,
                            name:      result.name,
                            isVisible: true,
                            onClick: ():void =>
                                     {
                                         this.router.navigateByUrl('plugin/system/carrier/' + this.systemGlobalSettings.getTraegerId() + '/targetplace/' + result.id)
                                     }
                        }
                    )
                },
                (error:any) =>
                {
                    this.alert.addAlert({
                        msg:              'Der Zielort wurde nicht angelegt!',
                        type:             'danger',
                        dismissOnTimeout: null,
                        identifier:       'targetPlaceNotCreated'
                    })
                });
    }
}