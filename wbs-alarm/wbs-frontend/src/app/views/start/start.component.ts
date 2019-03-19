import {
    Component,
    Input,
    OnInit
} from '@angular/core';
import { Language } from 'angular-l10n';
import { LoginService } from '../../core/service/rest/login/login.service';
import { Router } from '@angular/router';
import { TerraAlertComponent } from '@plentymarkets/terra-components';
import { GlobalRegistryService } from '../../core/global-registry/global-registry.service';

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

    constructor(private loginService:LoginService,
                private router:Router,
                private globalRegistry:GlobalRegistryService)
    {
    }

    public ngOnInit():void
    {
    }

    public logout():void
    {
        this.loginService.logout().subscribe(
            (result) =>
            {
                this.globalRegistry.setisLoggedIn(false);
                localStorage.removeItem('accessToken');

                this.alert.addAlert({
                    msg:              'Sie werden ausgeloggt',
                    type:             'success',
                    dismissOnTimeout: 0,
                    identifier:       'logout'
                });

                this.router.navigate(['login']);
            },
            (error:Error) =>
            {
                this.alert.addAlert({
                    msg:              'Beim ausloggen ist ein Fehler aufgetreten',
                    type:             'danger',
                    dismissOnTimeout: 0,
                    identifier:       'logoutError'
                });
            }
        )
    }
}
