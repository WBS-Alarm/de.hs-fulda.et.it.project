import {
    Component,
    OnInit
} from '@angular/core';
import { AuthoritiesService } from '../../../../core/service/rest/authorities/authorities.service';
import {
    AlertService,
    TerraAlertComponent,
    TerraMultiCheckBoxValueInterface
} from '@plentymarkets/terra-components';
import { UsersService } from '../../../../core/service/rest/users/users.service';
import { isNullOrUndefined } from 'util';
import { Observable } from 'rxjs';
import {
    ActivatedRoute,
    Data
} from '@angular/router';
import { AlertType } from '@plentymarkets/terra-components/components/alert/alert-type.enum';

@Component({
    selector: 'system-authorities',
    templateUrl: './system-authorities.compontent.html',
    styleUrls:   ['./system-authorities.compontent.scss']
})
export class SystemAuthoritiesCompontent implements OnInit
{
    public userId:number;

    public user1:any;

    public values:Array<TerraMultiCheckBoxValueInterface> = [];

    public routeData$:Observable<Data>;


    constructor(public authorityService:AuthoritiesService,
                public route:ActivatedRoute,
                public alert:AlertService,
                public userService:UsersService)
    {

    }

    public ngOnInit()
    {
        this.routeData$ = this.route.data;


        this.route.data.subscribe((data:Data) =>
        {
            console.log('Das ist die Route Data');
            console.log(data);

            this.userId = data.user.id;

            this.user1 = data.userWithAuthorities;

            this.values = [];

            data.authority._embedded.authorities.forEach((authority:any) =>
            {
                this.values.push(
                    {
                        value:    authority.id,
                        caption:  authority.bezeichnung,
                        selected: this.isSelected(authority)
                    }
                )
            })
        })
    }

    public isSelected(authority:any):boolean
    {
        let authorityFound:any;

        authorityFound = this.user1._embedded.authorities.find((searchAuth:any) =>
        {
            return searchAuth.id === authority.id;
        });

        return !isNullOrUndefined(authorityFound);
    }

    public saveBerechtigungen():void
    {
        this.values.forEach((value:TerraMultiCheckBoxValueInterface) =>
        {
            let userHasAuthority:any = this.user1._embedded.authorities.find((authority:any) =>
            {
                return authority.id === value.value;
            });

            if(value.selected && isNullOrUndefined(userHasAuthority))
            {
                this.authorityService.grantAuthorities(this.userId, value.value).subscribe((result:any) =>
                    {
                        this.alert.success('Die Berechtigungen wurden erfolgreich gespeichert!')
                    },
                    (error:any) =>
                    {
                        this.alert.error('Beim Speichern der Berechtigungen ist ein Fehler aufgetreten');
                    })
            }
        });

        this.removeBerechtigungen();
    }

    public removeBerechtigungen():void
    {
        this.values.forEach((value:TerraMultiCheckBoxValueInterface) =>
        {
            let userHasAuthority:any = this.user1._embedded.authorities.find((authority:any) =>
            {
                return authority.id === value.value;
            })

            if(!value.selected && !isNullOrUndefined(userHasAuthority))
            {
                this.authorityService.removeAuthorities(this.userId, value.value).subscribe((result:any) =>
                {
                    console.log(result);
                })
            }
        })
    }
}