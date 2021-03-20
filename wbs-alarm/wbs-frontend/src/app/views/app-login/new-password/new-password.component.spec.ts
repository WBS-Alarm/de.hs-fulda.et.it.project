import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import {
    ActivatedRoute,
    Params,
    Router
} from '@angular/router';
import { FormsModule } from '@angular/forms';
import { LocalizationModule } from 'angular-l10n';
import { l10nConfig } from '../../../core/localization/l10n.config';
import { NewPasswordService } from './service/new-password.service';
import { NewPasswordComponent } from './new-password.component';
import { AlertService } from '@plentymarkets/terra-components';
import { Observable } from 'rxjs';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('Component: NewPasswordComponent', () =>
{
    let component:NewPasswordComponent;
    let fixture:ComponentFixture<NewPasswordComponent>;

    const newPasswordServiceStub:Partial<NewPasswordService> = {};
    const activatedRouteStub:Partial<ActivatedRoute> = {
        params: new Observable<Params>()
    };
    const router:Partial<Router> = {};

    beforeEach(waitForAsync(() =>
    {
        TestBed.configureTestingModule({
            declarations: [NewPasswordComponent],
            imports:      [FormsModule,
                           LocalizationModule.forRoot(l10nConfig),
                           MatInputModule,
                           BrowserAnimationsModule],
            providers:    [
                {
                    provide:  Router,
                    useValue: router
                },
                {
                    provide:  NewPasswordService,
                    useValue: newPasswordServiceStub
                },
                {
                    provide:  ActivatedRoute,
                    useValue: activatedRouteStub
                },
                AlertService
            ]
        });
    }));

    beforeEach(() =>
    {
        fixture = TestBed.createComponent(NewPasswordComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create an instance', () =>
    {
        expect(component).toBeTruthy();
    });
});

