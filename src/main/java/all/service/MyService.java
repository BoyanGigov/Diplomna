package all.service;

import all.component.diplomna.MoodleApi;
import all.component.diplomna.model.MoodleCourseSectionMO;
import com.google.gson.Gson;
import all.component.diplomna.MyApi;
import all.component.diplomna.model.dto.DataDTO;
import all.component.diplomna.model.DataMO;
import org.glassfish.jersey.client.ClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.StreamingOutput;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@Path("/myService")
public class MyService {

    private static Logger logger = Logger.getLogger(MyService.class);

    @Autowired
    private MyApi myApi;
    @Autowired
    private MoodleApi moodleApi;

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@Context HttpServletRequest request, DataDTO dataDTO) {

        Gson gson = new Gson();
        logger.info("Received save request: " + gson.toJson(dataDTO));

//        DataMO dataMO = myApi.apiOperation(dataDTO);
        moodleApi.updateDataInDB();

//        return Response.ok().entity(gson.toJson(obj)).build();
        return Response.ok("dataMO", MediaType.APPLICATION_JSON).build();
    }

    @Path("/getCourseData")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCouseData(@Context HttpServletRequest request,
                                  @QueryParam("courseId") Long courseId) {

        List<MoodleCourseSectionMO> courseSections = moodleApi.getCourseFilesFromDB(courseId);
        return Response.ok(courseSections, MediaType.APPLICATION_JSON).build();
    }

    @Path("/getCouseFiles/{courseId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCouseFiles(@Context HttpServletRequest request,
                                  @PathParam("courseId") Long courseId) {

        String fileName = "course_"+courseId+".zip";
        return Response.ok(getStreamingOutput(courseId), MediaType.APPLICATION_OCTET_STREAM).header(
                "content-disposition","attachment; filename = " + fileName).build();
    }

    private StreamingOutput getStreamingOutput(Long courseId) {
        return new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                moodleApi.getCourseFilesFromDB(courseId, outputStream);
//                moodleApi.testArchiving(courseId, outputStream);
                outputStream.flush();
            }
        };
    }
}
