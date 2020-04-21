import {Component, Input, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {ActivatedRoute, Data} from '@angular/router';
import {AlertService,} from '@plentymarkets/terra-components';
import {GroesseService} from '../../../../core/service/rest/groesse/groesse.service';
import {MatTableDataSource} from "@angular/material/table";
import {SelectionModel} from "@angular/cdk/collections";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {GroessenUpdateDialogComponent} from "./dialog/groessen-update-dialog.component";

export interface GroesseRow {
    groesse: any;
}

@Component({
    selector: 'system-groessen',
    templateUrl: './system-groessen.component.html',
    styleUrls:   ['./system-groessen.component.scss']
})
export class SystemGroessenComponent implements OnInit
{
    public _groesse:string;
    public bestandswarnung:number = 0;

    public routeData$:Observable<Data>;

    public groessen:any;

    @Input()
    public categoryId:number;

    private tableData:Array<GroesseRow> = [];

    public displayedColumns:Array<string> = ['select', 'name', 'bestandsgrenze'];
    public dataSource:MatTableDataSource<GroesseRow> = new MatTableDataSource<GroesseRow>(this.tableData);
    public selection:SelectionModel<GroesseRow> = new SelectionModel<GroesseRow>(false, []);

    /** Whether the number of selected elements matches the total number of rows. */
    isAllSelected() {
        let numSelected = this.selection.selected.length;
        let numRows = this.dataSource.data.length;
        return numSelected === numRows;
    }

    /** Selects all rows if they are not all selected; otherwise clear selection. */
    masterToggle() {
        this.isAllSelected() ?
            this.selection.clear() :
            this.dataSource.data.forEach(row => this.selection.select(row));
    }


    constructor(public route:ActivatedRoute,
                public groessenService:GroesseService,
                public dialog:MatDialog,
                private alert:AlertService)
    {}

    public ngOnInit():void
    {
        this.routeData$ = this.route.data;

        this.route.data.subscribe((data:any) =>
        {
            this.groessen = data.groesse._embedded.elemente;

            this.tableData = [];
            this.dataSource = new MatTableDataSource<GroesseRow>(this.tableData);

            this.groessen.forEach((groesse:any) =>
            {
                this.groessenZurTabelleHinzufuegen(groesse);
            })
        });


    }

    public addGroesse():void
    {
        this.groessenService.addGroesseForTraeger(this.categoryId, {name: this._groesse, bestandsgrenze:this.bestandswarnung}).subscribe((result:any) =>
        {
            this.alert.success('Die Größe wurde gespeichert.');

            let url:string = result.headers.get('Location');

            this.groessenService.getSingleGroesse(url).subscribe((groesse:any) =>
            {
                this.groessenZurTabelleHinzufuegen(groesse);
            })
        },
            (error:any) =>
            {
                this.alert.error('Die Größe konnte nicht gespeichert werden! ' + error.error.message)
            })
    }

    public groessenZurTabelleHinzufuegen(groesse:any):void
    {
        this.tableData.push(
            {
                groesse: groesse
            }
        );

        this.dataSource._updateChangeSubscription();
    }

    public saveGroesse(groesse:any):void
    {
        this.groessenService.updateGroesse(groesse).subscribe(() =>
        {
            this.alert.success('Die Größe wurde erfolgreich gespeichert');
        },
            (error: any) => {
                this.alert.error('Beim Speichern der Größe ist ein Fehler aufgetreten. ' + error.error.message)
            });
    }

    public deleteGroesse():any
    {
        this.groessenService.deleteGroesse(this.selection.selected[0].groesse).subscribe(()=>
        {
            this.alert.success('Die Größe wurde gelöscht.');

            let idx:number = this.tableData.indexOf(this.selection.selected[0]);

            this.tableData.splice(idx, 1);
            this.selection.clear();
            this.dataSource._updateChangeSubscription();
        },
            (error: any) => {
                this.alert.error('Die Größe wurde nicht gelöscht. ' + error.error.message)
            });
    }

    public createBestandWarnung():void
    {
        let selected:any = this.selection.selected;

        const editDialog:MatDialogRef<GroessenUpdateDialogComponent> = this.dialog.open(GroessenUpdateDialogComponent, {autoFocus:true, data: selected});

        editDialog.afterClosed().subscribe((neueGroesse:any) =>
        {
            if(neueGroesse)
            {
                let saveGroesse = this.selection.selected[0].groesse;

                saveGroesse.name = neueGroesse.name;
                saveGroesse.bestandsgrenze = neueGroesse.bestandsgrenze;

                this.saveGroesse(saveGroesse);

                this.selection.selected[0] = saveGroesse;
                this.selection.clear();
                this.dataSource._updateChangeSubscription();
            }
        });
    }
}