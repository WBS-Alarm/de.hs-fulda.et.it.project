import {Component, OnInit} from '@angular/core';
import {CarrierService} from "../../../../core/service/rest/carrier/carrier.service";

@Component({
    selector: 'carrier',
    template: require('./system-carrier.component.html'),
    styles:   [require('./system-carrier.component.scss')]
})
export class SystemCarrierComponent implements OnInit
{
    protected newCarrierName:string;

    constructor(private carrierService:CarrierService)
    {

    }

    public ngOnInit():void
    {

    }

    protected saveCarrier():void
    {
        this.carrierService.createCarrier(this.newCarrierName)
    }
}