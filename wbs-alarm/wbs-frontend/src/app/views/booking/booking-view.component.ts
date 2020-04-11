import {Component, OnInit, ViewChild} from '@angular/core';
import {
    AlertService,
    TerraAlertComponent,
    TerraSelectBoxValueInterface,
    TerraSimpleTableCellInterface,
    TerraSimpleTableComponent,
    TerraSimpleTableHeaderCellInterface,
    TerraSimpleTableRowInterface
} from '@plentymarkets/terra-components';
import {CategoryService} from '../../core/service/rest/categories/category.service';
import {GlobalRegistryService} from '../../core/global-registry/global-registry.service';
import {UsersService} from '../../core/service/rest/users/users.service';
import {CarrierService} from '../../core/service/rest/carrier/carrier.service';
import {TransaktionService} from '../../core/service/rest/transaktions/transaktion.service';
import {AlertType} from '@plentymarkets/terra-components/components/alert/alert-type.enum';
import {MatTableDataSource} from "@angular/material/table";
import {SelectionModel} from "@angular/cdk/collections";
import {SystemZielortInterface} from "../system/components/targetplaces/data/system-zielort.interface";

export interface RowData {
    von:SystemZielortInterface;
    nach:SystemZielortInterface;
    kategorie:any;
    groesse:any;
    anzahl:number;
}


@Component({
    selector: 'booking',
    templateUrl: './booking-view.component.html',
    styleUrls:   ['./booking-view.component.scss']
})
export class BookingViewComponent implements OnInit
{
    public _traeger:string = 'Eschenstruth';

    public _zielorte:Array<TerraSelectBoxValueInterface> = [{
        value:   null,
        caption: 'Bitte wählen'
    }];

    public _zielorteKomplett:Array<TerraSelectBoxValueInterface> = [{
        value:   null,
        caption: 'Bitte wählen'
    }];
    public _zielorteEinkauf:Array<TerraSelectBoxValueInterface> = [{
        value:   null,
        caption: 'Bitte wählen'
    }];
    public _zielorteAussonderung:Array<TerraSelectBoxValueInterface> = [{
        value:   null,
        caption: 'Bitte wählen'
    }];
    public _von:any;
    public _nach:any;
    public _modus:string = 'buchen';

    public _kategorien:Array<TerraSelectBoxValueInterface> = [{
        value:   null,
        caption: 'Bitte wählen'
    }];
    public _kategorie:any;
    public _groessen:Array<TerraSelectBoxValueInterface> = [{
        value:   null,
        caption: 'Bitte wählen'
    }];
    public _goresse:any = '';
    public _anzahl:number = 0;

    public _headerList:Array<TerraSimpleTableHeaderCellInterface> = [];
    public _rowList:Array<TerraSimpleTableRowInterface<any>> = [];

    public buchungsliste:Array<{ von:number, nach:number, positions:Array<{ groesse:number, anzahl:number }> }> = [];

    public _traegerId:number;

    @ViewChild('table', {static:true})
    public table:TerraSimpleTableComponent<any>;

    private tableData:Array<RowData> = [];

    public displayedColumns:Array<string> = ['select', 'von', 'nach', 'kategorie', 'größe', 'anzahl'];
    public dataSource:MatTableDataSource<RowData> = new MatTableDataSource<RowData>(this.tableData);
    public selection:SelectionModel<RowData> = new SelectionModel<RowData>(true, []);

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


    constructor(public categoryService:CategoryService,
                public userService:UsersService,
                public alert:AlertService,
                public carrierService:CarrierService,
                public globalRegistryService:GlobalRegistryService,
                public transaktionService:TransaktionService)
    {
    }

    public ngOnInit():void
    {
        this.loadCategories();

        this.buildTableStructure();

        console.log(this._zielorteKomplett);
    }

    public _buchen():void
    {
        this.buchungsliste.forEach((buchung:any) =>
        {
            this.transaktionService.postTransaktion(this._traegerId, buchung).subscribe(
                (result:any) =>
                {
                    this.alert.success('Die Buchung wurde erfolgreich durchgeführt');

                    console.log('Erfolg')
                    console.log(result)
                },
                (error:any) =>
                {
                    this.alert.error('Beim der Buchung ist ein Fehler aufgetreten: ' + error.message);

                    console.log('Fehler')
                    console.log(error)
                })
        })


        //this._rowList.forEach((row:TerraSimpleTableRowInterface<any>) =>
        //{
        //    row.cellList.forEach((cell:TerraSimpleTableCellInterface) =>
        //    {
        //        console.log(cell);
        //    })
        //})
    }


    public fuelleGroesse(kategroie:any):void
    {
        if(kategroie === null)
        {
            this._groessen = [];
        }
        else
        {
            this.categoryService.getGroesseForCategory(kategroie.id).subscribe((groessen) =>
            {
                groessen._embedded.elemente.forEach((groesse) =>
                {
                    this._groessen.push({
                        value:   groesse,
                        caption: groesse.name
                    })
                })
            });
        }
    }

    public leereTabelleUndAendereZielorte():void
    {
        //this._rowList = [];

        if(this._modus === 'buchen')
        {
            this._zielorte = this._zielorteKomplett;
        }
        else if(this._modus === 'einkauf')
        {
            this._zielorte = this._zielorteEinkauf;
            this._von = this._zielorteKomplett.find((zielort:any) =>
            {
                return zielort.caption === 'Wareneingang';
            }).value
        }
        else
        {
            this._zielorte = this._zielorteAussonderung;

            this._nach = this._zielorteKomplett.find((zielort:any) =>
            {
                return zielort.caption === 'Aussonderung';
            }).value
        }

        this._rowList = [];
    }

    public loadCategories():void
    {
        let traegerId:number;

        if(this.globalRegistryService.currentUser)
        {
            traegerId = this.globalRegistryService.currentUser._embedded.traeger[0].id;
            this._traegerId = this.globalRegistryService.currentUser._embedded.traeger[0].id;

            this.loadZielorte(traegerId);

            this.categoryService.getCategories(1).subscribe((kategorien:any) =>
            {
                this.addCategoriesToSelectBox(kategorien._embedded.elemente);
            });
        }
        else
        {
            this.userService.getCurrentUsers().subscribe((user:any) =>
            {
                this.globalRegistryService.currentUser = user;
                traegerId = user._embedded.traeger[0].id;
                this._traegerId = user._embedded.traeger[0].id;

                this.loadZielorte(traegerId);

                this.categoryService.getCategories(this._traegerId).subscribe((kategorien:any) =>
                {
                    this.addCategoriesToSelectBox(kategorien._embedded.elemente);
                });
            })
        }
    }

    public addCategoriesToSelectBox(kategorien:Array<any>):void
    {
        kategorien.forEach((kategorie:any) =>
        {
            this._kategorien.push({
                value:   kategorie,
                caption: kategorie.name
            })
        })
    }

    public loadZielorte(traegerId:number):void
    {
        this.carrierService.listZielorteForTraeger(traegerId).subscribe((zielorte:any) =>
        {
            zielorte._embedded.elemente.forEach((zielort:any) =>
            {
                this._zielorteKomplett.push(
                    {
                        value:   zielort,
                        caption: zielort.name
                    }
                );

                if(zielort.name.indexOf('Lager') > -1)
                {
                    this._zielorteEinkauf.push({
                        value:   zielort,
                        caption: zielort.name
                    })
                }

                if(!(zielort.name.indexOf('Aussonderung') > -1))
                {
                    this._zielorteAussonderung.push({
                        value:   zielort,
                        caption: zielort.name
                    })
                }
            });

            this._zielorte = this._zielorteKomplett;
        })
    }

    public buildTableStructure():void
    {
        let vonCell:TerraSimpleTableHeaderCellInterface = {
            caption: 'Von',
            width:   '100',
        };

        let nachCell:TerraSimpleTableHeaderCellInterface = {
            caption: 'Nach',
            width:   '100',
        };
        let kategorieCell:TerraSimpleTableHeaderCellInterface = {
            caption: 'Kategorie',
            width:   '100',
        };
        let groesseCell:TerraSimpleTableHeaderCellInterface = {
            caption: 'Größe',
            width:   '100',
        };
        let anzahlCell:TerraSimpleTableHeaderCellInterface = {
            caption: 'Anzahl',
            width:   '100',
        };

        this._headerList.push(vonCell, nachCell, kategorieCell, groesseCell, anzahlCell);
    }

    public addRow():void
    {
        this.tableData.push({
            von: this._von,
            nach: this._nach,
            kategorie: this._kategorie,
            groesse: this._goresse,
            anzahl: this._anzahl
        });

        this.dataSource._updateChangeSubscription();

        this.addRowToBuchungsListe();

    }

    public addRowToBuchungsListe():void
    {
        let buchungsKombination:{ von:number, nach:number, positions:Array<{ groesse:number, anzahl:number }> } = this.buchungsliste.find(
            (buchung:{ von:number, nach:number, positions:Array<{ groesse:number, anzahl:number }> }) =>
            {
                return buchung.von === this._von.id && buchung.nach === this._nach.id;
            });

        if(buchungsKombination)
        {
            let kombination:{ groesse:number, anzahl:number } =
                buchungsKombination.positions.find((position:{ groesse:number, anzahl:number }) =>
                {
                    return position.groesse === this._goresse.id;
                });

            if(kombination)
            {
                kombination.anzahl += this._anzahl;
            }
            else
            {
                buchungsKombination.positions.push(
                    {
                        groesse: this._goresse.id,
                        anzahl:  this._anzahl
                    }
                )
            }
        }
        else
        {
            this.buchungsliste.push({
                von:       this._von.id,
                nach:      this._nach.id,
                positions: [
                    {
                        groesse: this._goresse.id,
                        anzahl:  this._anzahl
                    }
                ]
            });
        }

        console.log(this.buchungsliste)
    }

    public deleteRows():void
    {
        this.selection.selected.forEach((row:RowData) =>
        {
            this.deleteRow(row);
        })
    }

    public deleteRow(row:RowData):void
    {
        let idx:number = this.tableData.indexOf(row);
        this.tableData.splice(idx, 1);
        this.dataSource._updateChangeSubscription();

        this.deleteFromBuchungsliste(row);
}

    public deleteFromBuchungsliste(toBeDeleted:RowData):void
    {
        let vonId:number;

        if(this._modus === 'einkauf')
        {
            vonId = this._von.id
        }
        else
        {
            vonId = this._zielorteKomplett.find((zielort:any) =>
            {
                return zielort.caption === toBeDeleted.von.name
            }).value.id;
        }


        let nachId:number;

        if(this._modus === 'aussonderung')
        {
            nachId = this._nach.id;
        }
        else
        {
            nachId = this._zielorteKomplett.find((zielort:any) =>
            {
                return zielort.caption === toBeDeleted.nach.name
            }).value.id;
        }

        let groesseId:number = this._groessen.find((groesse:any) =>
        {
            return groesse.caption === toBeDeleted.groesse.name
        }).value.id;

        let tbdBuchungen:Array<{ von:number, nach:number, positions:Array<{ groesse:number, anzahl:number }> }>;

        tbdBuchungen = this.buchungsliste.filter((buchung:{ von:number, nach:number, positions:Array<{ groesse:number, anzahl:number }> }) =>
        {
            return buchung.von === vonId && buchung.nach === nachId;
        });

        tbdBuchungen.forEach((buchung:{ von:number, nach:number, positions:Array<{ groesse:number, anzahl:number }> }) =>
        {
            let newPositions:Array<{ groesse:number, anzahl:number }> = buchung.positions.filter((position:{ groesse:number, anzahl:number }) =>
            {
                return position.groesse === groesseId;
            });

            newPositions.forEach((position:{ groesse:number, anzahl:number }) =>
            {
                position.anzahl -= toBeDeleted.anzahl;
            })
        });

        this.buchungsliste = tbdBuchungen;

        console.log(this.buchungsliste);
    }
}
