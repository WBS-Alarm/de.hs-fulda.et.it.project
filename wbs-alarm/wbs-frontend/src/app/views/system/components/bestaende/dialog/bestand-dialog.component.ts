import {
    Component,
    OnDestroy,
    OnInit,
} from '@angular/core';

import { MatDialogRef } from '@angular/material/dialog';

@Component({
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

    }

    public ngOnDestroy():void
    {
        // needed for aot
    }
}