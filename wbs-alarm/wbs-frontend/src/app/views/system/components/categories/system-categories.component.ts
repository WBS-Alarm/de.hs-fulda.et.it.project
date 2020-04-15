import {Component} from "@angular/core";
import {ActivatedRoute, Data} from "@angular/router";
import {AlertService, TerraAlertComponent, TerraNodeTreeConfig} from "@plentymarkets/terra-components";
import {SystemGlobalSettingsService} from "../../system-global-settings.service";
import {ExampleTreeData} from "../../system.component";
import {Observable} from "rxjs";
import {CategoryService} from "../../../../core/service/rest/categories/category.service";
import {SystemCategoryInterface} from "./data/system-category.interface";

@Component({
    selector: 'system-categories',
    templateUrl: './system-categories.component.html',
    styleUrls:   ['./system-categories.component.scss']
})
export class SystemCategoriesComponent
{
    public routeData$:Observable<Data>;

    constructor(public route:ActivatedRoute,
                public categoryService:CategoryService,
                public alert:AlertService,
                public systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>,
                public systemGlobalSettings:SystemGlobalSettingsService)
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
            this.alert.success('Änderungen gespeichert!');

            this.systemTreeConfig.currentSelectedNode.name = category.name;
        },
            (error:any) =>
            {
                this.alert.error('Änderungen konnten nicht gespeichert werden!');
            })


    }

    public delete(category:SystemCategoryInterface):void
    {
        this.categoryService.deleteCategory(category).subscribe(
            (result:any) =>
            {
                this.alert.success('Die Kategorie wurde gelöscht!');

                this.systemTreeConfig.removeNodeById(this.systemTreeConfig.currentSelectedNode.id)
            },
            (error:any)=>
            {
                this.alert.error('Die Kategorie konnte nicht gelöscht werden!');
            })
    }
}