import {
    Component,
    Input,
    OnInit,
    ViewChild
} from '@angular/core';
import {
    ActivatedRoute,
    Data
} from '@angular/router';
import { Observable } from 'rxjs';
import {
    TerraAlertComponent,
    TerraButtonInterface,
    TerraOverlayButtonInterface,
    TerraOverlayComponent,
    TerraPortletComponent,
    TerraSelectBoxValueInterface,
    TerraSimpleTableCellInterface,
    TerraSimpleTableHeaderCellInterface,
    TerraSimpleTableRowInterface
} from '@plentymarkets/terra-components';
import { GroesseService } from '../../../../core/service/rest/groesse/groesse.service';
import { CategoryService } from '../../../../core/service/rest/categories/category.service';
import { SystemGlobalSettingsService } from '../../system-global-settings.service';
import { isNullOrUndefined } from 'util';
import { BestaendeService } from '../../../../core/service/rest/bestaende/bestaende.serice';
import { AlertType } from '@plentymarkets/terra-components/components/alert/alert-type.enum';

@Component({
    selector: 'system-bestaende',
    template: require('./system-bestaende.component.html'),
    styles:   [require('./system-bestaende.component.scss')]
})
export class SystemBestaendeComponent implements OnInit
{
    public routeData$:Observable<Data>;

    public _headerList:Array<TerraSimpleTableHeaderCellInterface> = [];
    public _rowList:Array<TerraSimpleTableRowInterface<any>> = [];

    public _kategorien:Array<TerraSelectBoxValueInterface> = [{caption: 'Bitte wählen', value: null}];
    public _groessen:Array<TerraSelectBoxValueInterface> = [{caption: 'Bitte wählen', value: null}];

    public _kategorie:any;
    public _groesse:any;

    public _anzahl:number;

    public _bestandAendernAnzahl:number;
    public _bestandAendernBestand:number;

    public primaryButtonInterface:TerraOverlayButtonInterface;

    @Input()
    private zielortId:number;

    private alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    @ViewChild('bearbeitenOverlay')
    private bearbeitenOverlay:TerraOverlayComponent;


    constructor(private route:ActivatedRoute,
                private groessenService:GroesseService,
                private categoryService:CategoryService,
                private bestandService:BestaendeService,
                private systemGlobalSettingsService:SystemGlobalSettingsService)
    {

    }

    public ngOnInit():void
    {
        let tragerId:number = this.systemGlobalSettingsService.getTraegerId()

        this.routeData$ = this.route.data;

        this.route.data.subscribe((data:any) =>
        {
            this.erstelleTabellenStruktur();

            data.bestaende._embedded.elemente.forEach((element:any) =>
            {
                this.addRowToTable(
                    {
                        kategorieId:element._embedded.kategorie[0].id,
                        kategorie: element._embedded.kategorie[0].name,
                        groesseId: element._embedded.groesse[0].id,
                        groesse: element._embedded.groesse[0].name,
                        anzahl: element.anzahl,
                        bestandId: element.id
                    });
            })


        });


        this.categoryService.getCategories(tragerId).subscribe((result:any) =>
        {
            result._embedded.elemente.forEach((kategorie) =>
            {
                this._kategorien.push(
                    {
                        caption: kategorie.name,
                        value: kategorie
                    }
                )
            });
        });

        this.primaryButtonInterface = {
            icon:          'icon-save',
            caption:       'Änderungen speichern',
            isDisabled:    false,
            clickFunction: ():void => {
                this.saveChangesToBestand(this._bestandAendernAnzahl);

                this._rowList.find((row:TerraSimpleTableRowInterface<any>) =>
                {
                    return row.selected
                }).cellList[4].caption = this._bestandAendernAnzahl;

                this.bearbeitenOverlay.hideOverlay();
            }

        }
    }

    public addRowToTable(bestand:{kategorieId:number, kategorie:string, groesseId:number, groesse:string, anzahl:number, bestandId:number}):void
    {
        let kategorieIdCell:TerraSimpleTableCellInterface = {caption: bestand.kategorieId};

        let kategorieCell:TerraSimpleTableCellInterface = {caption: bestand.kategorie};

        let groesseIdCell:TerraSimpleTableCellInterface = {caption: bestand.groesseId};

        let groesseCell:TerraSimpleTableCellInterface = {caption: bestand.groesse};

        let anzahlCell:TerraSimpleTableCellInterface = {caption: bestand.anzahl};

        let bestandIdCell:TerraSimpleTableCellInterface = {caption: bestand.bestandId || ''}

        let row:TerraSimpleTableRowInterface<any>;

        let cellList:Array<TerraSimpleTableCellInterface> = [];

        cellList.push(kategorieIdCell, kategorieCell, groesseIdCell, groesseCell, anzahlCell, bestandIdCell);


        row = {
            cellList: cellList,
            disabled: false,
            selected: false
        };

        this._rowList.push(row);

    }

    public addBestand():void
    {
        this.bestandService.erfasseBestaendeFuerZielort(this._groesse.id, this._anzahl, this.zielortId).subscribe((result:any) =>
        {
            this.addRowToTable({
                kategorieId: this._kategorie.id,
                kategorie: this._kategorie.name,
                groesseId: this._groesse.id,
                groesse: this._groesse.name,
                anzahl: this._anzahl,
                bestandId: result.headers.get('Location').split('/wbs/bestand/')[1]
            })

            this.alert.addAlert(
                {
                    msg: 'Der Bestand wurde erfolgreich erfasst',
                    type: AlertType.success,
                    dismissOnTimeout: 0
                }
            )
        },
            (error:any) =>
            {
                this.alert.addAlert(
                    {
                        msg: 'Der Bestand konnte nicht erfasst werden: ' + error,
                        type: AlertType.error,
                        dismissOnTimeout: 0
                    }
                )
            })


    }

    public selectRow(event:any):void
    {
        this._rowList.forEach((row:any) =>
        {
            row.selected = false;
        });

        event.selected = true;
    }

    public erstelleTabellenStruktur():void
    {
        let kategorieId:TerraSimpleTableHeaderCellInterface = {
            caption: 'KATEGORIE-ID',
            width:   '100',
        };

        let kategorieCaption:TerraSimpleTableHeaderCellInterface = {
            caption: 'KATEGORIE',
            width:   '100',
        };
        let groesseId:TerraSimpleTableHeaderCellInterface = {
            caption: 'GROESSE-ID',
            width:   '100',
        };

        let groesseCaption:TerraSimpleTableHeaderCellInterface = {
            caption: 'GROESSE',
            width:   '100',
        };
        let anzahl:TerraSimpleTableHeaderCellInterface = {
            caption: 'ANZAHL',
            width:   '100',
        };
        let bestand:TerraSimpleTableHeaderCellInterface = {
            caption: 'BESTAND-ID',
            width:   '100',
        };


        this._headerList.push(kategorieId, kategorieCaption, groesseId, groesseCaption, anzahl, bestand);
    }

    public ladeGroessen():void
    {
        if(!isNullOrUndefined(this._kategorie))
        {
            this._groessen = [];

            this.groessenService.getGroessenForKategorie(this._kategorie.id).subscribe((result:any) =>
            {
                result._embedded.elemente.forEach((groesse:any) =>
                {
                    this._groessen.push(
                        {
                            caption: groesse.name,
                            value: groesse
                        }
                    )
                })
            })
        }
    }

    public aendereBestand():void
    {
        let datensatz:TerraSimpleTableRowInterface<any> = this._rowList.find((row:TerraSimpleTableRowInterface<any>) =>
        {
            return row.selected;
        });

        this._bestandAendernAnzahl = +datensatz.cellList[4].caption;

        this._bestandAendernBestand = +datensatz.cellList[5].caption;

        this.bearbeitenOverlay.showOverlay();
    }

    public saveChangesToBestand(neuerBestand:number):void
    {
        this.bestandService.aendereBestand(neuerBestand, this._bestandAendernBestand).subscribe((result:any) =>
        {
            this.alert.addAlert(
                {
                    msg: 'Der Bestand wurde erfolgreich geändert',
                    type: AlertType.success,
                    dismissOnTimeout: 0
                }
            )
        },
            (error:any) =>
            {
                this.alert.addAlert(
                    {
                        msg: 'Der Bestand wurde nicht erfolgreich geändert: ' + error,
                        type: AlertType.error,
                        dismissOnTimeout: 0
                    }
                )
            })
    }
}