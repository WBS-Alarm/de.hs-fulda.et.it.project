import {
    AfterViewInit,
    Component,
    OnInit,
    ViewEncapsulation
} from '@angular/core';
import { WbsSitemapHelper } from "./core/service/rest/sitemap/data/wbs-sitemap.helper";
import { GetSitemapService } from "./core/service/rest/sitemap/wbs-sitemap.service";
import { Router } from '@angular/router';
import { GlobalRegistryService } from './core/global-registry/global-registry.service';
import { Subscription } from 'rxjs';

@Component({
    selector:      'app-terra-basic',
    template:      require('./app-wbs-kleiderkammer.html'),
    styles:        [require('./app-wbs-kleiderkammer.scss')],
    encapsulation: ViewEncapsulation.None
})
export class AppWbsKleiderkammer implements OnInit
{
    constructor(private sitemapHelper:WbsSitemapHelper,
                private globalRegistry:GlobalRegistryService,
                private sitemapService:GetSitemapService,
                private router:Router)
    {

    }

    public ngOnInit():void
    {
        this.sitemapService.getSitemaps().subscribe((result:any) =>
        {
            this.sitemapHelper.sitemaps = result;
        });

        // Navigate to start of system on reload
        if(window.location.href.indexOf('system') > 0)
        {
            this.router.navigateByUrl('plugin/system')
        }
    }
}
