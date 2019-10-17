import {
    Component,
    Input,
    OnInit,
    Output
} from '@angular/core';
import { AuthoritiesService } from '../../../../core/service/rest/authorities/authorities.service';
import { TerraMultiCheckBoxValueInterface } from '@plentymarkets/terra-components';
import { UsersService } from '../../../../core/service/rest/users/users.service';
import { isNullOrUndefined } from 'util';
import { Observable } from 'rxjs';
import {
    ActivatedRoute,
    Data,
    Route
} from '@angular/router';
import { map } from 'rxjs/operators';

@Component({
    selector: 'system-authorities',
    template: require('./system-authorities.compontent.html'),
    styles:   [require('./system-authorities.compontent.scss')]
})
export class SystemAuthoritiesCompontent implements OnInit
{
   private userId:number

    private user1:any;

    protected values:Array<TerraMultiCheckBoxValueInterface> = [];

    protected routeData$:Observable<Data>;

    constructor(private authorityService:AuthoritiesService,
                private route:ActivatedRoute,
                private userService:UsersService)
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
            })

            if(value.selected && isNullOrUndefined(userHasAuthority))
            {
                this.authorityService.grantAuthorities(this.userId, value.value).subscribe((result:any) =>
                {
                    console.log(result);
                })
            }
        })

        this.removeBerechtigungen();
    }

    private removeBerechtigungen():void
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