import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserIssuesComponent } from './user-issues.component';

describe('UserIssuesComponent', () => {
  let component: UserIssuesComponent;
  let fixture: ComponentFixture<UserIssuesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserIssuesComponent]
    });
    fixture = TestBed.createComponent(UserIssuesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
