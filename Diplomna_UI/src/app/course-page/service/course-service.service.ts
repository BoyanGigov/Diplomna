import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {MatTableDataSource} from '@angular/material/table';

@Injectable()
export class CoursePageService {

    private baseConfig = { withCredentials: true, headers: { 'Content-Type': 'application/json;charset=utf-8;'} };
    base_url: string = window.location.protocol + "//" + window.location.hostname + ":8080" + "/diplomna-webapp/myService";

    constructor(private httpClient: HttpClient) {
        console.log("service constructor");
        console.log("this.base_url " + this.base_url);

    }

    getAllCourses(): Observable<MoodleCourseSection[]> {
        let getAllCoursesUrl: string = this.base_url + "/getAllCourses";
        console.log("getAllCourses to: " + getAllCoursesUrl);
        return this.httpClient.get<MoodleCourseSection[]>(getAllCoursesUrl, this.baseConfig);
    }

    getCourseData(courseId:string): Observable<MoodleCourseSection[]> {
        let getCourseDataUrl: string = this.base_url + "/getCourseData/?courseId="+courseId;
        console.log("getCourseData to: " + getCourseDataUrl);
        return this.httpClient.get<MoodleCourseSection[]>(getCourseDataUrl, this.baseConfig);
    }

    getCourseStatistics(courseId:string): Observable<MoodleCourseStatistic> {
             let getCourseStatisticsUrl: string = this.base_url + "/getCourseStatistics/?courseId=" + courseId;
             console.log("getStatisticsUrl: " + getCourseStatisticsUrl);
             return this.httpClient.get<MoodleCourseStatistic>(getCourseStatisticsUrl, this.baseConfig);
        }

    updateCourses() {
        let updateCoursesUrl: string = this.base_url + "/updateDB";
        return this.httpClient.patch(updateCoursesUrl, this.baseConfig);
    }

    getCourseDataVOs(courseId:string): Observable<MoodleVO[]> {
        var subject = new Subject<MoodleVO[]>();
        this.getCourseData(courseId).subscribe(moodleCourseSectionData => {
            let convertedData = this.convertToVOs(moodleCourseSectionData);
            subject.next(convertedData);
        });
        return subject.asObservable();
    }

    convertToVOs(moodleCourseSections: MoodleCourseSection[]): MoodleVO[] {
        var moodleVOs: MoodleVO[] = [];
        moodleCourseSections.forEach(moodleCourseSection => {
            moodleCourseSection.modules.forEach(module => {
                module.contents.forEach(content => {
                    let moodleVO: MoodleVO = {
                        sectionName: moodleCourseSection.sectionName,
                        moduleName: module.name,
                        fileName: content.fileName,
                        mimetype: content.mimetype,
                        author: content.author
                    };
                    moodleVOs.push(moodleVO);
                });
            });
        });
        return moodleVOs;
    }

    downloadCourseFiles(courseId:string) {
        let downloadCourseFilesUrl: string = this.base_url + "/downloadCourseFiles/?courseId=" + courseId;
        console.log("downloadCourseFiles request sent to: " + downloadCourseFilesUrl);
        let downloadHeaders = new HttpHeaders({'Content-Type': 'application/octet-stream', 'Accept':'application/octet-stream'});
        let downloadConfig = { headers: downloadHeaders, responseType: 'blob' as const }
        return this.httpClient.get(downloadCourseFilesUrl, downloadConfig);
    }
}

export class MoodleCourseSection {
    id: number;
    modules: MoodleModule[];
    courseId: number;
    sectionId: number;
    courseName: string;
    sectionName: string;
    userVisible: any;

    constructor(id, modules, courseId, sectionId, courseName, sectionName, userVisible) {
        this.id = id || 'N/A';
        this.modules = modules;
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.courseName = courseName;
        this.sectionName = sectionName;
        this.userVisible = userVisible;
    }
}

export class MoodleModule {
    id: number;
    courseSectionId: number;
    contents: MoodleContent[];
    name: string;
    visible: any;
    userVisible: any;

    constructor(id, courseSection, contents, name, visible, userVisible) {
        this.id = id || 'N/A';
        this.courseSectionId = courseSection.id;
        this.contents = contents;
        this.name = name;
        this.visible = visible;
        this.userVisible = userVisible;
    }
}

export class MoodleContent {
    id: number;
    moduleId: number;
    type: string;
    fileName: string;
    fileurl: string;
    mimetype: string;
    author: string;

    constructor(id, module, type, fileName, fileurl, userVisible, mimetype, author) {
        this.id = id || 'N/A';
        this.moduleId = module.id;
        this.type = type;
        this.fileName = fileName;
        this.fileurl = fileurl;
        this.mimetype = mimetype;
        this.author = author;
    }
}

export class MoodleVO {
    sectionName: string;
    moduleName: string;
    fileName: string;
    mimetype: string;
    author: string;

    constructor(sectionName, moduleName, fileName, mimetype, author) {
        this.sectionName = sectionName || 'N/A';
        this.moduleName = moduleName;
        this.fileName = fileName;
        this.mimetype = mimetype;
        this.author = author;
    }
}

export class MoodleCourseStatistic {
    id: number;
    courseId: number;
    courseName: string;
    numberOfFiles: number;
    numberOfUrls: number;
    numberOfResources: number;
    numberOfWikis: number;
    numberOfForumDiscussions: number;

    constructor(id, courseId, courseName, numberOfFiles, numberOfUrls, numberOfResources, numberOfWikis, numberOfForumDiscussions) {
        this.id = id || 'N/A';
        this.courseId = courseId;
        this.courseName = courseName;
        this.numberOfFiles = numberOfFiles;
        this.numberOfUrls = numberOfUrls;
        this.numberOfResources = numberOfResources;
        this.numberOfWikis = numberOfWikis;
        this.numberOfForumDiscussions = numberOfForumDiscussions;
    }
}