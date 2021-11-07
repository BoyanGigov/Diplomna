package all.component.diplomna;

import all.component.diplomna.converter.MoodleConverter;
import all.component.diplomna.converter.MoodleFileArchiver;
import all.component.diplomna.dao.interfaces.MoodleModuleDao;
import all.component.diplomna.dao.interfaces.MoodleCourseStatisticsDao;
import all.component.diplomna.dao.interfaces.MoodleActivityDao;
import all.component.diplomna.dao.interfaces.MoodleCourseSectionDao;
import all.component.diplomna.model.MoodleCourseSectionMO;
import all.component.diplomna.model.MoodleModuleMO;
import all.component.diplomna.model.dto.MoodleCourseInfoDTO;
import all.component.diplomna.model.dto.MoodleCourseSectionDTO;
import all.component.rest.MoodleRestClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MoodleApiImpl implements MoodleApi {

    private static Logger logger = Logger.getLogger(MoodleApiImpl.class);
    private static final String FILE = "file";

    @Autowired
    private MoodleCourseSectionDao moodleCourseSectionDao;
    @Autowired
    private MoodleModuleDao moodleModuleDao;
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
                moodleCourseSectionDao.saveCourseSection(sectionMO);
            }
        }
    }

    @Override
    public List<MoodleCourseSectionMO> getCourseFilesFromDB(Long courseId) {
        return moodleCourseSectionDao.getAllCourseSectionsByCourseId(courseId);
    }

    @Override
    public void getCourseFilesFromDB(Long courseId, OutputStream outputStream) {
        List<MoodleCourseSectionMO> sections = moodleCourseSectionDao.getAllCourseSectionsByCourseId(courseId);
        for (MoodleCourseSectionMO section : sections) {
            archiveCourseSectionFiles(section, outputStream);
        }

    }

    private void archiveCourseSectionFiles(MoodleCourseSectionMO section, OutputStream outputStream) {
        List<File> directoryList = new ArrayList<>();
        Map<String, List<File>> fileMap = new HashMap<>();

        downloadModules(section, directoryList, fileMap);
        MoodleFileArchiver.compressFilesToZip(fileMap, outputStream);
        cleanTempFiles(directoryList, fileMap);
    }

    @Override
    public void testArchiving(Long courseId, OutputStream outputStream) {
        MoodleCourseSectionDTO[] courseSections = moodleRestClient.getCourseContents(courseId);
        MoodleCourseSectionMO sectionMO = moodleConverter.convertDtoToMo(courseSections[1], courseId, "testDisplayName");
        archiveCourseSectionFiles(sectionMO, outputStream);
    }

    private void downloadModules(MoodleCourseSectionMO section, List<File> directoryList, Map<String, List<File>> fileMap) {
        section.getModules().forEach(module -> {
            String directoryName = getDirectoryName(module, directoryList);
            downloadFiles(module, directoryName, fileMap);
        });
    }

    private String getDirectoryName(MoodleModuleMO module, List<File> directoryList) {
        File directory = new File("temp\\module_"+module.getId());
        if (directory.mkdir()) {
            directoryList.add(directory); // add to list of directories to be deleted
            return directory.getName() + "\\";
        } else {
            logger.warn("Failed to make directory " + directory.getAbsolutePath());
            return ""; // failed to create directory
        }
    }

    private void downloadFiles(MoodleModuleMO module, String directoryName, Map<String, List<File>> fileMap) {
        List<File> fileList = new ArrayList<>();
        module.getContents().forEach(moduleContent -> {
            if (moduleContent.getType().equalsIgnoreCase(FILE)) {
                File file = moodleRestClient.buildGetFileRequest(moduleContent.getFileurl(), directoryName + moduleContent.getFileName());
                if (file != null) {
                    fileList.add(file);
                }
            }
        });
        fileMap.put(directoryName, fileList);
    }

    private void cleanTempFiles(List<File> directoryList, Map<String, List<File>> fileMap) {
        try {
            fileMap.values().forEach(fileList -> fileList.forEach(file -> {
                if (!file.delete()) {
                    logger.warn("failed to delete file: " + file.getAbsolutePath());
                    file.deleteOnExit(); // attempt to have Java delete it automatically once JVM is stopped
                }
            }));
        } catch (Exception e) {
            logger.error("failed to delete downloaded files", e);
        }
        try {
            directoryList.forEach(directory -> {
                if (!directory.delete()) {
                    logger.warn("failed to delete file: " + directory.getAbsolutePath());
                    directory.deleteOnExit(); // attempt to have Java delete it automatically once JVM is stopped
                }
            });
        } catch (Exception e) {
            logger.error("failed to delete download directories", e);
        }
    }

}
