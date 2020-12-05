import {
    async,
    ComponentFixture,
    TestBed
} from '@angular/core/testing';

import { AppLoginComponent } from './app-login.component';
import { FormsModule } from '@angular/forms';
import { LocalizationModule } from 'angular-l10n';
import { l10nConfig } from '../../core/localization/l10n.config';
import { LoginService } from '../../core/service/rest/login/login.service';
import { WbsSitemapHelper } from '../../core/service/rest/sitemap/data/wbs-sitemap.helper';
import { GlobalRegistryService } from '../../core/global-registry/global-registry.service';
import { AlertService } from '@plentymarkets/terra-components';
import { UsersService } from '../../core/service/rest/users/users.service';
import { MatDialogModule } from '@angular/material/dialog';
import { Router } from '@angular/router';

describe('Component: AppLoginComponent', () =>
{
    let component:AppLoginComponent;
    let fixture:ComponentFixture<AppLoginComponent>;

    const loginServiceStub:Partial<LoginService> = {};
    const usersServiceStub:Partial<UsersService> = {};
    const router:Partial<Router> = {};

    beforeEach(async(() =>
    {
        TestBed.configureTestingModule({
            declarations: [AppLoginComponent],
            imports:      [FormsModule,
                           LocalizationModule.forRoot(l10nConfig),
                           MatDialogModule],
            providers:    [
                {
                    provide:  LoginService,
                    useValue: loginServiceStub
                },
                {
                    provide:  UsersService,
                    useValue: usersServiceStub
                },
                {
                    provide:  Router,
                    useValue: router
                },
                WbsSitemapHelper,
                GlobalRegistryService,
                AlertService
            ]
        });
    }));

    beforeEach(() =>
    {
        fixture = TestBed.createComponent(AppLoginComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () =>
    {
        expect(component).toBeTruthy();
    });
});
