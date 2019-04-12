import {Component} from "../../../../../../node_modules/@angular/core";
import {ActivatedRoute, Data} from "../../../../../../node_modules/@angular/router";
import {TerraAlertComponent, TerraNodeTreeConfig} from "@plentymarkets/terra-components";
import {SystemGlobalSettingsService} from "../../system-global-settings.service";
import {ExampleTreeData} from "../../system.component";
import {Observable} from "rxjs";
import {CategoryService} from "../../../../core/service/rest/categories/category.service";
import {SystemCategoryInterface} from "./data/system-category.interface";

@Component({
    selector: 'system-categories',
    template: require('./system-categories.component.html'),
    styles:   [require('./system-categories.component.scss')]
})
export class SystemCategoriesComponent
{
    protected routeData$:Observable<Data>;

    private alert:TerraAlertComponent = TerraAlertComponent.getInstance();

    constructor(private route:ActivatedRoute,
                private categoryService:CategoryService,
                private systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>,
                private systemGlobalSettings:SystemGlobalSettingsService)
    {

    }

    public ngOnInit():void
    {
        this.routeData$ = this.route.data;
    }

    public save(category:SystemCategoryInterface):void
    {
        this.categoryService.editCategory(category).subscribe(
            (result:any) =>
        {
            this.alert.addAlert(
                {
                    msg:              'Änderungen gespeichert!',
                    type:             'success',
                    dismissOnTimeout: null,
                    identifier:       'categoryEdited'
                }
            );
            this.systemTreeConfig.currentSelectedNode.name = category.name;
        },
            (error:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Änderungen konnten nicht gespeichert werden!',
                        type:             'danger',
                        dismissOnTimeout: null,
                        identifier:       'categoryNotEdited'
                    }
                )
            })


    }

    public delete(category:SystemCategoryInterface):void
    {
        this.categoryService.deleteCategory(category).subscribe(
            (result:any) =>
            {
                this.alert.addAlert(
                    {
                        msg:              'Die Kategorie wurde gelöscht!',
                        type:             'success',
                        dismissOnTimeout: null,
                        identifier:       'categoryDeleted'
                    }
                )

                this.systemTreeConfig.removeNodeById(this.systemTreeConfig.currentSelectedNode.id)
            },
            (error:any)=>
            {
                this.alert.addAlert(
                    {
                        msg:              'Die Kategorie konnte nicht gelöscht werden!',
                        type:             'danger',
                        dismissOnTimeout: null,
                        identifier:       'categoryNotDeleted'
                    }
                )
            })
    }
}