package all.component.diplomna.converter;

import all.component.diplomna.model.MoodleContentMO;
import all.component.diplomna.model.MoodleCourseSectionMO;
import all.component.diplomna.model.MoodleCourseStatisticsMO;
import all.component.diplomna.model.MoodleModuleMO;
import all.component.diplomna.model.dto.MoodleContentDTO;
import all.component.diplomna.model.dto.MoodleCourseSectionDTO;
import all.component.diplomna.model.dto.MoodleModuleDTO;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MoodleConverter {

    private static Logger logger = LogManager.getLogger(MoodleConverter.class);

    public MoodleCourseSectionMO convertDtoToMo(MoodleCourseSectionDTO section, Long courseId, String courseDisplayName) {
        MoodleCourseSectionMO mo = new MoodleCourseSectionMO();
        mo.setCourseId(courseId);
        mo.setSectionId(section.getId());
        mo.setCourseName(courseDisplayName);
        mo.setSectionName(section.getName());
        mo.setUservisible(section.getUservisible());
        if (section.getModules() != null) {
            Arrays.stream(section.getModules()).forEach(moduleDTO -> mo.addMoudle(convertDtoToMo(moduleDTO, mo)));
        }
        return mo;
    }

    public MoodleModuleMO convertDtoToMo(MoodleModuleDTO moduleDTO, MoodleCourseSectionMO sectionMO) {
        MoodleModuleMO mo = new MoodleModuleMO();
        mo.setName(moduleDTO.getName());
        mo.setVisible(moduleDTO.getVisible());
        mo.setUserVisible(moduleDTO.isUserVisible());
        mo.setCourseSection(sectionMO);
        mo.setModName(moduleDTO.getModname());
        if (moduleDTO.getContents() != null) {
            Arrays.stream(moduleDTO.getContents()).forEach(contentDTO -> mo.addContent(convertDtoToMo(contentDTO, mo)));
        }
        return mo;
    }

    public MoodleContentMO convertDtoToMo(MoodleContentDTO contentDTO, MoodleModuleMO moduleMO) {
        MoodleContentMO mo = new MoodleContentMO();
        mo.setType(contentDTO.getType());
        mo.setFileName(contentDTO.getFilename());
        mo.setFileurl(contentDTO.getFileurl());
        mo.setMimetype(contentDTO.getMimetype());
        mo.setAuthor(contentDTO.getAuthor());
        mo.setModule(moduleMO);
        return mo;
    }
}
