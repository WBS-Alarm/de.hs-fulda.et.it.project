import {Component, OnInit} from '@angular/core';
import {CarrierService} from "../../../../core/service/rest/carrier/carrier.service";
import {TerraAlertComponent, TerraNodeTreeConfig} from "@plentymarkets/terra-components";
import {ExampleTreeData} from "../../system.component";

@Component({
    selector: 'carrier',
    template: require('./system-carrier.component.html'),
    styles:   [require('./system-carrier.component.scss')]
})
export class SystemCarrierComponent implements OnInit
{
    protected newCarrierName:string;

    private alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    constructor(private carrierService:CarrierService,
                private nodeTreeConfig:TerraNodeTreeConfig<ExampleTreeData>)
    {

    }

    public ngOnInit():void
    {

    }

    protected saveCarrier():void
    {
        this.carrierService.createCarrier(this.newCarrierName).subscribe((result:any) =>
            {
                this.alert.addAlert({
                    msg:              'Der Träger wurde angelegt!',
                    type:             'success',
                    dismissOnTimeout: null,
                    identifier:       'carrierCreated'
                });

                this.nodeTreeConfig.addChildToNodeById(21,{
                    id:        result.id,
                    name:      result.name,
                    isVisible: true
                })
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