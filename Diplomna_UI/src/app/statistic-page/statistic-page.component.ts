import {Component, OnInit, AfterViewInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';

import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatOptionSelectionChange} from '@angular/material/core';
import {MatButtonModule} from '@angular/material/button';

import {CoursePageService, MoodleCourseSection, MoodleCourseStatistic} from '../course-page/service/course-service.service';

import * as d3 from 'd3-selection';
import * as d3Scale from 'd3-scale';
import * as d3Array from 'd3-array';
import * as d3Axis from 'd3-axis';

@Component({
    selector: 'app-statistic-page',
    templateUrl: './statistic-page.component.html',
    styleUrls: ['./statistic-page.component.css'],
    providers: [CoursePageService]
})

export class StatisticPageComponent implements OnInit {
    public isLoginFailed = false;
    private statisticBarFormat: MoodleCourseStatisticBar[];
    public moodleAllCoursesData: MoodleCourseSection[];
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;

    public currentRate = 8;
    public title = 'D3 Barchart with Angular 10';
    public width: number;
    public height: number;
    public margin = { top: 20, right: 20, bottom: 30, left: 40 };
    public x: any;
    public y: any;
    public svg: any;
    public g: any;

    constructor(private coursePageService: CoursePageService, private route: ActivatedRoute) {
        console.log("constructor of StatisticPageComponent");
        this.width = 900 - this.margin.left - this.margin.right;
        this.height = 500 - this.margin.top - this.margin.bottom;
    }

    ngOnInit() {
        this.getAllCourses().subscribe(data => {
            this.moodleAllCoursesData = data;
            });
    }

    ngAfterViewInit(): void {
        this.paginator = this.paginator;
        this.sort = this.sort;
    }

    getAllCourses() {
        return this.coursePageService.getAllCourses();
    }

    updateCourses() {
            this.coursePageService.updateCourses().subscribe(() => {
                this.getAllCourses().subscribe(data => {
                    this.moodleAllCoursesData = data;
                });
            });
    }

    changeCourse(event: MatOptionSelectionChange, selectedCourseId:string, selectedCourseName:string) {
        this.clearSvg();
        this.coursePageService.getCourseStatistics(selectedCourseId).subscribe(data => {
            this.statisticBarFormat = [];
            this.transformStatisticsIntoBarFormat(data);
            this.initSvg();
            this.initAxis();
            this.drawAxis();
            this.drawBars();
        });
    }

    clearSvg() {
        d3.selectAll("svg").selectAll("*").remove();
    }

    transformStatisticsIntoBarFormat(data: MoodleCourseStatistic) {
        for (const [key, value] of Object.entries(data)) {
            if (key.startsWith("numberOf")) {
                var statisticBarObj = new MoodleCourseStatisticBar(key.substring("numberOf".length), value);
                this.statisticBarFormat.push(statisticBarObj);
            }
        }
    }


  initSvg() {
    if (!this.svg) {
        this.svg = d3.select('#barChart')
            .append('svg')
            .attr('height', '100%')
            .attr('viewBox', '0 0 900 500')
    }
    this.g = this.svg.append('g')
      .attr('transform', 'translate(' + this.margin.left + ',' + this.margin.top + ')');
  }

  initAxis() {
    this.x = d3Scale.scaleBand().rangeRound([0, this.width]).padding(0.1);
    this.y = d3Scale.scaleLinear().rangeRound([this.height, 0]);
    this.x.domain(this.statisticBarFormat.map((data) => data.axisX));
    this.y.domain([0, d3Array.max(this.statisticBarFormat, (data) => data.axisY)]);
  }

  drawAxis() {
    this.g.append('g')
      .attr('class', 'axis axis--x')
      .attr('transform', 'translate(0,' + this.height + ')')
      .call(d3Axis.axisBottom(this.x));
    this.g.append('g')
      .attr('class', 'axis axis--y')
      .call(d3Axis.axisLeft(this.y))
      .append('text')
      .attr('class', 'axis-title')
      .attr('transform', 'rotate(-90)')
      .attr('y', 6)
      .attr('dy', '0.71em')
      .attr('text-anchor', 'end');
  }

  drawBars() {
    var bar = this.g.selectAll('.bar')
        .data(this.statisticBarFormat)
        .enter()
        .append('g')
        .attr("class","bar")
        .attr('x', (data) => this.x(data.axisX))
        .attr('y', (data) => this.y(data.axisY))
        .attr('width', this.x.bandwidth())
        .attr('height', (data) => this.height - this.y(data.axisY));

    bar.append('rect')
      .attr('class', 'barRect')
      .attr('x', (data) => this.x(data.axisX))
      .attr('y', (data) => this.y(data.axisY))
      .attr('width', this.x.bandwidth())
      .attr('fill', '#498bfc')
      .attr('height', (data) => this.height - this.y(data.axisY));

    // text for the bar
    this.g.selectAll('.bar')
        .append("text")
        .attr("x", (data) => this.x(data.axisX) + (this.x.bandwidth() / 2) )
        .attr("y", (data) => this.y(data.axisY) - 5)
        //.attr("dy", ".35em")
        .attr('fill', 'black')
        .style("font-size", "20px")
        .style("text-anchor", "middle")
        .text(data => data.axisY);
  }

}

export class MoodleCourseStatisticBar {
    axisX: any;
    axisY: any;

    constructor(axisX, axisY) {
        this.axisX = axisX;
        this.axisY = axisY;
    }
}