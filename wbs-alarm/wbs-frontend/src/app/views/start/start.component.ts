import {
    Component,
    EventEmitter,
    Input,
    OnInit,
    Output,
    ViewChild
} from '@angular/core';
import { Language } from 'angular-l10n';
import {
    ActivatedRoute,
    Data,
    Router
} from '@angular/router';
import { AlertService } from '@plentymarkets/terra-components';
import { GlobalRegistryService } from '../../core/global-registry/global-registry.service';
import { TransaktionService } from '../../core/service/rest/transaktions/transaktion.service';
import { Observable } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { HttpClient } from '@angular/common/http';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import {
    animate,
    state,
    style,
    transition,
    trigger
} from '@angular/animations';

export interface BuchungsuebersichtRow
{
    Name:any;
    Von:any;
    Nach:any;
    Datum:any;
    Anzahl:any;
    positionen:any;
}
/* tslint:disable */
@Component({
    // tslint:disable-next-line:component-selector
    selector:    'start',
    templateUrl: './start.component.html',
    styleUrls:   ['./start.component.scss'],
    animations:  [
        trigger('detailExpand', [
            state('collapsed',
                style({
                    height:    '0px',
                    minHeight: '0'
                })),
            state('expanded', style({height: '*'})),
            transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
        ]),
    ]
})
export class StartComponent implements OnInit
{
    @Language()
    public lang:string;

    @Input()
    public myTitle:string;

    @Output()
    public userIsLoggedIn:EventEmitter<boolean> = new EventEmitter();

    public routeData$:Observable<Data>;

    public tableData:Array<BuchungsuebersichtRow> = [];

    public displayedColumns:Array<string> = ['Von',
                                             'Nach',
                                             'Datum',
                                             'Name',
                                             'Anzahl'];
    public dataSource:MatTableDataSource<BuchungsuebersichtRow> = new MatTableDataSource<BuchungsuebersichtRow>(this.tableData);

    @ViewChild(MatPaginator)
    public paginator:MatPaginator;

    @ViewChild(MatSort)
    public sort:MatSort;

    public expandedElement:any | null;

    constructor(public router:Router,
                public alert:AlertService,
                public route:ActivatedRoute,
                public globalRegistry:GlobalRegistryService,
                public transaktionsService:TransaktionService,
                private http:HttpClient)
    {
    }

    public ngOnInit():void
    {
        this.routeData$ = this.route.data;

        this.route.data.subscribe((data:any) =>
        {
            let traegerId:any = data.user._embedded.traeger[0].id;

            this.transaktionsService.getTransaktionenForTraeger(traegerId).subscribe((result:any) =>
            {
                result._embedded.elemente.forEach((element:any) =>
                {
                    let anzahl:number = 0;

                    let positionenArray:Array<{ kategorie:string; groesse:string; anzahl:number }> = [];

                    element._embedded.positionen.forEach((position:any) =>
                    {
                        let positionForList:{ kategorie:string; groesse:string; anzahl:number } =
                            {
                                kategorie: '',
                                groesse:   '',
                                anzahl:    0
                            };

                        anzahl += position.anzahl;

                        positionForList.anzahl = position.anzahl;

                        positionenArray = this.getGroessen(position, positionForList);
                    });

                    this.tableData.push({
                        Datum:      new Date(element.datum).toLocaleString(),
                        Nach:       element._embedded.nach[0].name,
                        Name:       element._embedded.benutzer[0].username,
                        Von:        element._embedded.von[0].name,
                        Anzahl:     anzahl,
                        positionen: positionenArray
                    });

                    this.dataSource.paginator = this.paginator;
                    this.dataSource._updateChangeSubscription();
                });
            });

        });
    }

    private getGroessen(position:any, positionForList:any):Array<any>
    {
        let positionenArray:Array<any> = [];

        position._embedded.groesse.forEach((groesse:any) =>
        {
            positionForList.groesse = groesse.name;

            groesse._embedded.kategorie.forEach((kategorie:any) =>
            {
                positionForList.kategorie = kategorie.name;

                positionenArray.push(positionForList);
            });
        });

        return positionenArray;
    }
}



/* tslint:enable */
