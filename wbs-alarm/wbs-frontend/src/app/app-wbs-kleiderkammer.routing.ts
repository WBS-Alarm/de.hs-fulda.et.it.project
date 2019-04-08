import { ModuleWithProviders } from '@angular/core';
import {
    RouterModule,
    Routes
} from '@angular/router';
import { StartViewComponent } from './views/start-view.component';
import { RouterViewComponent } from './views/router/router-view.component';
import { ExampleViewComponent } from './views/example/example-view.component';
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
                }
            },
            {
                path: 'example',
                component: ExampleViewComponent,
                canActivate: GUARDS,
                data: {
                    label: 'example'
                }
            },
            // {
            //     path: 'booking',
            //     component: ExampleViewComponent,
            //     canActivate: GUARDS,
            //     data: {
            //         label: 'booking.booking'
            //     }
            // },
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
                            label: 'system.village.village'
                        }
                    },
                    {
                        path: 'carrier/:carrierId',
                        component: SystemCarrierComponent,
                        canActivate: GUARDS,
                        data: {
                            label: 'system.village.village'
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
                        path: 'carrier/:carrierId/targetplace',
                        component: SystemNewTargetplaceComponent,
                        canActivate: GUARDS,
                        data: {
                            label: 'system.targetPlace.addTargetPlace'
                        }
                    },
                    {
                        path: 'carrier/:carrierId/targetplace/:targetplaceId',
                        component: SystemTargetplacesComponent,
                        canActivate: GUARDS,
                        data: {
                            label: 'system.targetPlace.targetPlace'
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
