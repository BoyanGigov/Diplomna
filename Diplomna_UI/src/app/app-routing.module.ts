import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {CommonModule} from "@angular/common";

import {MoodleCourseTableComponent} from './course-page/table/course-table.component';
import {StatisticPageComponent} from './statistic-page/statistic-page.component';
import {LoginPageComponent} from './login-page/login-page.component';
import {AboutPageComponent} from './about-page/about-page.component';

const routes: Routes = [
    {path: 'courses', component: MoodleCourseTableComponent},
    {path: 'statistics', component: StatisticPageComponent},
    {path: 'about', component: AboutPageComponent},
    {path: '', component: LoginPageComponent},
];

@NgModule({
    imports: [CommonModule,
        RouterModule.forRoot(routes, { enableTracing: false, useHash: false}) // useHash true allows loading the page through browser url
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {}