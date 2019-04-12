import {
    Component,
    OnInit
} from '@angular/core';
import { CarrierService } from '../../../../core/service/rest/carrier/carrier.service';
import {
    TerraAlertComponent,
    TerraNodeTreeConfig
} from '@plentymarkets/terra-components';
import {
    ActivatedRoute,
    Data
} from '@angular/router';
import { Observable } from "rxjs";
import { SystemGlobalSettingsService } from '../../system-global-settings.service';
import { SystemZielortInterface } from './data/system-zielort.interface';
import { ExampleTreeData } from '../../system.component';

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
                private systemGlobalSettings:SystemGlobalSettingsService,
                private systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>)
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

                this.systemTreeConfig.currentSelectedNode.name = targetPlace.name;
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

    private delete(targetPlace:SystemZielortInterface):void
    {
        this.carrierService.deleteTargetplace(targetPlace).subscribe((result:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Der Zielort wurde gelöscht!',
                        type:             'success',
                        dismissOnTimeout: null,
                        identifier:       'targetPlaceDeleted'
                    }
                );

                this.systemTreeConfig.removeNodeById(this.systemTreeConfig.currentSelectedNode.id)
            },
            (error:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Der Zielort konnte nicht gelöscht werden!',
                        type:             'danger',
                        dismissOnTimeout: null,
                        identifier:       'targetPlaceNotDeleted'
                    }
                )
            })
    }

    private lock(targetPlace:SystemZielortInterface):void
    {
        this.carrierService.lockTargetplace(targetPlace).subscribe(
            (result:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Der Zielort wurde für die Erfassung gesperrt!',
                        type:             'success',
                        dismissOnTimeout: null,
                        identifier:       'targetplaceLocked'
                    }
                )
            },
            (error:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Der Zielort konnte nicht für die Erfassung gesperrt werden!',
                        type:             'success',
                        dismissOnTimeout: null,
                        identifier:       'targetPlaceNotLocked'
                    }
                )
            })
    }
}