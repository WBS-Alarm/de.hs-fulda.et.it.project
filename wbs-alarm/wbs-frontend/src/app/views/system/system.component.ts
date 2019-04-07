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
                    result._embedded.elemente.forEach((element:any) =>
                    {
                        this.nodeTreeConfig.addChildToNodeById(21, {
                            id:        element.id,
                            name:      element.name,
                            isVisible: true,
                            onClick: ():void =>
                            {
                                // this.router.navigateByUrl('plugin/system/carrier/' + element.id)
                            },
                            onLazyLoad: ():Observable<any> =>
                            {
                                return this.getCarrierDetailForId(element.id, element.name);
                            },
                            children: [
                                {
                                    id:        element.name + 11,
                                    name:      this.translation.translate('system.user.user'),
                                    isVisible: true,
                                    children:  [],
                                    onClick: ():void =>
                                    {
                                        this.router.navigateByUrl('plugin/system/carrier/' + name + 11 + '/user');
                                    }
                                },
                                {
                                    id:        element.name + 12,
                                    name:      this.translation.translate('system.targetPlaces.targetPlaces'),
                                    isVisible: true,
                                    children:  [],
                                    onClick: ():void =>
                                    {
                                        this.router.navigateByUrl('plugin/system/carrier/' + name + 12 + '/targetplaces')
                                    }
                                },
                                {
                                    id:        element.name + 13,
                                    name:      this.translation.translate('system.clothes.clothes'),
                                    isVisible: true,
                                    children:  [],
                                    onClick: ():void =>
                                    {
                                        this.router.navigateByUrl('plugin/system/carrier/' + name + 13 + '/categories')
                                    }
                                }
                            ]
                        });
                    })
            }));
    }

    private getCarrierDetailForId(id:number, name:string):Observable<any>
    {
        return this.carrierService.getDetailsForCarrier(id).pipe(
            tap((result:any) =>
            {
                console.log(result);

                result._embedded.benutzer.forEach((benutzer:any) =>
                {
                    this.nodeTreeConfig.addChildToNodeById(name + 11,
                        {
                            id: 'benutzer ' + benutzer.id,
                            name: benutzer.username,
                            isVisible: true,
                            onClick: ():void =>
                            {
                                this.router.navigateByUrl('plugin/system/carrier/' + name + 11 + '/user/' + benutzer.id)
                            }
                        })
                });

                result._embedded.zielorte.forEach((zielort:any) =>
                {
                    this.nodeTreeConfig.addChildToNodeById(name + 12,
                        {
                            id: 'zielort ' + zielort.id,
                            name: zielort.name,
                            isVisible: true
                        })
                });

                result._embedded.kategorien.forEach((kategorie:any) =>
                {
                    this.nodeTreeConfig.addChildToNodeById(name + 13,
                        {
                            id: 'kategorie ' + kategorie.id,
                            name: kategorie.name,
                            isVisible: true
                        })
                });
            })
        )
    }
}