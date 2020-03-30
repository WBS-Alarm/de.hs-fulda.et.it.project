import {Component, OnInit} from '@angular/core';
import {CarrierService} from "../../../../core/service/rest/carrier/carrier.service";
import {TerraAlertComponent, TerraNodeTreeConfig} from "@plentymarkets/terra-components";
import {ExampleTreeData} from "../../system.component";
import { Router } from '@angular/router';
import { SystemGlobalSettingsService } from '../../system-global-settings.service';

@Component({
    selector: 'carrier',
    templateUrl: './system-carrier.component.html',
    styleUrls:   ['./system-carrier.component.scss']
})
export class SystemCarrierComponent implements OnInit
{
    public newCarrierName:string;

    public alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    constructor(public carrierService:CarrierService,
                public nodeTreeConfig:TerraNodeTreeConfig<ExampleTreeData>,
                public router:Router,
                public systemTreeSettings:SystemGlobalSettingsService)
    {

    }

    public ngOnInit():void
    {

    }

    public saveCarrier():void
    {
        this.carrierService.createCarrier(this.newCarrierName).subscribe((result:any) =>
            {
                this.alert.addAlert({
                    msg:              'Der Träger wurde angelegt!',
                    type:             'success',
                    dismissOnTimeout: null,
                    identifier:       'carrierCreated'
                });

                this.newCarrierName = '';

                this.nodeTreeConfig.addChildToNodeById(this.nodeTreeConfig.currentSelectedNode.id,{
                    id:        result.id,
                    name:      result.name,
                    isVisible: true,
                    onClick: ():void =>
                             {
                                 this.router.navigateByUrl('plugin/system/carrier/' + result.id)
                             }
                });

                this.systemTreeSettings.setTraegers([result])

            },
            (error:any) =>
            {
                this.alert.addAlert({
                    msg:              'Beim Anlegen ist ein Fehler aufgetreten!',
                    type:             'danger',
                    dismissOnTimeout: null,
                    identifier:       'carrierNotCreated'
                })
            }
        )
    }
}