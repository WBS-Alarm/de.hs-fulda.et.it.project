import {
    Component,
    OnDestroy,
    OnInit,
} from '@angular/core';

import { MatDialogRef } from '@angular/material/dialog';

@Component({
    // tslint:disable-next-line:component-selector
    selector:    'bestand-dialog',
    templateUrl: './bestand-dialog.component.html'
})
export class BestandDialogComponent implements OnInit, OnDestroy
{
    public neuerBestand:number;

    constructor(public dialogRef:MatDialogRef<BestandDialogComponent>)
    {
    }

    public ngOnInit():void
    {
        // Fix lint
    }

    public ngOnDestroy():void
    {
        // needed for aot
    }
}
