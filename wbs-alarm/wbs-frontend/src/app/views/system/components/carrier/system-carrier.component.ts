import {Component, OnInit} from '@angular/core';
import {CarrierService} from "../../../../core/service/rest/carrier/carrier.service";
import {AlertService, TerraAlertComponent, TerraNodeTreeConfig} from "@plentymarkets/terra-components";
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

    constructor(public carrierService:CarrierService,
                public nodeTreeConfig:TerraNodeTreeConfig<ExampleTreeData>,
                public router:Router,
                public alert:AlertService,
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
                this.alert.success('Der Träger wurde angelegt!');

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
                this.alert.error('Beim Anlegen ist ein Fehler aufgetreten!');
            }
        )
    }
}