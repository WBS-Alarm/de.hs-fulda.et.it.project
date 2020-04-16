import {
    Component,
    EventEmitter,
    Input,
    OnInit,
    Output
} from '@angular/core';
import { Language } from 'angular-l10n';
import { LoginService } from '../../core/service/rest/login/login.service';
import {
    ActivatedRoute,
    Data,
    Router
} from '@angular/router';
import {AlertService, TerraAlertComponent} from '@plentymarkets/terra-components';
import { GlobalRegistryService } from '../../core/global-registry/global-registry.service';
import { TransaktionService } from '../../core/service/rest/transaktions/transaktion.service';
import { Observable } from 'rxjs';

@Component({
    selector: 'start',
    templateUrl: './start.component.html',
    styleUrls:   ['./start.component.scss'],
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

    public _buchungen:Array<{benutzer:string, von:string, nach:string, date:Date}> = [];

    constructor(public loginService:LoginService,
                public router:Router,
                public alert:AlertService,
                public route:ActivatedRoute,
                public globalRegistry:GlobalRegistryService,
                public transaktionsService:TransaktionService)
    {
    }

    public ngOnInit():void
    {
        this.routeData$ = this.route.data;

        this.route.data.subscribe((data:any) =>
        {
            let traegerId = data.user._embedded.traeger[0].id

            this.transaktionsService.getTransaktionenForTraeger(traegerId).subscribe((result:any) =>
            {
                result._embedded.elemente.forEach((element:any) =>
                {
                    let buchung:{benutzer:string, von:string, nach:string, date:Date} =
                        {
                            benutzer: element._embedded.benutzer[0].username,
                            date: element.datum,
                            nach: element._embedded.nach[0].name,
                            von: element._embedded.von[0].name,
                    };

                    this._buchungen.push(buchung);
                })
            })

        })
    }

    public logout():void
    {
        let today:Date = new Date(Date.now());

        today.setTime(today.getTime() - 1);

        let expires:string = 'expires=' + today.toUTCString();

        document.cookie = 'loginToken=' +';' + expires;

        this.globalRegistry.setisLoggedIn(false);
        this.globalRegistry.isLoginActive = false;

        this.alert.success('Sie werden ausgeloggt!');

        let body:HTMLElement = document.getElementById('body');
        let html:HTMLElement = document.getElementById('html');

        body.classList.remove('isLoggedIn');
        html.classList.remove('isLoggedIn');

        this.router.navigate(['login']);
    }
}
