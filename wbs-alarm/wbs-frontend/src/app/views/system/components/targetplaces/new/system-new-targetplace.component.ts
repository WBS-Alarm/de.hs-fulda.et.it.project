import {Component} from "@angular/core";
import {AlertService, TerraAlertComponent, TerraNodeTreeConfig} from '@plentymarkets/terra-components';
import {CarrierService} from '../../../../../core/service/rest/carrier/carrier.service';
import {ActivatedRoute, Router} from '@angular/router';
import {SystemGlobalSettingsService} from '../../../system-global-settings.service';
import {ExampleTreeData} from '../../../system.component';

@Component({
    selector: 'system-new-targetplace',
    templateUrl: './system-new-targetplace.component.html',
    styleUrls:   ['./system-new-targetplace.component.scss']
})
export class SystemNewTargetplaceComponent
{
    public newTargetplaceName:string = '';

     constructor(public carrierService:CarrierService,
                public route:ActivatedRoute,
                public alert:AlertService,
                public router:Router,
                public systemGlobalSettings:SystemGlobalSettingsService,
                public systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>)
    {

    }

    public save():void
    {
        this.carrierService.createTargetplace(this.systemGlobalSettings.getTraegerId(), this.newTargetplaceName)
            .subscribe((result:any) =>
                {
                    this.alert.success('Der Zielort wurde angelegt!');

                    this.newTargetplaceName = '';

                    this.systemGlobalSettings.setZielOrte([result]);

                    this.systemTreeConfig.addChildToNodeById(this.systemTreeConfig.currentSelectedNode.id,
                        {
                            id:        'zielort' + result.id,
                            name:      result.name,
                            isVisible: true,
                            onClick: ():void =>
                                     {
                                         this.router.navigateByUrl('plugin/system/carrier/' + this.systemGlobalSettings.getTraegerId() + '/targetplace/' + result.id)
                                     }
                        }
                    )
                },
                (error:any) =>
                {
                    this.alert.error('Der Zielort wurde nicht angelegt!');

                });
    }
}