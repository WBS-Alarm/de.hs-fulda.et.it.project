import {AfterViewInit, Component, OnInit} from "../../../../node_modules/@angular/core";
import {TerraNodeInterface, TerraNodeTreeConfig} from "@plentymarkets/terra-components";
import {TranslationService} from "angular-l10n";
import {CarrierService} from "../../core/service/rest/carrier/carrier.service";
import {Observable} from "rxjs";

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
    styles: [require('./system.component.scss')]
})
export class SystemComponent implements OnInit, AfterViewInit
{
    private nodeCounter:number = 0;

    private carriers:any;


    constructor(private nodeTreeConfig:TerraNodeTreeConfig<ExampleTreeData>,
                private translation:TranslationService,
                private carrierService:CarrierService)
    {
    }

    public ngOnInit():void
    {
        this.createCompleteTree();


    }

    public ngAfterViewInit():void
    {
        this.getCarriers();
    }

    // protected addNode():void
    // {
    //     this.nodeTreeConfig.addNode({
    //         name:      'Test' + this.nodeCounter,
    //         id:        this.nodeCounter,
    //         isVisible: true
    //     });
    //
    //     this.nodeCounter++;
    // }
    //
    // protected addExistingNode():void
    // {
    //     this.nodeTreeConfig.addNode({
    //         name: 'Test' + this.nodeCounter,
    //         id:   0
    //     });
    // }
    //
    // protected findNodeById(id:string | number):void
    // {
    //     let node:TerraNodeInterface<ExampleTreeData> = this.nodeTreeConfig.findNodeById(id);
    //     alert(node.name);
    //
    // }

    // protected deleteNodeById(id:string | number):void
    // {
    //     this.nodeTreeConfig.removeNodeById(id);
    // }
    //
    // protected getSelectedNode():void
    // {
    //     console.log(this.nodeTreeConfig.currentSelectedNode);
    // }
    //
    // protected deleteSelectedNode():void
    // {
    //     this.nodeTreeConfig.removeNode(this.nodeTreeConfig.currentSelectedNode);
    // }
    //
    // protected updateSelectedNode():void
    // {
    //     this.nodeTreeConfig.currentSelectedNode.name = 'Terra';
    // }
    //
    // protected updateNodeById(id:string | number):void
    // {
    //     this.nodeTreeConfig.updateNodeById(id,
    //         {
    //             id:   id,
    //             name: 'Terra'
    //         });
    // }
    //
    // protected addChildToNodeById(id:string | number):void
    // {
    //     this.nodeTreeConfig.addChildToNodeById(id, {
    //         id:        133,
    //         name:      'myNewNode',
    //         isVisible: true
    //     });
    // }
    //
    // protected setSelectedNode(id:string | number):void
    // {
    //     this.nodeTreeConfig.setCurrentSelectedNodeById(id);
    // }

    protected createCompleteTree():void
    {
        this.nodeTreeConfig.list = [
            {
                id:        1,
                name:      this.translation.translate('system.user.user'),
                isVisible: true,
                children:  [
                    {
                        id:        12,
                        name:      'Child1',
                        isVisible: true,
                        children:  [{
                            id:        13,
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
                id: 2,
                name: this.translation.translate('system.village.village'),
                isVisible: true,
                children: [],
                onLazyLoad: ():void =>
                {
                    this.getCarriers()
                }
            }];
    }

    private getCarriers():Observable<any>
    {
        let obsi:Observable<any>;

       this.carrierService.getCarriers().subscribe((result:resultData) =>
       {
           result._embedded.elemente.forEach((element:any) =>
               {
                    this.nodeTreeConfig.addNode({
                        id: element.id,
                        name: element.name,
                        isVisible: true
                    })
               }
           );
       });

       return obsi;
    }
}