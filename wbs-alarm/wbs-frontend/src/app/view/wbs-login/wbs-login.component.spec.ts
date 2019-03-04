import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WbsLoginComponent } from './wbs-login.component';

describe('WbsLoginComponent', () => {
  let component: WbsLoginComponent;
  let fixture: ComponentFixture<WbsLoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WbsLoginComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WbsLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
