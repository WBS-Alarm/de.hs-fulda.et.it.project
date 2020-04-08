import {
    Component,
    OnInit
} from "@angular/core";
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
import { map } from 'rxjs/operators';

@Component({
    selector: 'system-user',
    templateUrl: './system-user.component.html',
    styleUrls:   ['./system-user.component.scss']
})
export class SystemUserComponent implements OnInit
{
    public userId:number;

    public routeData$:Observable<Data>;

    public mulitValues:any;


    public alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    constructor(public route:ActivatedRoute,
                public usersService:UsersService,
                public systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>,
                public systemGlobalSettings:SystemGlobalSettingsService)
    {

    }

    public ngOnInit():void
    {
        this.userId = +this.systemTreeConfig.currentSelectedNode.id.toString().replace('benutzer ', '');

        this.routeData$ = this.route.data;
    }


    public save(user:UserDataInterface):void
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

    public delete(user:UserDataInterface)
    {
        this.usersService.deleteUser(user.id).subscribe(
            (result:any) =>
        {
            this.alert.addAlert(
                {
                    msg:              'Der Benutzer wurde gelöscht',
                    type:             'success',
                    dismissOnTimeout: null,
                    identifier:       'userDeleted'
                }
            );

            this.systemTreeConfig.removeNodeById(this.systemTreeConfig.currentSelectedNode.id)
        },
            (error:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Der Benutzer konnte nicht gelöscht werden',
                        type:             'danger',
                        dismissOnTimeout: null,
                        identifier:       'userNotDeleted'
                    }
                )
            })
    }
}