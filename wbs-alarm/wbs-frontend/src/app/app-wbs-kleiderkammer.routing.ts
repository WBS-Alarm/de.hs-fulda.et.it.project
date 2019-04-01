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
                }
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
