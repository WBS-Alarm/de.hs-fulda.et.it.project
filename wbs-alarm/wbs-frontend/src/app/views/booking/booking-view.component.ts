import {
    Component,
    OnInit
} from '@angular/core';

@Component({
    selector: 'booking',
    template: require('./booking-view.component.html'),
    styles:   [require('./booking-view.component.scss')]
})
export class BookingViewComponent implements OnInit
{
    constructor()
    {

    }

    public ngOnInit():void
    {
    }
}