import {
    Component,
    OnInit
} from "../../../../../../node_modules/@angular/core";
import {
    TerraAlertComponent,
    TerraNodeTreeConfig
} from "@plentymarkets/terra-components";
import { UserDataInterface } from "../../../../core/service/rest/users/user-data.interface";
import { UsersService } from "../../../../core/service/rest/users/users.service";
import { SystemGlobalSettingsService } from "../../system-global-settings.service";
import { ExampleTreeData } from "../../system.component";
import {
    ActivatedRoute,
    Data
} from '@angular/router';
import { Observable } from "rxjs";

@Component({
    selector: 'system-user',
    template: require('./system-user.component.html'),
    styles:   [require('./system-user.component.scss')]
})
export class SystemUserComponent implements OnInit
{
    private userId:number;

    protected routeData$:Observable<Data>;

    private alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    constructor(private route:ActivatedRoute,
                private usersService:UsersService,
                private systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>,
                private systemGlobalSettings:SystemGlobalSettingsService)
    {

    }

    public ngOnInit():void
    {
        this.userId = +this.systemTreeConfig.currentSelectedNode.id.toString().replace('benutzer ', '');

        this.routeData$ = this.route.data;
    }


    protected save(user:UserDataInterface):void
    {
        this.usersService.editUser(+user.id,
            user).subscribe(
            (result:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Änderungen gespeichert!',
                        type:             'success',
                        dismissOnTimeout: null,
                        identifier:       'userEdited'
                    }
                )
            },
            (error:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Änderungen wurden nicht gespeichert!',
                        type:             'danger',
                        dismissOnTimeout: null,
                        identifier:       'userNotEdited'
                    }
                )
            }
        )
    }
}