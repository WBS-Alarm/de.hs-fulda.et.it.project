import {
    Component,
    OnInit
} from '@angular/core';
import {
    ActivatedRoute,
    Data
} from '@angular/router';
import {
    AlertService,
    TerraNodeTreeConfig
} from '@plentymarkets/terra-components';
import { SystemGlobalSettingsService } from '../../../system-global-settings.service';
import { ExampleTreeData } from '../../../system.component';
import { CarrierService } from '../../../../../core/service/rest/carrier/carrier.service';
import { Observable } from 'rxjs';
import { SystemCarrierInterface } from '../data/system-carrier.interface';

@Component({
    // tslint:disable-next-line:component-selector
    selector:    'edit-carrier',
    templateUrl: './system-edit-carrier.component.html',
    styleUrls:   ['./system-edit-carrier.component.scss']
})
export class SystemEditCarrierComponent implements OnInit
{
    public routeData$:Observable<Data>;

    constructor(public route:ActivatedRoute,
                public carrierService:CarrierService,
                public alert:AlertService,
                public systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>,
                public systemGlobalSettings:SystemGlobalSettingsService)
    {

    }

    public ngOnInit():void
    {
        this.routeData$ = this.route.data;
    }

    public saveCarrier(carrier:SystemCarrierInterface):void
    {
        this.carrierService.updateCarrier(carrier).subscribe(
            (result:any):any =>
            {
                this.alert.success('Änderungen gespeichert!');

                this.systemTreeConfig.currentSelectedNode.name = carrier.name;
            },
            (error:any):any =>
            {
                this.alert.error('Änderungen konnten nicht gespeichert werden. ' + error.error.message);
            });
    }

    public deleteCarrier(carrier:SystemCarrierInterface):void
    {
        this.carrierService.deleteCarrier(carrier).subscribe(
            (result:any):any =>
            {
                this.alert.success('Der Träger wurde gelöscht!');

                this.systemTreeConfig.removeNodeById(this.systemTreeConfig.currentSelectedNode.id);
            },
            (error:any):any =>
            {
                this.alert.error('Der Träger konnte nicht gelöscht werden! ' + error.error.message);
            }
        );
    }
}
