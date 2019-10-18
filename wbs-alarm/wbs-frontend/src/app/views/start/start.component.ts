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
import { TerraAlertComponent } from '@plentymarkets/terra-components';
import { GlobalRegistryService } from '../../core/global-registry/global-registry.service';
import { TransaktionService } from '../../core/service/rest/transaktions/transaktion.service';
import { Observable } from 'rxjs';

@Component({
    selector: 'start',
    template: require('./start.component.html'),
    styles:   [require('./start.component.scss')],
})
export class StartComponent implements OnInit
{
    private alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    @Language()
    public lang:string;

    @Input()
    public myTitle:string;

    @Output()
    private userIsLoggedIn:EventEmitter<boolean> = new EventEmitter();

    public routeData$:Observable<Data>;

    public _buchungen:Array<any> = [];

    constructor(private loginService:LoginService,
                private router:Router,
                private route:ActivatedRoute,
                private globalRegistry:GlobalRegistryService,
                private transaktionsService:TransaktionService)
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
                console.log(result);
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

        this.alert.addAlert({
            msg:              'Sie werden ausgeloggt',
            type:             'success',
            dismissOnTimeout: null,
            identifier:       'logout'
        });

        let body:HTMLElement = document.getElementById('body');
        let html:HTMLElement = document.getElementById('html');

        body.classList.remove('isLoggedIn');
        html.classList.remove('isLoggedIn');

        this.router.navigate(['login']);
    }
}
