import {Component, OnInit} from '@angular/core';
import {CarrierService} from '../../../../core/service/rest/carrier/carrier.service';
import {AlertService, TerraNodeTreeConfig} from '@plentymarkets/terra-components';
import {ActivatedRoute, Data, Router} from '@angular/router';
import {Observable} from "rxjs";
import {SystemGlobalSettingsService} from '../../system-global-settings.service';
import {SystemZielortInterface} from './data/system-zielort.interface';
import {ExampleTreeData} from '../../system.component';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {LockTargetplaceDialogComponent} from "./dialog/lock-targetplace-dialog.component";

@Component({
    selector: 'system-targetplace',
    templateUrl: './system-targetplaces.component.html',
    styleUrls: ['./system-targetplaces.component.scss']
})
export class SystemTargetplacesComponent implements OnInit {
    public routeData$:Observable<Data>;
    public gesperrt:boolean;

    private traegerId:number;

    constructor(public carrierService:CarrierService,
                public route:ActivatedRoute,
                public router:Router,
                public alert:AlertService,
                public dialog:MatDialog,
                public systemGlobalSettings:SystemGlobalSettingsService,
                public systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>)
    {

    }

    public ngOnInit():void
    {
        this.routeData$ = this.route.data;

        this.route.params.subscribe((params:any) =>
        {
            this.traegerId = params.carrierId;
        });

        this.route.data.subscribe((data:any) =>
        {
            this.gesperrt = data.targetPlace.erfasst;
        })
    }

    public save(targetPlace:SystemZielortInterface):void
    {
        this.carrierService.updateTargetplace(targetPlace).subscribe(
            (result:any) =>
            {
                this.alert.success('Änderungen gespeichert!');

                this.systemTreeConfig.currentSelectedNode.name = targetPlace.name;
            },
            (error:any) =>
            {
                this.alert.error('Änderungen konnten nicht gespeichert werden: ' + error.error.message);
            })
    }

    public delete(targetPlace:SystemZielortInterface):void
    {
        this.carrierService.deleteTargetplace(targetPlace).subscribe((result:any) =>
            {
                this.alert.success('Der Zielort wurde gelöscht!');

                this.systemTreeConfig.removeNodeById(this.systemTreeConfig.currentSelectedNode.id);

                this.router.navigateByUrl('/plugin/system/carrier/' + this.traegerId + '/targetplace')
            },
            (error:any) =>
            {
                this.alert.error('Der Zielort konnte nicht gelöscht werden! ' + error.error.message);

            })
    }

    public lock(targetPlace:SystemZielortInterface):void
    {
        const lockTargetPlaceDialog:MatDialogRef<LockTargetplaceDialogComponent> = this.dialog.open(LockTargetplaceDialogComponent, {autoFocus:true});

        lockTargetPlaceDialog.afterClosed().subscribe((approved:boolean) =>
        {
            if(approved)
            {
                this.carrierService.lockTargetplace(targetPlace).subscribe(
                    (result:any) =>
                    {
                        this.alert.success('Der Zielort wurde für die Erfassung gesperrt!');
                        this.gesperrt = true;
                    },
                    (error:any) =>
                    {
                        this.alert.error('Der Zielort konnte nicht für die Erfassung gesperrt werden! ' + error.error.message);
                    })
            }
        });



    }
}