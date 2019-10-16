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

    private rowCounter:number = 0;

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
        console.log('von:' + this._von);
        console.log('nach:' + this._nach);
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
                this._zielorte.push(
                    {
                        value:   zielort,
                        caption: zielort.name
                    }
                )
            })
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
        let buttonCell:TerraSimpleTableHeaderCellInterface = {
            caption: 'Button',
            width:   '100',
        };

        this._headerList.push(vonCell, nachCell, kategorieCell, groesseCell, anzahlCell, buttonCell);
    }

    private addRow():void
    {
        let vonCell:TerraSimpleTableCellInterface = {
            caption: this._von.name
        };

        let nachCell:TerraSimpleTableCellInterface = {
            caption: this._nach.name
        };
        let kategorieCell:TerraSimpleTableCellInterface = {
            caption: this._kategorie.name
        };
        let groesseCell:TerraSimpleTableCellInterface = {
            caption: this._goresse.name
        };
        let anzahlCell:TerraSimpleTableCellInterface = {
            caption: this._anzahl
        };

        let buttonList:Array<TerraButtonInterface> = [];

        buttonList.push({
            caption:       'Löschen',
            clickFunction: ():any =>
                           {
                               this.deleteRow();
                           }
        });

        let buttonCell:TerraSimpleTableCellInterface = {
            buttonList: buttonList
        };

        let row:TerraSimpleTableRowInterface<any>;

        let cellList:Array<TerraSimpleTableCellInterface> = [];

        cellList.push(vonCell, nachCell, kategorieCell, groesseCell, anzahlCell)

        cellList.push(buttonCell);

        row = {
                cellList: cellList,
                disabled: false,
                selected: false
            };

        this._rowList.push(row);

        this.rowCounter++;
    }

    private deleteRow():void
    {
        this._rowList.forEach((row:TerraSimpleTableRowInterface<any>) =>
        {
            if(row.selected)
            {
                let index:number = this._rowList.indexOf(row);

                if(index > -1)
                {
                    this._rowList.splice(index, 1);
                }
            }
        })
    }

    private selectRow(event:any)
    {
        event.selected = true;
    }
}
