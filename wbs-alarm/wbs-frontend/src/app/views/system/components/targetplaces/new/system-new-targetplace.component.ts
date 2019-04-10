import {Component} from "../../../../../../../node_modules/@angular/core";
import { TerraAlertComponent } from '@plentymarkets/terra-components';
import { CarrierService } from '../../../../../core/service/rest/carrier/carrier.service';
import { ActivatedRoute } from '@angular/router';
import { SystemGlobalSettingsService } from '../../../system-global-settings.service';

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
                private systemGlobalSettings:SystemGlobalSettingsService)
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

                    this.newTargetplaceName = '':
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