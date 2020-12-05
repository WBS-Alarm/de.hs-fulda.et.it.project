import {
    Component,
    OnDestroy,
    OnInit
} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';


@Component({
    // tslint:disable-next-line:component-selector
    selector:    'lock-targetplace-dialog',
    templateUrl: './lock-targetplace-dialog.component.html'
})
export class LockTargetplaceDialogComponent implements OnInit, OnDestroy
{


    constructor(public dialogRef:MatDialogRef<LockTargetplaceDialogComponent>)
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
