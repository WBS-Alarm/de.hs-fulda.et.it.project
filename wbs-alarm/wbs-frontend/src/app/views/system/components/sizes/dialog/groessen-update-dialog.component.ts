import {
    Component,
    Inject,
    OnDestroy,
    OnInit,
} from '@angular/core';

import {
    MAT_DIALOG_DATA,
    MatDialogRef
} from '@angular/material/dialog';


@Component({
    // tslint:disable-next-line:component-selector
    selector: 'groessen-update-dialog',
    templateUrl: './groessen-update-dialog.component.html'
})
export class GroessenUpdateDialogComponent implements OnInit, OnDestroy
{
    public groesseUpdate:{ name:string; bestandsgrenze:number } = {
        name:           '',
        bestandsgrenze: 0
    };

    constructor(public dialogRef:MatDialogRef<GroessenUpdateDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public groesse:any)
    {
    }

    public ngOnInit():void
    {
        this.groesseUpdate.name = this.groesse[0].groesse.name;
        this.groesseUpdate.bestandsgrenze = this.groesse[0].groesse.bestandsgrenze;
    }

    public ngOnDestroy():void
    {
        // needed for aot
    }
}
