import {Component, OnDestroy, OnInit} from "@angular/core";
import {MatDialogRef} from "@angular/material/dialog";


@Component(
    {
        selector:    'lock-targetplace-dialog',
        templateUrl: './lock-targetplace-dialog.component.html'
    }
)
export class LockTargetplaceDialogComponent implements OnInit, OnDestroy
{


    constructor(public dialogRef:MatDialogRef<LockTargetplaceDialogComponent>)
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