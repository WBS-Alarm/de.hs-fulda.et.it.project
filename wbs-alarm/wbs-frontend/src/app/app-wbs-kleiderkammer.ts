import {
    Component, OnInit,
    ViewEncapsulation
} from '@angular/core';
import {WbsSitemapHelper} from "./core/service/rest/sitemap/data/wbs-sitemap.helper";
import {GetSitemapService} from "./core/service/rest/sitemap/wbs-sitemap.service";

@Component({
    selector:      'app-terra-basic',
    template:      require('./app-wbs-kleiderkammer.html'),
    styles:        [require('./app-wbs-kleiderkammer.scss')],
    encapsulation: ViewEncapsulation.None
})
export class AppWbsKleiderkammer implements OnInit
{
    constructor(private sitemapHelper:WbsSitemapHelper,
                private sitemapService:GetSitemapService)
    {

    }

    public ngOnInit():void
    {
           this.sitemapService.getSitemaps().subscribe((result:any) =>
           {
               this.sitemapHelper.sitemaps = result;
           });


    }
}
