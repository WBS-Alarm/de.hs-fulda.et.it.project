import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {StartComponent} from './views/start/start.component';
import {L10nLoader, TranslationModule} from 'angular-l10n';
import {FormsModule} from '@angular/forms';
import {l10nConfig} from './core/localization/l10n.config';
import {HttpClientModule} from '@angular/common/http';
import {TerraComponentsModule, TerraNodeTreeConfig} from '@plentymarkets/terra-components';
import {appRoutingProviders, routing} from './app-wbs-kleiderkammer.routing';
import {StartViewComponent} from './views/start-view.component';
import {RouterViewComponent} from './views/router/router-view.component';
import {MainMenuComponent} from './views/menu/main-menu.component';
import {AppWbsKleiderkammer} from './app-wbs-kleiderkammer';
import {AppLoginComponent} from './views/app-login/app-login.component';
import {GetSitemapService} from './core/service/rest/sitemap/wbs-sitemap.service';
import {WbsSitemapHelper} from "./core/service/rest/sitemap/data/wbs-sitemap.helper";
import {LoginService} from "./core/service/rest/login/login.service";
import {UsersService} from './core/service/rest/users/users.service';
import {UserAndSystemDataService} from './core/service/rest/users/user-and-system-data.service';
import {WbsPreloadingStrategy} from './core/strategies/preloading-strategy';
import {GlobalRegistryService} from './core/global-registry/global-registry.service';
import {IsLoggedInGuard} from './core/guards/isLoggedInGuard';
import {AppUsersComponent} from './views/app-users/app-users.component';
import {NavigationBarComponent} from "./views/navigation-bar/navigation-bar.component";
import {CarrierService} from "./core/service/rest/carrier/carrier.service";
import {SystemCarrierComponent} from './views/system/components/carrier/system-carrier.component';
import {SystemTargetplacesComponent} from "./views/system/components/targetplaces/system-targetplaces.component";
import {SystemGlobalSettingsService} from "./views/system/system-global-settings.service";
import {SystemUserResolver} from './views/system/components/user/resolver/system-user.resolver';
import {SystemTargetplacesResolver} from './views/system/components/targetplaces/resolver/system-targetplaces.resolver';
import {SystemCarrierResolver} from "./views/system/components/carrier/resolver/system-carrier-resolver";
import {CategoryService} from "./core/service/rest/categories/category.service";
import {SystemCategoryResolver} from "./views/system/components/categories/resolver/system-category.resolver";
import {AuthoritiesService} from './core/service/rest/authorities/authorities.service';
import {SystemAuthoritiesCompontent} from './views/system/components/authorities/system-authorities.compontent';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BookingViewComponent} from './views/booking/booking-view.component';
import {TransaktionService} from './core/service/rest/transaktions/transaktion.service';
import {SystemAuthorityResolver} from './views/system/components/user/resolver/system-authority.resolver';
import {SystemAuthorityUserResolver} from './views/system/components/user/resolver/system-authority-user.resolver';
import {SystemGroessenComponent} from './views/system/components/sizes/system-groessen.component';
import {SystemGroessenResolver} from './views/system/components/sizes/system-groessen.resolver';
import {GroesseService} from './core/service/rest/groesse/groesse.service';
import {SystemBestaendeComponent} from './views/system/components/bestaende/system-bestaende.component';
import {BestaendeService} from './core/service/rest/bestaende/bestaende.serice';
import {SystemBestaendeResolver} from './views/system/components/bestaende/system-bestaende.resolver';
import {StartUserResolver} from './views/start/start-user.resolver';
import {SystemComponent} from "./views/system/system.component";
import {SystemUserComponent} from "./views/system/components/user/system-user.component";
import {SystemCategoriesComponent} from "./views/system/components/categories/system-categories.component";
import {SystemNewUserComponent} from "./views/system/components/user/new/system-new-user.component";
import {SystemNewCategoryComponent} from "./views/system/components/categories/new/system-new-category.component";
import {SystemEditCarrierComponent} from "./views/system/components/carrier/edit-carrier/system-edit-carrier.component";
import {SystemNewTargetplaceComponent} from "./views/system/components/targetplaces/new/system-new-targetplace.component";
import {TranslationProvider} from "./core/localization/translation-provider";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatRadioModule} from "@angular/material/radio";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatTableModule} from "@angular/material/table";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {BestandDialogComponent} from "./views/system/components/bestaende/dialog/bestand-dialog.component";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {ResetPasswordDialogComponent} from "./views/app-login/reset-password/reset-password-dialog.component";


@NgModule({
    entryComponents:[
        BestandDialogComponent,
        ResetPasswordDialogComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        TranslationModule.forRoot(l10nConfig, {translationProvider: TranslationProvider}),
        TerraComponentsModule,
        BrowserAnimationsModule,
        routing,
        MatExpansionModule,
        MatRadioModule,
        MatFormFieldModule,
        MatSelectModule,
        MatInputModule,
        MatButtonModule,
        MatIconModule,
        MatTableModule,
        MatCheckboxModule,
        MatToolbarModule,
        MatDialogModule,
        MatSidenavModule,
        MatListModule
    ],
    declarations: [
        AppWbsKleiderkammer,
        RouterViewComponent,
        MainMenuComponent,
        StartViewComponent,
        StartComponent,
        BookingViewComponent,
        AppLoginComponent,
        AppUsersComponent,
        SystemComponent,
        SystemCarrierComponent,
        SystemBestaendeComponent,
        SystemUserComponent,
        SystemCategoriesComponent,
        SystemNewUserComponent,
        SystemAuthoritiesCompontent,
        SystemNewCategoryComponent,
        SystemEditCarrierComponent,
        SystemNewTargetplaceComponent,
        SystemTargetplacesComponent,
        SystemGroessenComponent,
        BestandDialogComponent,
        ResetPasswordDialogComponent,
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
        TransaktionService,
        WbsPreloadingStrategy,
        SystemUserResolver,
        SystemTargetplacesResolver,
        SystemCarrierResolver,
        CategoryService,
        GroesseService,
        BestaendeService,
        SystemBestaendeResolver,
        SystemCategoryResolver,
        SystemAuthorityResolver,
        SystemGroessenResolver,
        StartUserResolver,
        SystemAuthorityUserResolver,
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

export function initL10n(l10nLoader:L10nLoader):Function
{
    return ():Promise<any> => l10nLoader.load();
}

export function initUserAndSystemData(userAndSystemDataLoader:UserAndSystemDataService):Function
{
    return ():Promise<void | Error> => userAndSystemDataLoader.load();
}
