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
    AlertService,
    TerraOverlayButtonInterface,
    TerraSelectBoxValueInterface
} from '@plentymarkets/terra-components';
import { GroesseService } from '../../../../core/service/rest/groesse/groesse.service';
import { CategoryService } from '../../../../core/service/rest/categories/category.service';
import { SystemGlobalSettingsService } from '../../system-global-settings.service';
import { isNullOrUndefined } from 'util';
import { BestaendeService } from '../../../../core/service/rest/bestaende/bestaende.serice';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import {
    MatDialog,
    MatDialogRef
} from '@angular/material/dialog';
import { BestandDialogComponent } from './dialog/bestand-dialog.component';

export interface BestandRow
{
    kategorie:any;
    groesse:any;
    anzahl:number;
    bestand:any;
}

@Component({
    // tslint:disable-next-line:component-selector
    selector: 'system-bestaende',
    templateUrl: './system-bestaende.component.html',
    styleUrls: ['./system-bestaende.component.scss']
})
export class SystemBestaendeComponent implements OnInit
{
    public routeData$:Observable<Data>;

    public _kategorien:Array<TerraSelectBoxValueInterface> = [{
        caption: 'Bitte wählen',
        value:   null
    }];
    public _groessen:Array<TerraSelectBoxValueInterface> = [{
        caption: 'Bitte wählen',
        value:   null
    }];

    public _kategorie:any;
    public _groesse:any;

    public _anzahl:number;

    public _bestandAendernAnzahl:number;
    public _bestandAendernBestand:number;

    public primaryButtonInterface:TerraOverlayButtonInterface;

    @Input()
    public zielortId:number;

    @Input()
    public gesperrt:boolean = false;

    @ViewChild('bearbeitenOverlay')
    public bearbeitenOverlay:any;

    public tableData:Array<BestandRow> = [];

    public displayedColumns:Array<string> = ['select',
                                             'kategorie',
                                             'größe',
                                             'anzahl'];
    public dataSource:MatTableDataSource<BestandRow>;
    public selection:SelectionModel<BestandRow> = new SelectionModel<BestandRow>(false, []);

    public disabled:boolean = false;

    constructor(public route:ActivatedRoute,
                public groessenService:GroesseService,
                public categoryService:CategoryService,
                public dialog:MatDialog,
                public alert:AlertService,
                public bestandService:BestaendeService,
                public systemGlobalSettingsService:SystemGlobalSettingsService)
    {
    }

    public ngOnInit():void
    {
        let tragerId:number = this.systemGlobalSettingsService.getTraegerId();

        this.routeData$ = this.route.data;

        this.dataSource = new MatTableDataSource<BestandRow>(this.tableData);

        this.route.data.subscribe((data:any):any =>
        {
            this.tableData = [];
            this.dataSource = new MatTableDataSource<BestandRow>(this.tableData);
            data.bestaende._embedded.elemente.forEach((element:any):any =>
            {
                this.addRowToTable(
                    {
                        kategorie: element._embedded.kategorie[0],
                        groesse:   element._embedded.groesse[0],
                        anzahl:    element.anzahl,
                        bestand:   element.id
                    });
            });
        });


        this.categoryService.getCategories(tragerId).subscribe((result:any):any =>
        {
            result._embedded.elemente.forEach((kategorie:any):any =>
            {
                this._kategorien.push(
                    {
                        caption: kategorie.name,
                        value:   kategorie
                    }
                );
            });
        });
    }

    public addRowToTable(bestand:BestandRow):void
    {
        this.tableData.push(
            {
                kategorie: bestand.kategorie,
                groesse:   bestand.groesse,
                anzahl:    bestand.anzahl,
                bestand:   bestand.bestand
            }
        );

        this.dataSource._updateChangeSubscription();
    }

    public addBestand():void
    {
        this.bestandService.erfasseBestaendeFuerZielort(this._groesse.id, this._anzahl, this.zielortId).subscribe((result:any):any =>
            {
                this.addRowToTable({
                    kategorie: this._kategorie,
                    groesse:   this._groesse,
                    anzahl:    this._anzahl,
                    bestand:   result.headers.get('Location').split('/wbs/bestand/')[1]
                });

                this.alert.success('Der Bestand wurde erfolgreich erfasst');
            },
            (error:any):any =>
            {
                this.alert.error('Der Bestand konnte nicht erfasst werden: ' + error.error.message);
            });


    }

    public ladeGroessen():void
    {
        if(!isNullOrUndefined(this._kategorie))
        {
            this._groessen = [{
                caption: 'Bitte wählen',
                value:   null
            }];

            this._groesse = {
                caption: 'Bitte wählen',
                value:   null
            };

            this.groessenService.getGroessenForKategorie(this._kategorie.id).subscribe((result:any):any =>
            {
                this.disabled = result._embedded.elemente.length === 0;

                result._embedded.elemente.forEach((groesse:any):any =>
                {
                    this._groessen.push(
                        {
                            caption: groesse.name,
                            value:   groesse
                        }
                    );
                });
            });

        }
    }

    public aendereBestand():void
    {
        if(this.selection.selected.length > 0)
        {
            const editDialog:MatDialogRef<BestandDialogComponent> = this.dialog.open(BestandDialogComponent, {autoFocus: true});
            this._bestandAendernAnzahl = this.selection.selected[0].anzahl;

            this._bestandAendernBestand = this.selection.selected[0].bestand;

            editDialog.afterClosed().subscribe((neuerBestand:number):any =>
            {
                if(neuerBestand > 0)
                {
                    this.saveChangesToBestand(neuerBestand);

                    this.selection.selected[0].anzahl = neuerBestand;
                    this.dataSource._updateChangeSubscription();
                }
            });
        }
    }

    public saveChangesToBestand(neuerBestand:number):void
    {
        this.bestandService.aendereBestand(neuerBestand, this._bestandAendernBestand).subscribe((result:any):any =>
            {
                this.alert.success('Der Bestand wurde erfolgreich geändert');
            },
            (error:any):any =>
            {
                this.alert.error('Der Bestand wurde nicht erfolgreich geändert: ' + error.error.message);

            });
    }

    public loescheBestand():void
    {
        let loeschBestand:BestandRow = this.selection.selected[0];

        this.bestandService.loescheBestand(loeschBestand.bestand).subscribe(
            (result:any):any =>
            {
                this.alert.success('Der Bestand wurde gelöscht');

                let idx:number = this.tableData.indexOf(loeschBestand);

                this.tableData.splice(idx, 1);
                this.dataSource._updateChangeSubscription();
            },
            (error:any):any =>
            {
                this.alert.error('Der Bestand wurde nicht gelöscht. ' + error.error.message);

            });
    }
}
