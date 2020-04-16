import {
    Component,
    OnInit
} from '@angular/core';
import { CarrierService } from '../../../../core/service/rest/carrier/carrier.service';
import {
    AlertService,
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
    templateUrl: './system-targetplaces.component.html',
    styleUrls:   ['./system-targetplaces.component.scss']
})
export class SystemTargetplacesComponent implements OnInit
{
    public routeData$:Observable<Data>;

    constructor(public carrierService:CarrierService,
                public route:ActivatedRoute,
                public alert:AlertService,
                public systemGlobalSettings:SystemGlobalSettingsService,
                public systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>)
    {

    }

    public ngOnInit():void
    {
        this.routeData$ = this.route.data;
    }

    public save(targetPlace:SystemZielortInterface):void
    {
        this.carrierService.updateTargetplace(targetPlace).subscribe(
            (result:any) =>
            {
                this.alert.success('Änderungen gespeichert!');

                this.systemTreeConfig.currentSelectedNode.name = targetPlace.name;
            },
            (error:any) =>
            {
                this.alert.error('Änderungen konnten nicht gespeichert werden: ' + error.error.message);
            })
    }

    public delete(targetPlace:SystemZielortInterface):void
    {
        this.carrierService.deleteTargetplace(targetPlace).subscribe((result:any) =>
            {
                this.alert.success('Der Zielort wurde gelöscht!');

                this.systemTreeConfig.removeNodeById(this.systemTreeConfig.currentSelectedNode.id)
            },
            (error:any) =>
            {
                this.alert.error('Der Zielort konnte nicht gelöscht werden!');

            })
    }

    public lock(targetPlace:SystemZielortInterface):void
    {
        this.carrierService.lockTargetplace(targetPlace).subscribe(
            (result:any) =>
            {
                this.alert.success('Der Zielort wurde für die Erfassung gesperrt!');
            },
            (error:any) =>
            {
                this.alert.error('Der Zielort konnte nicht für die Erfassung gesperrt werden!');
            })
    }
}