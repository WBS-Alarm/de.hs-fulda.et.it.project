import {
    Component,
    OnDestroy,
    OnInit
} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
/* tslint:disable */
@Component({
    selector:    'reset-password-dialog',
    templateUrl: './reset-password-dialog.component.html'
})
/* tslint:enable */
export class ResetPasswordDialogComponent implements OnInit, OnDestroy
{
    public username:number;

    constructor(public dialogRef:MatDialogRef<ResetPasswordDialogComponent>)
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
