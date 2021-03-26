import {
    Component,
    OnInit
} from '@angular/core';
import {
    AlertService,
    TerraNodeTreeConfig
} from '@plentymarkets/terra-components';
import { UserDataInterface } from '../../../../core/service/rest/users/user-data.interface';
import { UsersService } from '../../../../core/service/rest/users/users.service';
import { SystemGlobalSettingsService } from '../../system-global-settings.service';
import { ExampleTreeData } from '../../system.component';
import {
    ActivatedRoute,
    Data,
    Router
} from '@angular/router';
import { Observable } from 'rxjs';

@Component({
    // tslint:disable-next-line:component-selector
    selector:    'system-user',
    templateUrl: './system-user.component.html',
    styleUrls:   ['./system-user.component.scss']
})
export class SystemUserComponent implements OnInit
{
    public userId:number;

    public routeData$:Observable<Data>;

    public traegerId:number;

    constructor(public route:ActivatedRoute,
                public usersService:UsersService,
                public alert:AlertService,
                public router:Router,
                public systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>,
                public systemGlobalSettings:SystemGlobalSettingsService)
    {

    }

    public ngOnInit():void
    {
        this.userId = +this.systemTreeConfig.currentSelectedNode.id.toString().replace('benutzer ', '');

        this.routeData$ = this.route.data;

        this.route.params.subscribe((params:any):any =>
        {
            this.traegerId = params.carrierId;
        });
    }


    public save(user:UserDataInterface):void
    {
        this.usersService.editUser(+user.id,
            user).subscribe(
            (result:any):any =>
            {
                this.alert.success('Änderungen gespeichert!');
            },
            (error:any):any =>
            {
                this.alert.error('Änderungen wurden nicht gespeichert! ' + error.error.message);
            }
        );
    }

    public delete(user:UserDataInterface):void
    {
        this.usersService.deleteUser(user.id).subscribe(
            (result:any):any =>
            {
                this.alert.success('Der Benutzer wurde gelöscht');

                this.systemTreeConfig.removeNodeById(this.systemTreeConfig.currentSelectedNode.id);

                this.router.navigateByUrl('plugin/system/carrier/' + this.traegerId + '/user');
            },
            (error:any):any =>
            {
                this.alert.error('Der Benutzer konnte nicht gelöscht werden. ' + error.error.message);
            });
    }
}
