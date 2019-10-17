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

@Component({
    selector: 'system-authorities',
    template: require('./system-authorities.compontent.html'),
    styles:   [require('./system-authorities.compontent.scss')]
})
export class SystemAuthoritiesCompontent implements OnInit
{
    @Input()
    public userId:number;

    @Output()
    public authorityId:string;

    @Input()
    public user:any

    private user1:any;

    protected values:Array<TerraMultiCheckBoxValueInterface> = [];

    constructor(private authorityService:AuthoritiesService,
                private userService:UsersService)
    {

    }

    public ngOnInit()
    {
        this.userService.getOneUser(this.userId).subscribe((result:any) =>
        {
            this.user1 = result
        });


        this.authorityService.getAuthorities().subscribe((result:any) =>
            {
                result._embedded.authorities.forEach((authority:any) =>
                {
                    this.values.push(
                        {
                            value:    authority.id,
                            caption:  authority.bezeichnung,
                            selected: this.isSelected(authority)
                        }
                    )
                });
            },
            (error:any) =>
            {
                console.log(error)
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

    }
}