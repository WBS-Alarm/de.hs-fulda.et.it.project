import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import {
    L10nTranslationModule
} from 'angular-l10n';
import { l10nConfig } from '../../../core/localization/l10n.config';
import {
    MatDialogModule,
    MatDialogRef
} from '@angular/material/dialog';
import { ResetPasswordDialogComponent } from './reset-password-dialog.component';
import { TerraComponentsModule } from '@plentymarkets/terra-components';
import { Router } from '@angular/router';
import { UserLanguage } from '../../../core/localization/translation-provider';


describe('Component: ResetPasswordDialogComponent', ():any =>
{
    let component:ResetPasswordDialogComponent;
    let fixture:ComponentFixture<ResetPasswordDialogComponent>;

    const matDialogRef:Partial<MatDialogRef<ResetPasswordDialogComponent>> = {};
    const router:Partial<Router> = {};

    beforeEach(waitForAsync(():any =>
    {
        TestBed.configureTestingModule({
            declarations: [ResetPasswordDialogComponent],
            imports:      [FormsModule,
                           L10nTranslationModule.forRoot(l10nConfig, { userLanguage: UserLanguage }),
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

    beforeEach(():any =>
    {
        fixture = TestBed.createComponent(ResetPasswordDialogComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create an instance', ():any =>
    {
        expect(component).toBeTruthy();
    });
});
