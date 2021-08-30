package all.component.diplomna;

import all.component.diplomna.converter.MoodleConverter;
import all.component.diplomna.converter.MoodleFileArchiver;
import all.component.diplomna.dao.interfaces.MoodleCourseInfoDao;
import all.component.diplomna.dao.interfaces.MoodleCourseStatisticsDao;
import all.component.diplomna.dao.interfaces.MoodleActivityDao;
import all.component.diplomna.dao.interfaces.MoodleCourseDao;
import all.component.diplomna.model.MoodleCourseSectionMO;
import all.component.diplomna.model.dto.MoodleCourseInfoDTO;
import all.component.diplomna.model.dto.MoodleCourseSectionDTO;
import all.component.rest.MoodleRestClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class MoodleApiImpl implements MoodleApi {

    private static Logger logger = Logger.getLogger(MoodleApiImpl.class);
    private static final String FILE = "file";

    @Autowired
    private MoodleCourseDao moodleCourseDao;
    @Autowired
    private MoodleCourseInfoDao moodleCourseInfoDao;
    @Autowired
    private MoodleActivityDao moodleActivityDao;
    @Autowired
    private MoodleCourseStatisticsDao moodleCourseStatisticsDao;
    @Autowired
    private MoodleRestClient moodleRestClient;
    @Autowired
    private MoodleConverter moodleConverter;

    @Override
    public void updateDataInDB() {
        MoodleCourseInfoDTO[] courseInfos = moodleRestClient.getCourses();
        for (MoodleCourseInfoDTO courseInfo : courseInfos) {
            MoodleCourseSectionDTO[] courseSections = moodleRestClient.getCourseContents(courseInfo.getId());
            for (MoodleCourseSectionDTO section : courseSections) {
                MoodleCourseSectionMO sectionMO = moodleConverter.convertDtoToMo(section, courseInfo.getId(), courseInfo.getDisplayname());
//                moodleCourseDao.saveCourse(courseMO);
//                archiveCourseSectionFiles(sectionMO);
            }
        }
    }

    @Override
    public void getCourseFilesFromDB(int courseId, OutputStream outputStream) {
        List<MoodleCourseSectionMO> sections = moodleCourseDao.getCoursesByCourseId(courseId);
        sections.forEach(section -> archiveCourseSectionFiles(section, outputStream));

    }

    private void archiveCourseSectionFiles(MoodleCourseSectionMO sectionMO, OutputStream outputStream) {
        List<File> fileList = new ArrayList<>();
        sectionMO.getModules().forEach(module ->
                module.getContents().forEach(moduleContent -> {
                    if (moduleContent.getType().equalsIgnoreCase(FILE)) {
                        fileList.add(moodleRestClient.buildGetFileRequest(moduleContent.getFileurl(), moduleContent.getFileName()));
                    }
                }));
        MoodleFileArchiver.compressFilesToZip(fileList, outputStream);
    }

}
