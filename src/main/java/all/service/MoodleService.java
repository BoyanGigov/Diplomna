package all.service;

import all.component.diplomna.api.MoodleApi;
import all.component.diplomna.model.MoodleCourseSectionMO;
import all.component.diplomna.model.MoodleCourseStatisticsMO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.StreamingOutput;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@Path("/myService")
public class MoodleService {

    private static Logger logger = LogManager.getLogger(MoodleService.class);

    @Autowired
    private MoodleApi moodleApi;

    @Path("/updateDB")
    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDB(@Context HttpServletRequest request) {

        try {
            moodleApi.updateDB();
            return Response.ok().build();
        } catch (Exception e) {
            return buildServerError("updateDB", e);
        }
    }

    @Path("/getAllCourses")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllCouses(@Context HttpServletRequest request) {

        try {
            List<MoodleCourseSectionMO> courseSections = moodleApi.getAllCourses();
            logSuccess("/getAllCourses", courseSections);
            return Response.ok(courseSections, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return buildServerError("getAllCourses", e);
        }
    }

    @Path("/getCourseData")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCourseData(@Context HttpServletRequest request,
                                  @QueryParam("courseId") Long courseId) {

        try {
            List<MoodleCourseSectionMO> courseSections = moodleApi.getArchivedCourseFilesFromMoodle(courseId);
            return Response.ok(courseSections, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return buildServerError("getCourseData", e);
        }
    }

    @Path("/getCourseStatistics")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCourseStatistics(@Context HttpServletRequest request,
                                 @QueryParam("courseId") Long courseId) {

        try {
            MoodleCourseStatisticsMO courseStatistics = moodleApi.getCourseStatistics(courseId);
            logSuccess("/getCourseStatistics", courseStatistics);
            return Response.ok(courseStatistics, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return buildServerError("getCourseStatistics", e);
        }
    }

    @Path("/downloadCourseFiles")
    @GET
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadCourseFiles(@Context HttpServletRequest request,
                                  @QueryParam("courseId") Long courseId) {

        String fileName = "course_"+courseId+".zip";
        try {
            return Response.ok(getArchivedCourseFilesAsStreamingOutput(courseId), MediaType.APPLICATION_OCTET_STREAM).header(
                    "content-disposition", "attachment; filename = " + fileName).build();
        } catch (Exception e) {
            return buildServerError("downloadCourseFiles", e);
        }
    }

    private Response buildServerError(String requestName, Exception e) {
        logger.error("Internal Server Error (500) in " + requestName, e);
        return Response.serverError().build();
    }

    private StreamingOutput getArchivedCourseFilesAsStreamingOutput(Long courseId) {
        return new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                moodleApi.getArchivedCourseFilesFromMoodle(courseId, outputStream);
//                moodleApi.testArchiving(courseId, outputStream);
                outputStream.flush();
            }
        };
    }

    private void logSuccess(String requestName, Object returnObject) {
        logger.info(requestName + " successfully returned" + new Gson().toJson(returnObject));
    }
}
