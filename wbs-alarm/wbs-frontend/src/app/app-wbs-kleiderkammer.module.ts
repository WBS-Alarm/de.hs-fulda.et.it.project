import {
    APP_INITIALIZER,
    NgModule
} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { StartComponent } from './views/start/start.component';
import { HttpModule } from '@angular/http';
import {
    L10nLoader,
    TranslationModule
} from 'angular-l10n';
import { FormsModule } from '@angular/forms';
import { l10nConfig } from './core/localization/l10n.config';
import { HttpClientModule } from '@angular/common/http';
import { TerraComponentsModule } from '@plentymarkets/terra-components';
import { RouterModule } from '@angular/router';
import {
    appRoutingProviders,
    routing
} from './app-wbs-kleiderkammer.routing';
import { StartViewComponent } from './views/start-view.component';
import { RouterViewComponent } from './views/router/router-view.component';
import { MainMenuComponent } from './views/menu/main-menu.component';
import { TerraNodeTreeConfig } from '@plentymarkets/terra-components';
import { ExampleViewComponent } from './views/example/example-view.component';
import { TableComponent } from './views/example/overview/table/table.component';
import { FilterComponent } from './views/example/overview/filter/filter.component';
import { OverviewViewComponent } from './views/example/overview/overview-view.component';
import { AppWbsKleiderkammer } from './app-wbs-kleiderkammer';
import { AppLoginComponent } from './views/app-login/app-login.component';
import { GetSitemapService } from './core/service/rest/sitemap/wbs-sitemap.service';
import { WbsSitemapHelper } from "./core/service/rest/sitemap/data/wbs-sitemap.helper";
import {LoginService} from "./core/service/rest/login/login.service";
import { UsersService } from './core/service/rest/users/users.service';
import { UserAndSystemDataService } from './core/service/rest/users/user-and-system-data.service';
import { WbsPreloadingStrategy } from './core/strategies/preloading-strategy';
import { GlobalRegistryService } from './core/global-registry/global-registry.service';
import { IsLoggedInGuard } from './core/guards/isLoggedInGuard';
import { AppUsersComponent } from './views/app-users/app-users.component';
import {NavigationBarComponent} from "./views/navigation-bar/navigation-bar.component";
import {SystemComponent} from "./views/system/system.component";
import {CarrierService} from "./core/service/rest/carrier/carrier.service";
import { SystemCarrierComponent } from './views/system/components/carrier/system-carrier.component';
import {SystemUserComponent} from "./views/system/components/user/system-user.component";
import {SystemCategoriesComponent} from "./views/system/components/categories/system-categories.component";
import {SystemNewUserComponent} from "./views/system/components/user/new/system-new-user.component";
import {SystemNewCategoryComponent} from "./views/system/components/categories/new/system-new-category.component";
import {SystemNewTargetplaceComponent} from "./views/system/components/targetplaces/new/system-new-targetplace.component";
import {SystemTargetplacesComponent} from "./views/system/components/targetplaces/system-targetplaces.component";
import {SystemGlobalSettingsService} from "./views/system/system-global-settings.service";
import { SystemUserResolver } from './views/system/components/user/resolver/system-user.resolver';
import { SystemTargetplacesResolver } from './views/system/components/targetplaces/resolver/system-targetplaces.resolver';
import {SystemEditCarrierComponent} from "./views/system/components/carrier/edit-carrier/system-edit-carrier.component";
import {SystemCarrierResolver} from "./views/system/components/carrier/resolver/system-carrier-resolver";
import {CategoryService} from "./core/service/rest/categories/category.service";
import {SystemCategoryResolver} from "./views/system/components/categories/resolver/system-category.resolver";
import { AuthoritiesService } from './core/service/rest/authorities/authorities.service';
import { SystemAuthoritiesCompontent } from './views/system/components/authorities/system-authorities.compontent';


@NgModule({
    imports:      [
        BrowserModule,
        HttpModule,
        FormsModule,
        HttpClientModule,
        TranslationModule.forRoot(l10nConfig),
        //RouterModule.forRoot([]),
        TerraComponentsModule,
        routing
    ],
    declarations: [
        AppWbsKleiderkammer,
        RouterViewComponent,
        MainMenuComponent,
        StartViewComponent,
        StartComponent,
        ExampleViewComponent,
        TableComponent,
        FilterComponent,
        OverviewViewComponent,
        AppLoginComponent,
        AppUsersComponent,
        SystemComponent,
        SystemCarrierComponent,
        SystemUserComponent,
        SystemCategoriesComponent,
        SystemNewUserComponent,
        SystemAuthoritiesCompontent,
        SystemNewCategoryComponent,
        SystemEditCarrierComponent,
        SystemNewTargetplaceComponent,
        SystemTargetplacesComponent,
        NavigationBarComponent
    ],
    providers:    [
        {
            provide:    APP_INITIALIZER,
            useFactory: initL10n,
            deps:       [L10nLoader],
            multi:      true
        },
        {
            provide:    APP_INITIALIZER, // APP_INITIALIZER will execute the function when the app is initialized and delay what
                                         // it provides.
            useFactory: initUserAndSystemData,
            deps:       [UserAndSystemDataService],
            multi:      true
        },
        appRoutingProviders,
        GetSitemapService,
        WbsSitemapHelper,
        LoginService,
        UsersService,
        GlobalRegistryService,
        SystemGlobalSettingsService,
        IsLoggedInGuard,
        CarrierService,
        AuthoritiesService,
        UserAndSystemDataService,
        WbsPreloadingStrategy,
        SystemUserResolver,
        SystemTargetplacesResolver,
        SystemCarrierResolver,
        CategoryService,
        SystemCategoryResolver,
        TerraNodeTreeConfig
    ],
    bootstrap:    [
        AppWbsKleiderkammer
    ]
})
export class AppWbsKleiderkammerModule
{
    constructor(public l10nLoader:L10nLoader)
    {
        this.l10nLoader.load();
    }
}

function initL10n(l10nLoader:L10nLoader):Function
{
    return ():Promise<void> => l10nLoader.load();
}

function initUserAndSystemData(userAndSystemDataLoader:UserAndSystemDataService):Function
{
    return ():Promise<void | Error> => userAndSystemDataLoader.load();
}
