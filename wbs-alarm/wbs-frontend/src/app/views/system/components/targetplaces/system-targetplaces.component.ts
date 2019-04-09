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
import { SystemZielortInterface } from './data/system-zielort.interface';

@Component({
    selector: 'system-targetplace',
    template: require('./system-targetplaces.component.html'),
    styles:   [require('./system-targetplaces.component.scss')]
})
export class SystemTargetplacesComponent implements OnInit
{
    private alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    protected routeData$:Observable<Data>;

    constructor(private carrierService:CarrierService,
                private route:ActivatedRoute,
                private systemGlobalSettings:SystemGlobalSettingsService)
    {

    }

    public ngOnInit():void
    {
        this.routeData$ = this.route.data;
    }

    private save(targetPlace:SystemZielortInterface):void
    {
        this.carrierService.updateTargetplace(targetPlace).subscribe(
            (result:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Änderungen gespeichert!',
                        type:             'success',
                        dismissOnTimeout: null,
                        identifier:       'targetPlaceEdited'
                    }
                )
            },
            (error:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Änderungen konnten nicht gespeichert werden: ' + error.error.message,
                        type:             'danger',
                        dismissOnTimeout: null,
                        identifier:       'targetPlaceNotEdited'
                    }
                )
            })
    }

    private delete():void
    {

    }
}