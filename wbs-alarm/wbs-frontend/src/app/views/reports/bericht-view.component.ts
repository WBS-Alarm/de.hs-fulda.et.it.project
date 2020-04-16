import {Component, OnInit} from "@angular/core";
import {GlobalRegistryService} from "../../core/global-registry/global-registry.service";
import {ActivatedRoute, Data, Route} from "@angular/router";
import {ReportService} from "./service/report.service";
import {MatTableDataSource} from "@angular/material/table";
import {Observable} from "rxjs";

export interface BerichtRow
{
    traeger:any;
    kategorie:any;
    groesse:any;
    zielort:any;
    anzahl:any;
}

@Component({
    selector: 'bericht-view',
    templateUrl: './bericht-view.component.html',
    styleUrls:   ['./bericht-view.component.scss'],
})
export class BerichtViewComponent implements OnInit
{
    public traegerId:number;
    public routeData$:Observable<Data>;
    public trager:any;
    public traegers:Array<any>;

    private tableData:Array<BerichtRow> = [];

    public displayedColumns:Array<string> = ['traeger', 'kategorie', 'größe', 'zielort', 'anzahl'];
    public dataSource:MatTableDataSource<BerichtRow> = new MatTableDataSource<BerichtRow>(this.tableData);

    constructor(private globalRegistryService:GlobalRegistryService,
                private route:ActivatedRoute,
                private berichtService:ReportService)
    {
    }

    ngOnInit():void
    {
        this.routeData$ = this.route.data;

        this.routeData$.subscribe((data:any) =>
        {
            this.traegerId = data.user._embedded.traeger[0].id;
            this.traegers = data.user._embedded.traeger
        });

    }

    public createReport():void
    {
        this.berichtService.listBestaendeZuTraeger(this.trager.id).subscribe((bericht:any) =>
        {
            bericht._embedded.elements.forEach((element:any) =>
            {
                this.tableData.push({
                    traeger: element.traeger,
                    kategorie: element.kategorie,
                    groesse: element.groesse,
                    zielort: element.zielort,
                    anzahl: element.anzahl,
                });
            });

            this.dataSource._updateChangeSubscription();
        });
    }

}