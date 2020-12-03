import {async, ComponentFixture, TestBed} from "@angular/core/testing";
import {LoginService} from "../../core/service/rest/login/login.service";
import {UsersService} from "../../core/service/rest/users/users.service";
import {Router} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {LocalizationModule} from "angular-l10n";
import {l10nConfig} from "../../core/localization/l10n.config";
import {MatDialogModule} from "@angular/material/dialog";
import {WbsSitemapHelper} from "../../core/service/rest/sitemap/data/wbs-sitemap.helper";
import {GlobalRegistryService} from "../../core/global-registry/global-registry.service";
import {AlertService, TerraComponentsModule} from "@plentymarkets/terra-components";
import {BookingViewComponent} from "./booking-view.component";

describe('Component: AppLoginComponent', () => {
    let component: BookingViewComponent;
    let fixture: ComponentFixture<BookingViewComponent>;

    const loginServiceStub: Partial<LoginService> = {};
    const usersServiceStub: Partial<UsersService> = {};
    const router: Partial<Router> = {};

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [BookingViewComponent],
            imports: [FormsModule, LocalizationModule.forRoot(l10nConfig), MatDialogModule, TerraComponentsModule],
            providers: [
                {
                    provide: LoginService,
                    useValue: loginServiceStub
                },
                {
                    provide: UsersService,
                    useValue: usersServiceStub
                },
                {
                    provide: Router,
                    useValue: router
                },
                WbsSitemapHelper,
                GlobalRegistryService,
                AlertService
            ]
        })
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(BookingViewComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });


});