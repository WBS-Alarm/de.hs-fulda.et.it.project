import {
    Component,
    Input,
    OnInit,
    Output
} from '@angular/core';
import { AuthoritiesService } from '../../../../core/service/rest/authorities/authorities.service';
import { TerraMultiCheckBoxValueInterface } from '@plentymarkets/terra-components';

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

    protected values:Array<TerraMultiCheckBoxValueInterface> = [];

    constructor(private authorityService:AuthoritiesService)
    {

    }

    public ngOnInit()
    {
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

        // TODO implement real handling after Achims fix

        return false;

    }
}