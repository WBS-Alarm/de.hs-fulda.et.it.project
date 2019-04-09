import {
    Component,
    OnInit
} from '@angular/core';
import { CarrierService } from '../../../../core/service/rest/carrier/carrier.service';
import { TerraAlertComponent } from '@plentymarkets/terra-components';
import {
    ActivatedRoute,
    Data
} from '@angular/router';
import { Observable } from "rxjs";
import { SystemGlobalSettingsService } from '../../system-global-settings.service';

@Component({
    selector: 'system-targetplace',
    template: require('./system-targetplaces.component.html'),
    styles:   [require('./system-targetplaces.component.scss')]
})
export class SystemTargetplacesComponent
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
                    })
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