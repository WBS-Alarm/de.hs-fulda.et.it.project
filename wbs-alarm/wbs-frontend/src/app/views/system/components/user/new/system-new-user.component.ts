import {Component} from "../../../../../../../node_modules/@angular/core";
import {UserDataInterface} from "../../../../../core/service/rest/users/user-data.interface";
import {UsersService} from "../../../../../core/service/rest/users/users.service";
import {SystemGlobalSettingsService} from "../../../system-global-settings.service";
import {
    TerraAlertComponent,
    TerraNodeTreeConfig
} from "@plentymarkets/terra-components";
import { ExampleTreeData } from '../../../system.component';


@Component({
    selector: 'system-new-user',
    template: require('./system-new-user.component.html'),
    styles:   [require('./system-new-user.component.scss')]
})
export class SystemNewUserComponent
{
    private newUser:UserDataInterface =
        {
            username: '',
            password: ''
        };

    private alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    constructor(private usersService:UsersService,
                private systemGlobalSettings:SystemGlobalSettingsService,
                private systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>)
    {

    }

    protected save():void
    {
        let traegerId:number = this.systemGlobalSettings.getTraegerId();

        this.usersService.registerUser(traegerId, this.newUser).subscribe(
            (result:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Der Benutzer wurde angelegt!',
                        type:             'success',
                        dismissOnTimeout: null,
                        identifier:       'userCreated'
                    }
                );

                //this.systemTreeConfig.addChildToNodeById(this.systemTreeConfig.currentSelectedNode.id,
                //    {
                //        id:        result.id,
                //        name:      result.name,
                //        isVisible: true
                //    }
                //);

                this.newUser.username = '';
                this.newUser.password = '';
            },
            (error:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Der Benutzer konnte nicht angelegt werden!',
                        type:             'danger',
                        dismissOnTimeout: null,
                        identifier:       'userNotCreated'
                    }
                )
            })
    }
}