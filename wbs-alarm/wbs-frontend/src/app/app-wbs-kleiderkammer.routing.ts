import { ModuleWithProviders } from '@angular/core';
import {
    RouterModule,
    Routes
} from '@angular/router';
import { StartViewComponent } from './views/start-view.component';
import { RouterViewComponent } from './views/router/router-view.component';
import {AppLoginComponent} from "./views/app-login/app-login.component";
import { GUARDS } from './core/guards/guards';
import { WbsPreloadingStrategy } from './core/strategies/preloading-strategy';
import {SystemComponent} from "./views/system/system.component";
import { SystemCarrierComponent } from './views/system/components/carrier/system-carrier.component';
import {SystemUserComponent} from "./views/system/components/user/system-user.component";
import {SystemCategoriesComponent} from "./views/system/components/categories/system-categories.component";
import {SystemNewUserComponent} from "./views/system/components/user/new/system-new-user.component";
import {SystemNewTargetplaceComponent} from "./views/system/components/targetplaces/new/system-new-targetplace.component";
import {
    SystemNewCategoryComponent
} from "./views/system/components/categories/new/system-new-category.component";
import {SystemTargetplacesComponent} from "./views/system/components/targetplaces/system-targetplaces.component";
import { SystemUserResolver } from './views/system/components/user/resolver/system-user.resolver';
import { SystemTargetplacesResolver } from './views/system/components/targetplaces/resolver/system-targetplaces.resolver';
import {SystemEditCarrierComponent} from "./views/system/components/carrier/edit-carrier/system-edit-carrier.component";
import {SystemCarrierResolver} from "./views/system/components/carrier/resolver/system-carrier-resolver";
import {SystemCategoryResolver} from "./views/system/components/categories/resolver/system-category.resolver";
import { BookingViewComponent } from './views/booking/booking-view.component';
import { SystemAuthorityResolver } from './views/system/components/user/resolver/system-authority.resolver';
import { SystemAuthoritiesCompontent } from './views/system/components/authorities/system-authorities.compontent';
import { SystemAuthorityUserResolver } from './views/system/components/user/resolver/system-authority-user.resolver';
import { SystemGroessenResolver } from './views/system/components/sizes/system-groessen.resolver';
import { SystemBestaendeResolver } from './views/system/components/bestaende/system-bestaende.resolver';
import { StartUserResolver } from './views/start/start-user.resolver';
import {BerichtViewComponent} from "./views/reports/bericht-view.component";
import {NewPasswordComponent} from "./views/app-login/new-password/new-password.component";

const appRoutes:Routes = [
    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full',
    },
    {
        path: 'login',
        component: AppLoginComponent
    },
    {
        path: 'update-password/:token',
        component: NewPasswordComponent
    },
    {
        path: 'plugin',
        component: RouterViewComponent,
        canActivate: GUARDS,
        children:[
            {
                path: '',
                data:        {
                    label:       'menu'
                },
                redirectTo: 'start',
                pathMatch: 'full'
            },
            {
                path: 'start',
                component: StartViewComponent,
                canActivate: GUARDS,
                data: {
                    label: 'start'
                },
                resolve:
                    {
                        user: StartUserResolver
                    }
            },
            {
                 path: 'booking',
                 component: BookingViewComponent,
                 canActivate: GUARDS,
                 data: {
                     label: 'booking.booking'
                 }
             },
            {
                path: 'reports',
                component: BerichtViewComponent,
                canActivate: GUARDS,
                data: {
                    label: 'report.report'
                },
                resolve: {
                    user: StartUserResolver
                }
            },
            {
                path: 'system',
                component: SystemComponent,
                canActivate: GUARDS,
                data: {
                    label: 'system.system'
                },
                children: [
                    {
                        path: 'carrier',
                        component: SystemCarrierComponent,
                        canActivate: GUARDS,
                        data: {
                            label: 'system.village.addVillage'
                        }
                    },
                    {
                        path: 'carrier/:carrierId',
                        component: SystemEditCarrierComponent,
                        canActivate: GUARDS,
                        data: {
                            label: 'system.village.village'
                        },
                        resolve:
                            {
                                carrier: SystemCarrierResolver
                            }
                    },
                    {
                        path: 'carrier/:carrierId/user',
                        component: SystemNewUserComponent,
                        canActivate: GUARDS,
                        data: {
                            label: 'system.user.addUser'
                        }
                    },
                    {
                        path: 'carrier/:carrierId/user/:userId',
                        component: SystemUserComponent,
                        canActivate: GUARDS,
                        data: {
                            label: 'system.user.user'
                        },
                        resolve:
                            {
                                user: SystemUserResolver
                            }
                    },
                    {
                        path: 'carrier/:carrierId/user/:userId/authority/:userId',
                        component: SystemAuthoritiesCompontent,
                        canActivate: GUARDS,
                        data: {
                            label: 'system.user.user'
                        },
                        resolve:
                            {
                                authority: SystemAuthorityResolver,
                                user: SystemUserResolver,
                                userWithAuthorities: SystemAuthorityUserResolver
                            }
                    },
                    {
                        path: 'carrier/:carrierId/targetplace',
                        component: SystemNewTargetplaceComponent,
                        canActivate: GUARDS,
                        data: {
                            label: 'system.targetPlaces.addTargetPlace'
                        }
                    },
                    {
                        path: 'carrier/:carrierId/targetplace/:targetplaceId',
                        component: SystemTargetplacesComponent,
                        canActivate: GUARDS,
                        data: {
                            label: 'system.targetPlaces.targetPlaces'
                        },
                        resolve:
                            {
                                targetPlace: SystemTargetplacesResolver,
                                bestaende: SystemBestaendeResolver
                            }
                    },
                    {
                        path: 'carrier/:carrierId/category',
                        component: SystemNewCategoryComponent,
                        canActivate: GUARDS,
                        data: {
                            label: 'system.clothes.addClothes'
                        }
                    },
                    {
                        path: 'carrier/:carrierId/category/:categoryId',
                        component: SystemCategoriesComponent,
                        canActivate: GUARDS,
                        data: {
                            label: 'system.clothes.clothes'
                        },
                        resolve:
                            {
                                category: SystemCategoryResolver,
                                groesse: SystemGroessenResolver
                            }
                    }
                ]
            }
        ]
    },

];

export const appRoutingProviders:Array<any> = [];

export const routing:ModuleWithProviders =
    RouterModule.forRoot(appRoutes, {
        preloadingStrategy: WbsPreloadingStrategy,
        onSameUrlNavigation: 'reload',
        useHash: true
    });
