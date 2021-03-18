import {
    async,
    ComponentFixture,
    TestBed
} from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { LocalizationModule } from 'angular-l10n';
import { l10nConfig } from '../../../core/localization/l10n.config';
import {
    MatDialogModule,
    MatDialogRef
} from '@angular/material/dialog';
import { ResetPasswordDialogComponent } from './reset-password-dialog.component';
import { TerraComponentsModule } from '@plentymarkets/terra-components';
import { Router } from '@angular/router';


describe('Component: ResetPasswordDialogComponent', () =>
{
    let component:ResetPasswordDialogComponent;
    let fixture:ComponentFixture<ResetPasswordDialogComponent>;

    const matDialogRef:Partial<MatDialogRef<ResetPasswordDialogComponent>> = {};
    const router:Partial<Router> = {};

    beforeEach(async(() =>
    {
        TestBed.configureTestingModule({
            declarations: [ResetPasswordDialogComponent],
            imports:      [FormsModule,
                           LocalizationModule.forRoot(l10nConfig),
                           MatDialogModule,
                           TerraComponentsModule],
            providers:    [{
                provide:  MatDialogRef,
                useValue: matDialogRef
            },
                           {
                               provide:  Router,
                               useValue: router
                           },
            ]
        });
    }));

    beforeEach(() =>
    {
        fixture = TestBed.createComponent(ResetPasswordDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create an instance', () =>
    {
        expect(component).toBeTruthy();
    });
});
