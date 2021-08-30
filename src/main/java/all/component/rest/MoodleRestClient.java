package all.component.rest;

import all.component.diplomna.model.dto.MoodleCourseInfoDTO;
import all.component.diplomna.model.dto.MoodleCourseSectionDTO;
import org.apache.log4j.Logger;
import org.glassfish.jersey.jackson1.Jackson1Feature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Component
public class MoodleRestClient {

    private static Logger logger = Logger.getLogger(MoodleRestClient.class);

    private static final String baseUrl = "https://aptitude-learn.w3c.fmi.uni-sofia.bg";
    private static final String restApiUrl = "/webservice/rest/server.php";
    private static final String wsToken = "?wstoken=92b109d211f2b52d15d042b4f925f437";
    private static final String formatType = "&moodlewsrestformat=json";
    private static final String url = baseUrl + restApiUrl + wsToken + formatType;

    private Client client;

    // https://aptitude-learn.w3c.fmi.uni-sofia.bg/webservice/rest/server.php?wstoken=92b109d211f2b52d15d042b4f925f437&moodlewsrestformat=json&wsfunction=core_calendar_get_action_events_by_courses&courseids[0]=2
    // https://aptitude-learn.w3c.fmi.uni-sofia.bg/webservice/rest/server.php?wstoken=92b109d211f2b52d15d042b4f925f437&moodlewsrestformat=json&wsfunction=core_course_get_contents&courseid=2
    MoodleRestClient() {
        SSLContext sslcontext;
        try {
        sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {}
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
        }}, new java.security.SecureRandom());
        } catch (Exception e) {
            logger.error("Failed to create client that ignores certificate exception: ", e);
            client = ClientBuilder.newBuilder().register(Jackson1Feature.class).build();
            return;
        }
        client = ClientBuilder.newBuilder().sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).register(Jackson1Feature.class).build();
    }

    private Invocation.Builder buildRequest(String path, MediaType requestType, MediaType accept) {
        String requestUrl = url + path;
        return client.target(requestUrl).request(requestType).accept(accept);
    }

    private Invocation.Builder buildDownloadRequest(String url, MediaType requestType, MediaType accept) {
        String requestUrl = url + wsToken.replaceFirst("\\?ws", "&");
        return client.target(requestUrl).request(requestType).accept(accept);
    }

    public MoodleCourseInfoDTO[] getCourses() {
        Response response = buildRequest("&wsfunction=core_course_get_courses", MediaType.TEXT_PLAIN_TYPE, MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        if (response.getStatus() == HttpStatus.OK.value()) {
            return response.readEntity(MoodleCourseInfoDTO[].class);
        } else {
//            String responseString = response.readEntity(String.class);
            logger.error("getCourses response: " + response.toString());
//            logger.error("getCourses response: " + response.getStatus() + " " + responseString);
            return null;
        }
    }

    public MoodleCourseSectionDTO[] getCourseContents(int courseId) {
        Response response = buildRequest("&wsfunction=core_course_get_contents&courseid=" + courseId, MediaType.TEXT_PLAIN_TYPE, MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        if (response.getStatus() == HttpStatus.OK.value()) {
            return response.readEntity(MoodleCourseSectionDTO[].class);
        } else {
            logger.error("getCourseContents response: " + response.toString());
            return null;
        }
    }

    public File buildGetFileRequest(String fileUrl, String fileName) {
        Response response = buildDownloadRequest(fileUrl, MediaType.TEXT_PLAIN_TYPE, MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        if (response.getStatus() == HttpStatus.OK.value()) {
            File downloadedFile = response.readEntity(File.class);
            String absPath = downloadedFile.getAbsolutePath();
            boolean success = downloadedFile.renameTo(new File(absPath.replace(absPath.substring(absPath.lastIndexOf('\\')+1), fileName)));
            if (!success) {
                logger.error("failed to rename file " + downloadedFile.getAbsolutePath());
            }
            return downloadedFile;
        } else {
            logger.error("buildGetFileRequest response: " + response.toString());
            return null;
        }
    }

}
