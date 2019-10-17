import {
    Component,
    OnInit,
    ViewChild
} from '@angular/core';
import {
    TerraButtonInterface,
    TerraSelectBoxValueInterface,
    TerraSimpleTableCellInterface,
    TerraSimpleTableComponent,
    TerraSimpleTableHeaderCellInterface,
    TerraSimpleTableRowInterface
} from '@plentymarkets/terra-components';
import { CategoryService } from '../../core/service/rest/categories/category.service';
import { GlobalRegistryService } from '../../core/global-registry/global-registry.service';
import { UsersService } from '../../core/service/rest/users/users.service';
import { CarrierService } from '../../core/service/rest/carrier/carrier.service';
import { isNullOrUndefined } from 'util';

@Component({
    selector: 'booking',
    template: require('./booking-view.component.html'),
    styles:   [require('./booking-view.component.scss')]
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
    private _rowList:Array<TerraSimpleTableRowInterface<any>> = [];

    private buchungsliste:Array<{ von:string, nach:string, positions:Array<{ groesse:string, anzahl:number }> }> = []


    @ViewChild('table')
    public table:TerraSimpleTableComponent<any>;


    constructor(private categoryService:CategoryService,
                private userService:UsersService,
                private carrierService:CarrierService,
                private globalRegistryService:GlobalRegistryService)
    {
    }

    public ngOnInit():void
    {
        this.loadCategories();

        this.buildTableStructure();
    }

    public _buchen():void
    {
        this._rowList.forEach((row:TerraSimpleTableRowInterface<any>) =>
        {
            row.cellList.forEach((cell:TerraSimpleTableCellInterface) =>
            {
                console.log(cell);
            })
        })
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
        }
        else
        {
            this._zielorte = this._zielorteAussonderung;
        }
    }

    private loadCategories():void
    {
        let traegerId:number;

        if(this.globalRegistryService.currentUser)
        {
            traegerId = this.globalRegistryService.currentUser._embedded.traeger[0].id;

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

                this.loadZielorte(traegerId);

                this.categoryService.getCategories(1).subscribe((kategorien:any) =>
                {
                    this.addCategoriesToSelectBox(kategorien._embedded.elemente);
                });
            })
        }
    }

    private addCategoriesToSelectBox(kategorien:Array<any>):void
    {
        kategorien.forEach((kategorie:any) =>
        {
            this._kategorien.push({
                value:   kategorie,
                caption: kategorie.name
            })
        })
    }

    private loadZielorte(traegerId:number):void
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

    private buildTableStructure():void
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

    private addRow():void
    {
        let vonCell:TerraSimpleTableCellInterface = {caption: ''};

        if(this._modus != 'einkauf')
        {
            vonCell = {
                caption: this._von.name
            };
        }

        let nachCell:TerraSimpleTableCellInterface = {caption: ''};

        if(this._modus != 'aussonderung')
        {
            nachCell = {
                caption: this._nach.name
            };
        }

        let kategorieCell:TerraSimpleTableCellInterface = {
            caption: this._kategorie.name
        };
        let groesseCell:TerraSimpleTableCellInterface = {
            caption: this._goresse.name
        };
        let anzahlCell:TerraSimpleTableCellInterface = {
            caption: this._anzahl
        };

        let row:TerraSimpleTableRowInterface<any>;

        let cellList:Array<TerraSimpleTableCellInterface> = [];

        cellList.push(vonCell, nachCell, kategorieCell, groesseCell, anzahlCell);


        row = {
            cellList: cellList,
            disabled: false,
            selected: false
        };

        this._rowList.push(row);

        this.addRowToBuchungsListe();

    }

    private addRowToBuchungsListe():void
    {
        let buchungsKombination:{ von:string, nach:string, positions:Array<{ groesse:string, anzahl:number }> } = this.buchungsliste.find(
            (buchung:{ von:string, nach:string, positions:Array<{ groesse:string, anzahl:number }> }) =>
            {
                return buchung.von === this._von.id && buchung.nach === this._nach.id;
            });

        if(buchungsKombination)
        {
            let kombination:{ groesse:string, anzahl:number } =
                buchungsKombination.positions.find((position:{ groesse:string, anzahl:number }) =>
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
                        anzahl: this._anzahl
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

    private deleteRow():void
    {
        let newRowList:Array<TerraSimpleTableRowInterface<any>>;

        newRowList = this._rowList.filter((row:TerraSimpleTableRowInterface<any>) =>
        {
            return row.selected === false;
        });

        this._rowList = newRowList;

        //this._rowList.forEach((row:TerraSimpleTableRowInterface<any>) =>
        //{
        //    if(row.selected)
        //    {
        //        let index:number = this._rowList.indexOf(row);
        //
        //        if(index > -1)
        //        {
        //            this._rowList.splice(index, 1);
        //        }
        //    }
        //})
    }

    private selectRow(event:any)
    {
        event.selected = true;
    }
}
