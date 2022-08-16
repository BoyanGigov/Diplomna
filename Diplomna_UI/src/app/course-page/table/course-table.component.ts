import {Component, OnInit, AfterViewInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';

import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatOptionSelectionChange} from '@angular/material/core';
import {MatButtonModule} from '@angular/material/button';

import {CoursePageService, MoodleCourseSection, MoodleModule, MoodleContent, MoodleVO} from '../service/course-service.service';

@Component({
    selector: 'app-course-table',
    templateUrl: './course-table.component.html',
    styleUrls: ['./course-table.component.css'],
    providers: [CoursePageService]
})

export class MoodleCourseTableComponent implements OnInit {
    displayedColumns: string[] = ['sectionName', 'moduleName', 'fileName', 'author'];
    public selectedCourseId;
    public selectedCourseName;
    public moodleAllCoursesData: MoodleCourseSection[];
    public moodleVOsData: MoodleVO[];
    public moodleCourseTableData = new MatTableDataSource<MoodleVO>();
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;

    constructor(private coursePageService: CoursePageService, private route: ActivatedRoute) {
        console.log("constructor of MoodleCourseTableComponent");
    }

    ngOnInit() {
        this.getAllCourses().subscribe(data => {
            this.moodleAllCoursesData = data;
        });
    }

    ngAfterViewInit(): void {
        this.moodleCourseTableData.paginator = this.paginator;
        this.moodleCourseTableData.sort = this.sort;
    }

    getAllCourses() {
        return this.coursePageService.getAllCourses();
    }

    getCourseData(courseId:string) {
        return this.coursePageService.getCourseDataVOs(courseId);
    }

    changeCourse(event: MatOptionSelectionChange, selectedCourseId:number, selectedCourseName:string) {
        this.selectedCourseId = selectedCourseId;
        this.selectedCourseName = selectedCourseName;
        this.getCourseData(this.selectedCourseId).subscribe(data => {
                    this.moodleCourseTableData.data = data;
        });
    }

    downloadCourseFiles() {
        this.coursePageService.downloadCourseFiles(this.selectedCourseId).subscribe(data => {
            const downloadUrl = window.URL.createObjectURL(data);
            var anchor = document.createElement("a"); // "a" for an anchor element
            anchor.download = this.selectedCourseName + ".zip";
            anchor.href = downloadUrl;
            anchor.click();
            window.URL.revokeObjectURL(downloadUrl);
        });
    }

    updateCourses() {
        this.coursePageService.updateCourses().subscribe(() => {
            this.getAllCourses().subscribe(data => {
                this.moodleAllCoursesData = data;
            });
        });
    }
}