import {
    Component,
    OnInit
} from '@angular/core';
import { GlobalRegistryService } from '../../core/global-registry/global-registry.service';
import {
    ActivatedRoute,
    Data
} from '@angular/router';
import { ReportService } from './service/report.service';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';


export interface BerichtRow
{
    traeger:any;
    kategorie:any;
    groesse:any;
    zielort:any;
    anzahl:any;
}

export interface AktuellUeberKategorienBerichtRow
{
    traeger:string;
    anzahl:number;
    kategorie:string;
}

export interface AktuellUeberZielorteRow
{
    traeger:string;
    anzahl:number;
    kategorie:string;
    zielort:string;
}

@Component({
    // tslint:disable-next-line:component-selector
    selector:    'bericht-view',
    templateUrl: './bericht-view.component.html',
    styleUrls:   ['./bericht-view.component.scss'],
})
export class BerichtViewComponent implements OnInit
{
    public traegerId:number;
    public routeData$:Observable<Data>;
    public trager:any;
    public traegers:Array<any>;

    public tableData:Array<BerichtRow> = [];
    public tableDataKategorien:Array<AktuellUeberKategorienBerichtRow> = [];
    public tableDataBestaende:Array<AktuellUeberZielorteRow> = [];

    public displayedColumns:Array<string> = ['zielort',
                                             'kategorie',
                                             'größe',
                                             'anzahl'];
    public dataSource:MatTableDataSource<BerichtRow> = new MatTableDataSource<BerichtRow>(this.tableData);

    public displayedColumnsKategorien:Array<string> = ['kategorie',
                                                       'anzahl'];
    public dataSourceUeberKategorien:MatTableDataSource<AktuellUeberKategorienBerichtRow> = new MatTableDataSource<AktuellUeberKategorienBerichtRow>(
        this.tableDataKategorien);

    public displayedColumnsBestaende:Array<string> = ['zielort',
                                                      'kategorie',
                                                      'anzahl'];
    public dataSourceUeberBestaende:MatTableDataSource<AktuellUeberZielorteRow> =
        new MatTableDataSource<AktuellUeberZielorteRow>(this.tableDataBestaende);

    constructor(private globalRegistryService:GlobalRegistryService,
                private route:ActivatedRoute,
                private berichtService:ReportService)
    {
    }

    public ngOnInit():void
    {
        this.routeData$ = this.route.data;

        this.routeData$.subscribe((data:any):any =>
        {
            this.traegerId = data.user._embedded.traeger[0].id;
            this.traegers = data.user._embedded.traeger;

            this.trager = this.traegers[0];
        });

    }

    public createReport():void
    {
        this.tableData = [];
        this.dataSource = new MatTableDataSource<BerichtRow>(this.tableData);

        this.berichtService.listBestaendeZuTraeger(this.trager.id).subscribe((bericht:any):any =>
        {
            bericht._embedded.elements.forEach((element:any):any =>
            {
                this.tableData.push({
                    traeger:   element.traeger,
                    kategorie: element.kategorie,
                    groesse:   element.groesse,
                    zielort:   element.zielort,
                    anzahl:    element.anzahl,
                });
            });

            this.dataSource._updateChangeSubscription();
        });
    }

    public createReportZielort():void
    {
        this.tableDataBestaende = [];
        this.dataSourceUeberBestaende = new MatTableDataSource<AktuellUeberZielorteRow>(this.tableDataBestaende);

        this.berichtService.listBestaendeUeberZielort(this.trager.id).subscribe((bericht:any):any =>
        {
            bericht._embedded.elements.forEach((element:any):any =>
            {
                this.tableDataBestaende.push({
                    traeger:   element.traeger,
                    kategorie: element.kategorie,
                    zielort:   element.zielort,
                    anzahl:    element.anzahl,
                });
            });

            this.dataSourceUeberBestaende._updateChangeSubscription();
        });
    }

    public createReportKategorien():void
    {
        this.tableDataKategorien = [];
        this.dataSourceUeberKategorien = new MatTableDataSource<AktuellUeberKategorienBerichtRow>(this.tableDataKategorien);

        this.berichtService.listBestaendeUeberKategorien(this.trager.id).subscribe((bericht:any):any =>
        {
            bericht._embedded.elements.forEach((element:any):any =>
            {
                this.tableDataKategorien.push({
                    traeger:   element.traeger,
                    kategorie: element.kategorie,
                    anzahl:    element.anzahl,
                });
            });

            this.dataSourceUeberKategorien._updateChangeSubscription();
        });
    }
}
