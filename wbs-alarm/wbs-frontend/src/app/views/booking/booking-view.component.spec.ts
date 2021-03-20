import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { UsersService } from '../../core/service/rest/users/users.service';
import {
    ActivatedRoute,
    Data,
    Router
} from '@angular/router';
import { FormsModule } from '@angular/forms';
import {
    L10nTranslationModule
} from 'angular-l10n';
import { l10nConfig } from '../../core/localization/l10n.config';
import { MatDialogModule } from '@angular/material/dialog';
import { WbsSitemapHelper } from '../../core/service/rest/sitemap/data/wbs-sitemap.helper';
import { GlobalRegistryService } from '../../core/global-registry/global-registry.service';
import {
    AlertService,
    TerraComponentsModule
} from '@plentymarkets/terra-components';
import { BookingViewComponent } from './booking-view.component';
import { CategoryService } from '../../core/service/rest/categories/category.service';
import { CarrierService } from '../../core/service/rest/carrier/carrier.service';
import { TransaktionService } from '../../core/service/rest/transaktions/transaktion.service';
import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Observable } from 'rxjs';
import { UserLanguage } from '../../core/localization/translation-provider';
/* tslint:disable */
xdescribe('Component: BookingViewComponent', () =>
{
    let component:BookingViewComponent;
    let fixture:ComponentFixture<BookingViewComponent>;

    const categoryService:Partial<CategoryService> = {};
    const activatedRouteStub:Partial<ActivatedRoute> = {
        data: new Observable<Data>()
    };
    const carrierService:Partial<CarrierService> = {};
    const usersServiceStub:Partial<UsersService> = {
        getCurrentUsers():Observable<any>
        {
            return new Observable<any>();
        }
    };
    const transaktionService:Partial<TransaktionService> = {};
    const router:Partial<Router> = {};

    beforeEach(waitForAsync(() =>
    {
        TestBed.configureTestingModule({
            declarations: [BookingViewComponent],
            imports:      [
                FormsModule,
                L10nTranslationModule.forRoot(l10nConfig, { userLanguage: UserLanguage }),
                MatDialogModule,
                TerraComponentsModule,
                MatTableModule,
                MatCheckboxModule,
                MatExpansionModule,
                MatFormFieldModule,
                MatSelectModule,
                MatRadioModule,
                BrowserAnimationsModule
            ],
            providers:    [
                {
                    provide:  CategoryService,
                    useValue: categoryService
                },
                {
                    provide:  TransaktionService,
                    useValue: transaktionService
                },
                {
                    provide:  CarrierService,
                    useValue: carrierService
                },
                {
                    provide:  UsersService,
                    useValue: usersServiceStub
                },
                {
                    provide:  Router,
                    useValue: router
                },
                {
                    provide:  ActivatedRoute,
                    useValue: activatedRouteStub
                },
                WbsSitemapHelper,
                GlobalRegistryService,
                AlertService
            ]
        })
    }));

    beforeEach(() =>
    {
        fixture = TestBed.createComponent(BookingViewComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create an instance', () =>
    {
        expect(component).toBeTruthy();
    });
});
/* tslint:enable */
