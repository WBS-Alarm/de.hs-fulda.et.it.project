import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Data} from "@angular/router";
import {AlertService, TerraAlertComponent, TerraNodeTreeConfig} from "@plentymarkets/terra-components";
import {UsersService} from "../../../../../core/service/rest/users/users.service";
import {SystemGlobalSettingsService} from "../../../system-global-settings.service";
import {ExampleTreeData} from "../../../system.component";
import {CarrierService} from "../../../../../core/service/rest/carrier/carrier.service";
import { Observable } from "rxjs";
import {SystemCarrierInterface} from "../data/system-carrier.interface";

@Component({
    selector: 'edit-carrier',
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
            (result:any) =>
            {
                this.alert.success('Änderungen gespeichert!');

                this.systemTreeConfig.currentSelectedNode.name = carrier.name;
            },
            (error:any) =>
            {
                this.alert.error('Änderungen konnten nicht gespeichert werden');
            })
    }

    public deleteCarrier(carrier:SystemCarrierInterface):void
    {
        this.carrierService.deleteCarrier(carrier).subscribe(
            (result:any) =>
            {
                this.alert.success('Der Träger wurde gelöscht!');

                this.systemTreeConfig.removeNodeById(this.systemTreeConfig.currentSelectedNode.id)
            },
            (error:any) =>
            {
                this.alert.error('Der Träger konnte nicht gelöscht werden!');
            }
        )
    }
}