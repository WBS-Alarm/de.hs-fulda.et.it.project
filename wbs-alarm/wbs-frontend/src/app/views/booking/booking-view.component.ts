import {
    Component,
    OnInit
} from '@angular/core';
import { TerraSelectBoxValueInterface } from '@plentymarkets/terra-components';

@Component({
    selector: 'booking',
    template: require('./booking-view.component.html'),
    styles:   [require('./booking-view.component.scss')]
})
export class BookingViewComponent implements OnInit
{
    public _traeger:string = 'Eschenstruth';
    public _zielorte:Array<TerraSelectBoxValueInterface> = [];
    public _von:TerraSelectBoxValueInterface;
    public _nach:TerraSelectBoxValueInterface;
    public _modus:string ='buchen';

    constructor()
    {

    }

    public ngOnInit():void
    {
        this._zielorte = [
            {
                value: 1,
                caption: 'Eschenstruth'
        },
            {
                value: 2,
                caption: 'Helsa'
            }]
    }

    public _buchen():void
    {
        console.log('von:' + this._von);
        console.log('nach:' + this._nach);
    }
}