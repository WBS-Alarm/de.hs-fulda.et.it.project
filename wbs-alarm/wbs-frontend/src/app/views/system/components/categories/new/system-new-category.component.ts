import {Component} from "../../../../../../../node_modules/@angular/core";
import {CarrierService} from "../../../../../core/service/rest/carrier/carrier.service";
import {SystemGlobalSettingsService} from "../../../system-global-settings.service";
import {TerraAlertComponent} from "@plentymarkets/terra-components";

@Component({
    selector: 'system-new-categories',
    template: require('./system-new-category.component.html'),
    styles:   [require('./system-new-category.component.scss')]
})
export class SystemNewCategoryComponent
{
    private categoryName:string;

    private alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    constructor(private carrierService:CarrierService,
                private systemGlobalSettings:SystemGlobalSettingsService)
    {

    }

    public save()
    {
        let traegerId:number = this.systemGlobalSettings.getTraegerId();

        this.carrierService.createCategory(traegerId, this.categoryName).subscribe(
            (result:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Kategorie wurde erstellt!',
                        type:             'success',
                        dismissOnTimeout: null,
                        identifier:       'categoryCreated'
                    }
                )
            },
            (error:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Die Kategorie konnte nicht erstellt werden!',
                        type:             'danger',
                        dismissOnTimeout: null,
                        identifier:       'categoryNotCreated'
                    }
                )
            }
        )
    }
}