import {
    Component,
    OnInit
} from '../../../../node_modules/@angular/core';
import { TerraNodeTreeConfig } from "@plentymarkets/terra-components";
import { TranslationService } from "angular-l10n";
import { CarrierService } from "../../core/service/rest/carrier/carrier.service";
import { Observable } from "rxjs/observable";
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';

export interface resultData
{
    _embedded:embeddedData;
    _links:Object;
}

export interface embeddedData
{
    elemente:Array<traegerData>;
}

export interface traegerData
{
    id:number;
    name:string;
    _links:Object;
}

export interface ExampleTreeData
{
    id:number;
}

@Component({
    selector: 'system',
    template: require('./system.component.html'),
    styles:   [require('./system.component.scss')]
})
export class SystemComponent implements OnInit
{
    constructor(private nodeTreeConfig:TerraNodeTreeConfig<ExampleTreeData>,
                private translation:TranslationService,
                private router:Router,
                private carrierService:CarrierService)
    {
    }

    public ngOnInit():void
    {
        this.createCompleteTree();
    }

    protected createCompleteTree():void
    {
        this.nodeTreeConfig.list = [
            {
                id:        11,
                name:      this.translation.translate('system.user.user'),
                isVisible: true,
                children:  [
                    {
                        id:        112,
                        name:      'Child1',
                        isVisible: true,
                        children:  [{
                            id:        113,
                            name:      'Subchild1',
                            isVisible: true,
                            onClick:   ():void =>
                                       {
                                           alert('Hello i am a click function');
                                       }
                        }]
                    }
                ]
            },
            {
                id:         21,
                name:       this.translation.translate('system.village.village'),
                isVisible:  true,
                children:   [],
                onClick: ():void => { this.router.navigateByUrl('plugin/system/carrier') },
                onLazyLoad: ():Observable<any> =>
                            {
                                return this.getCarriers();
                            }
            }];
    }

    private getCarriers():Observable<any>
    {
        return this.carrierService.getCarriers().pipe(
            tap((result:resultData) =>
            {
                {
                    result._embedded.elemente.forEach((element:any) =>
                    {
                        this.nodeTreeConfig.addChildToNodeById(21, {
                            id:        element.id,
                            name:      element.name,
                            isVisible: true
                        });
                    })
                }
            }));
    }
}