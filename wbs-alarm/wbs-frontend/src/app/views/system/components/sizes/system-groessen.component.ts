import {
    Component,
    Input,
    OnInit
} from '@angular/core';
import { Observable } from 'rxjs';
import {
    ActivatedRoute,
    Data
} from '@angular/router';
import {
    TerraAlertComponent,
    TerraSimpleTableCellInterface,
    TerraSimpleTableHeaderCellInterface,
    TerraSimpleTableRowInterface
} from '@plentymarkets/terra-components';
import { GroesseService } from '../../../../core/service/rest/groesse/groesse.service';
import { AlertType } from '@plentymarkets/terra-components/components/alert/alert-type.enum';

@Component({
    selector: 'system-groessen',
    template: require('./system-groessen.component.html'),
    styles:   [require('./system-groessen.component.scss')]
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

    @Input()
    public alert:TerraAlertComponent;


    constructor(private route:ActivatedRoute,
                private groessenService:GroesseService)
    {}

    public ngOnInit():void
    {
        this.routeData$ = this.route.data;

        this.route.data.subscribe((data:any) =>
        {
            this.groessen = data.groesse._embedded.elemente;

            this._headerList = [];
            this._rowList = [];

            this.erstelleTabellenStruktur();

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
            this.alert.addAlert({
                    type:             AlertType.success,
                    msg:              'Die Größe wurde gespeichert.',
                    dismissOnTimeout: 0
            })

            //this.groessenZurTabelleHinzufuegen(result);
        },
            (error:any) =>
            {
                this.alert.addAlert({
                    type: AlertType.success,
                    msg:  'Beim Speichern der Größe ist ein Fehler aufgetreten: ' + error.message,
                    dismissOnTimeout: 0
                })
            })
    }

    public groessenZurTabelleHinzufuegen(groesse:any):void
    {

            let idCell:TerraSimpleTableCellInterface = {caption: groesse.id};

            let nameCell:TerraSimpleTableCellInterface = {
                caption: groesse.name
            };

            let row:TerraSimpleTableRowInterface<any>;

            let cellList:Array<TerraSimpleTableCellInterface> = [];

            cellList.push(idCell, nameCell);


            row = {
                cellList: cellList,
                disabled: false,
                selected: false
            };

            this._rowList.push(row);
    }

    public erstelleTabellenStruktur():void
    {
        let groesseId:TerraSimpleTableHeaderCellInterface = {
            caption: 'ID',
            width:   '100',
        };

        let groesseCaption:TerraSimpleTableHeaderCellInterface = {
            caption: 'GROESSE',
            width:   '100',
        };

        this._headerList.push(groesseId, groesseCaption);
    }

    public selectRow(event:any):void
    {
        event.selected = true;
    }

}