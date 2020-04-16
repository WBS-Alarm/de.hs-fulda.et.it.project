import {Component, OnInit} from "@angular/core";
import {GlobalRegistryService} from "../../core/global-registry/global-registry.service";
import {Observable} from "rxjs";
import {ActivatedRoute, Data, Route} from "@angular/router";
import {ReportService} from "./service/report.service";

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

        });
    }

}