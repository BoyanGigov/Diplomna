import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AgGridModule} from 'ag-grid-angular';
import {AppRoutingModule} from './app-routing.module';
import {RouterModule} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {MatTableModule} from '@angular/material/table'
import {MatSelectModule} from '@angular/material/select';
import {MatListModule} from '@angular/material/list';
import {MatCardModule} from '@angular/material/card';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';

import {TopBarComponent} from './top-bar/top-bar.component';
import {MoodleCourseTableComponent} from './course-page/table/course-table.component';
import {StatisticPageComponent} from './statistic-page/statistic-page.component';
import {LoginPageComponent} from './login-page/login-page.component';

import {AppComponent} from './app.component';


@NgModule({
  imports:
  [ BrowserModule,
  FormsModule,
  AppRoutingModule,
  HttpClientModule,
  BrowserAnimationsModule,
  MatTableModule,
  MatSelectModule,
  MatListModule,
  MatCardModule,
  MatTooltipModule,
  MatButtonModule,
  MatInputModule,
  ],
  declarations:
  [ AppComponent,
    TopBarComponent,
    MoodleCourseTableComponent,
    StatisticPageComponent,
    LoginPageComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
