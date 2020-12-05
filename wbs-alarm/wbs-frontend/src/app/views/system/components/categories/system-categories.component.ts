import {
    Component,
    OnInit
} from '@angular/core';
import {
    ActivatedRoute,
    Data,
    Router
} from '@angular/router';
import {
    AlertService,
    TerraNodeTreeConfig
} from '@plentymarkets/terra-components';
import { SystemGlobalSettingsService } from '../../system-global-settings.service';
import { ExampleTreeData } from '../../system.component';
import { Observable } from 'rxjs';
import { CategoryService } from '../../../../core/service/rest/categories/category.service';
import { SystemCategoryInterface } from './data/system-category.interface';

@Component({
    // tslint:disable-next-line:component-selector
    selector:    'system-categories',
    templateUrl: './system-categories.component.html',
    styleUrls:   ['./system-categories.component.scss']
})
export class SystemCategoriesComponent implements OnInit
{
    public routeData$:Observable<Data>;

    public traegerId:number;

    constructor(public route:ActivatedRoute,
                public categoryService:CategoryService,
                public alert:AlertService,
                public router:Router,
                public systemTreeConfig:TerraNodeTreeConfig<ExampleTreeData>,
                public systemGlobalSettings:SystemGlobalSettingsService)
    {

    }

    public ngOnInit():void
    {
        this.routeData$ = this.route.data;

        this.route.params.subscribe((params:any) =>
        {
            this.traegerId = params.carrierId;
        });
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
                this.alert.error('Änderungen konnten nicht gespeichert werden! ' + error.error.message);
            });


    }

    public delete(category:SystemCategoryInterface):void
    {
        this.categoryService.deleteCategory(category).subscribe(
            (result:any) =>
            {
                this.alert.success('Die Kategorie wurde gelöscht!');

                this.systemTreeConfig.removeNodeById(this.systemTreeConfig.currentSelectedNode.id);

                this.router.navigateByUrl('/plugin/system/carrier/' + this.traegerId + '/category');
            },
            (error:any) =>
            {
                this.alert.error('Die Kategorie konnte nicht gelöscht werden! ' + error.error.message);
            });
    }
}
