import {
    Component,
    Input,
    OnInit, ViewChild
} from '@angular/core';
import { Observable } from 'rxjs';
import {
    ActivatedRoute,
    Data
} from '@angular/router';
import {
    TerraSimpleTableCellInterface, TerraSimpleTableComponent,
    TerraSimpleTableHeaderCellInterface,
    TerraSimpleTableRowInterface
} from '@plentymarkets/terra-components';
import { GroesseService } from '../../../../core/service/rest/groesse/groesse.service';
import {MatTableDataSource} from "@angular/material/table";
import {SelectionModel} from "@angular/cdk/collections";

export interface GroesseRow {
    groesse:any;
}

@Component({
    selector: 'system-groessen',
    templateUrl: './system-groessen.component.html',
    styleUrls:   ['./system-groessen.component.scss']
})
export class SystemGroessenComponent implements OnInit
{
    public _groesse:string;

    public routeData$:Observable<Data>;

    public _headerList:Array<TerraSimpleTableHeaderCellInterface> = [];
    public _rowList:Array<TerraSimpleTableRowInterface<any>> = [];

    public groessen:any;

    @Input()
    public categoryId:number;

    // @Input()
    // public alert:TerraAlertComponent;

    @ViewChild('table', {static:true})
    public table:TerraSimpleTableComponent<any>;

    private tableData:Array<GroesseRow> = [];

    public displayedColumns:Array<string> = ['select', 'name'];
    public dataSource:MatTableDataSource<GroesseRow> = new MatTableDataSource<GroesseRow>(this.tableData);
    public selection:SelectionModel<GroesseRow> = new SelectionModel<GroesseRow>(true, []);

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
                public groessenService:GroesseService)
    {}

    public ngOnInit():void
    {
        this.routeData$ = this.route.data;

        this.route.data.subscribe((data:any) =>
        {
            this.groessen = data.groesse._embedded.elemente;

            this._headerList = [];
            this._rowList = [];

            // this.erstelleTabellenStruktur();

            this.groessen.forEach((groesse:any) =>
            {
                this.groessenZurTabelleHinzufuegen(groesse);
            })
        });


    }

    public addGroesse():void
    {
        this.groessenService.addGroesseForTraeger(this.categoryId, this._groesse).subscribe((result:any) =>
        {
            // this.alert.addAlert({
            //         type:             AlertType.success,
            //         msg:              'Die Größe wurde gespeichert.',
            //         dismissOnTimeout: 0
            // })

            this.groessenZurTabelleHinzufuegen(this._groesse);
        },
            (error:any) =>
            {
                // this.alert.addAlert({
                //     type: AlertType.success,
                //     msg:  'Beim Speichern der Größe ist ein Fehler aufgetreten: ' + error.message,
                //     dismissOnTimeout: 0
                // })
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
}