package all.component.diplomna.api;

import all.component.diplomna.converter.MoodleConverter;
import all.component.diplomna.converter.MoodleFileArchiver;
import all.component.diplomna.dao.interfaces.MoodleModuleDao;
import all.component.diplomna.dao.interfaces.MoodleCourseStatisticsDao;
import all.component.diplomna.dao.interfaces.MoodleContentDao;
import all.component.diplomna.dao.interfaces.MoodleCourseSectionDao;
import all.component.diplomna.model.MoodleCourseSectionMO;
import all.component.diplomna.model.MoodleCourseStatisticsMO;
import all.component.diplomna.model.MoodleModuleMO;
import all.component.diplomna.model.dto.MoodleCourseInfoDTO;
import all.component.diplomna.model.dto.MoodleCourseSectionDTO;
import all.component.diplomna.rest.MoodleRestClient;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MoodleApiImpl implements MoodleApi {

    private static final Logger logger = LogManager.getLogger(MoodleApiImpl.class);
    private static final String FILE = "file";

    @Autowired
    private MoodleCourseSectionDao moodleCourseSectionDao;
    @Autowired
    private MoodleModuleDao moodleModuleDao;
    @Autowired
    private MoodleContentDao moodleContentDao;
    @Autowired
    private MoodleCourseStatisticsDao moodleCourseStatisticsDao;
    @Autowired
    private MoodleRestClient moodleRestClient;
    @Autowired
    private MoodleConverter moodleConverter;

    @Override
    public List<MoodleCourseSectionMO> getAllCourses() {
        return moodleCourseSectionDao.getAllCourses();
    }

    @Override
    public List<MoodleCourseSectionMO> getArchivedCourseFilesFromMoodle(Long courseId) {
        return moodleCourseSectionDao.getAllCourseSectionsByCourseId(courseId);
    }

    @Override
    public MoodleCourseStatisticsMO getCourseStatistics(Long courseId) {
        return moodleCourseStatisticsDao.getCourseStatisticsByCourseId(courseId);
    }

    @Override
    public void getArchivedCourseFilesFromMoodle(Long courseId, OutputStream outputStream) {
        List<MoodleCourseSectionMO> sections = moodleCourseSectionDao.getAllCourseSectionsByCourseId(courseId);
        archiveCourseSectionFiles(sections, outputStream);
    }

    @Override
    public void testArchiving(Long courseId, OutputStream outputStream) {
        MoodleCourseSectionDTO[] courseSections = moodleRestClient.getCourseContents(courseId);
        MoodleCourseSectionMO sectionMO = moodleConverter.convertDtoToMo(courseSections[1], courseId, "testDisplayName");
        archiveCourseSectionFiles(Collections.singletonList(sectionMO), outputStream);
    }

    @Transactional("diplomnaTransactionManager")
    @Override
    public void updateDB() {
        moodleContentDao.deleteAllContents();
        moodleModuleDao.deleteAllModules();
        moodleCourseSectionDao.deleteAllSections();
        moodleCourseStatisticsDao.deleteAllStatistics();

        MoodleCourseInfoDTO[] courseInfos = moodleRestClient.getCourses();
        for (MoodleCourseInfoDTO courseInfo : courseInfos) {
            MoodleCourseSectionDTO[] courseSections = moodleRestClient.getCourseContents(courseInfo.getId());
            for (MoodleCourseSectionDTO section : courseSections) {
                MoodleCourseSectionMO sectionMO = moodleConverter.convertDtoToMo(section, courseInfo.getId(), courseInfo.getDisplayname());
                moodleCourseSectionDao.saveCourseSection(sectionMO);
            }

            MoodleCourseStatisticsMO statisticsMO = generateStatistics(courseInfo.getId(), courseInfo.getDisplayname(), courseSections);
            moodleCourseStatisticsDao.saveCourseStatistics(statisticsMO);
        }
    }

    private void archiveCourseSectionFiles(List<MoodleCourseSectionMO> sections, OutputStream outputStream) {
        List<File> fileList = new ArrayList<>();

        for (MoodleCourseSectionMO section : sections) {
            downloadModules(section, fileList);
        }

        try {
            MoodleFileArchiver.compressFilesToZip(fileList, outputStream);
        } finally {
            cleanTempFiles(fileList);
        }
    }

    private void downloadModules(MoodleCourseSectionMO section, List<File> fileList) {
        section.getModules().forEach(module -> {
//            String directoryName = getDirectoryName(module, directoryList);
            downloadFiles(module, fileList);
        });
    }

//    private String getDirectoryName(MoodleModuleMO module, List<File> directoryList) {
//        File directory = new File("temp\\module_" + module.getId());
//        if (directory.mkdir()) {
//            directoryList.add(directory); // add to list of directories to be deleted
//            return directory.getName() + "\\";
//        } else {
//            logger.warn("Failed to make directory " + directory.getAbsolutePath());
//            return ""; // failed to create directory
//        }
//    }

    private void downloadFiles(MoodleModuleMO module, List<File> fileList) {
        module.getContents().forEach(moduleContent -> {
            if (moduleContent.getType().equalsIgnoreCase(FILE)) {
                File file = moodleRestClient.getFile(moduleContent.getFileurl(), moduleContent.getFileName());
                if (file != null) {
                    fileList.add(file);
                }
            }
        });
    }

    private void cleanTempFiles(List<File> fileList) {
        try {
            fileList.forEach(file -> {
                if (!file.delete()) {
                    logger.warn("failed to delete file: " + file.getAbsolutePath());
                    file.deleteOnExit(); // attempt to have Java delete it automatically once JVM is stopped
                }
            });
        } catch (Exception e) {
            logger.error("failed to delete downloaded files", e);
        }
//        try {
//            directoryList.forEach(directory -> {
//                if (!directory.delete()) {
//                    logger.warn("failed to delete file: " + directory.getAbsolutePath());
//                    directory.deleteOnExit(); // attempt to have Java delete it automatically once JVM is stopped
//                }
//            });
//        } catch (Exception e) {
//            logger.error("failed to delete download directories", e);
//        }
    }

    public MoodleCourseStatisticsMO generateStatistics(long courseId, String courseDisplayName, MoodleCourseSectionDTO[] courseSections) {
        MoodleCourseStatisticsMO mo = new MoodleCourseStatisticsMO();
        mo.setCourseId(courseId);
        mo.setCourseName(courseDisplayName);

        AtomicInteger fileCount = new AtomicInteger();
        AtomicInteger urlCount = new AtomicInteger();
        AtomicInteger discussionsCount = new AtomicInteger();
        Arrays.stream(courseSections).forEach(section ->
                Arrays.stream(section.getModules()).forEach(module ->
                        Arrays.stream(module.getContents()).forEach(content -> {
                                    if ("url".equalsIgnoreCase(content.getType())) {
                                        urlCount.getAndIncrement();
                                    } else if ("file".equalsIgnoreCase(content.getType())) {
                                        fileCount.getAndIncrement();
                                    }
                                }
                        )
                )
        );
        Arrays.stream(moodleRestClient.getForums(courseId)).forEach(forum ->
                discussionsCount.addAndGet(forum.getNumdiscussions())
        );
        mo.setNumberOfFiles(fileCount.get());
        mo.setNumberOfUrls(urlCount.get());
        mo.setNumberOfResources(moodleRestClient.getResources(courseId).length);
        mo.setNumberOfWikis(moodleRestClient.getWikis(courseId).length);
        mo.setNumberOfForumDiscussions(discussionsCount.get());

        return mo;
    }

}
