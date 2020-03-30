
import {WbsSitemapHelper} from "./core/service/rest/sitemap/data/wbs-sitemap.helper";
import {GetSitemapService} from "./core/service/rest/sitemap/wbs-sitemap.service";

import {GlobalRegistryService} from './core/global-registry/global-registry.service';
import {UsersService} from './core/service/rest/users/users.service';
import {Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";

@Component({
    selector: 'app-terra-basic',
    templateUrl: './app-wbs-kleiderkammer.html',
    styleUrls: ['./app-wbs-kleiderkammer.scss']
})
export class AppWbsKleiderkammer implements OnInit
{
    constructor(public sitemapHelper: WbsSitemapHelper,
                public userService: UsersService,
                public globalRegistry: GlobalRegistryService,
                public sitemapService: GetSitemapService,
                public router: Router)
    {
        // Fix lint
    }

    public ngOnInit(): void {
        this.sitemapService.getSitemaps().subscribe((result: any) => {
            this.sitemapHelper.sitemaps = result;
        });

        this.userService.getCurrentUsers().subscribe(
            (result: any) => {
                this.globalRegistry.currentUser = result;
            }
        );

        // Navigate to start of system on reload
        if (window.location.href.indexOf('system') > 0) {
            this.router.navigateByUrl('plugin/system')
        }
    }
}
