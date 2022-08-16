package all.component.diplomna.rest;

import all.component.diplomna.model.dto.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
//import org.glassfish.jersey.jackson1.Jackson1Feature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.security.cert.X509Certificate;

@Component
public class MoodleRestClient {

    private static Logger logger = LogManager.getLogger(MoodleRestClient.class);

    private static final String baseUrl = "https://aptitude-learn.w3c.fmi.uni-sofia.bg";
    private static final String loginUrl = "/login/token.php?service=moodle_mobile_app";
    private static final String restApiUrl = "/webservice/rest/server.php";
    private static String wsToken; // wsTokens expires after some time(days?), all downloaded files are 1KB with expired token
    private static final String formatType = "&moodlewsrestformat=json";
    private static String url;

    private Client client;

    // https://aptitude-learn.w3c.fmi.uni-sofia.bg/webservice/rest/server.php?wstoken=92b109d211f2b52d15d042b4f925f437&moodlewsrestformat=json&wsfunction=core_calendar_get_action_events_by_courses&courseids[0]=2
    // https://aptitude-learn.w3c.fmi.uni-sofia.bg/webservice/rest/server.php?wstoken=92b109d211f2b52d15d042b4f925f437&moodlewsrestformat=json&wsfunction=core_course_get_contents&courseid=2

    MoodleRestClient() {
        buildClient();
        getWsToken();
        setUrl();
    }

    private void buildClient() {
        SSLContext sslcontext;
        try {
            sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) {}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}
                public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
            }}, new java.security.SecureRandom());
        } catch (Exception e) {
            logger.error("Failed to create client that ignores certificate exception. This may lead to failed requests if certificate isn't configured in some other way. ", e);
            client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
            return;
        }
        client = ClientBuilder.newBuilder().sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).register(JacksonFeature.class).build();
    }

    private void getWsToken() {
        Invocation.Builder builder = client.target(baseUrl + loginUrl).request(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON);
        final String username = "boyan_gigov";
        final String password = "Boyan_gigov@123";
        MoodleWsTokenDTO wsTokenDTO = builder.post(Entity.entity("username=" + username + "&password=" + password, MediaType.APPLICATION_FORM_URLENCODED), MoodleWsTokenDTO.class);
        wsToken = wsTokenDTO.getToken();
    }

    private void setUrl() {
        url = baseUrl + restApiUrl + "?wstoken=" + wsToken + formatType;
    }

    private Invocation.Builder buildRequest(String path, MediaType requestType, MediaType acceptType) {
        String requestUrl = url + path;
        return client.target(requestUrl).request(requestType).accept(acceptType);
    }

    private Invocation.Builder buildDownloadRequest(String downloadUrl, MediaType requestType, MediaType acceptType) {
        String requestUrl = downloadUrl + "&token=" + wsToken;
        return client.target(requestUrl).request(requestType).accept(acceptType);
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

    public MoodleCourseSectionDTO[] getCourseContents(Long courseId) {
        Response response = buildRequest("&wsfunction=core_course_get_contents&courseid=" + courseId, MediaType.TEXT_PLAIN_TYPE, MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        if (response.getStatus() == HttpStatus.OK.value()) {
            return response.readEntity(MoodleCourseSectionDTO[].class);
        } else {
            logger.error("getCourseContents response: " + response.toString());
            return null;
        }
    }

    public File getFile(String fileUrl, String fileName) {
        Response response = buildDownloadRequest(fileUrl, MediaType.TEXT_PLAIN_TYPE, MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        if (response.getStatus() == HttpStatus.OK.value()) {
            File downloadedFile = response.readEntity(File.class);
            String absPath = downloadedFile.getAbsolutePath();
            String renamedFilePath = absPath.replace(absPath.substring(absPath.lastIndexOf('\\')+1), fileName);
            if (downloadedFile.renameTo(new File(renamedFilePath))) {
                downloadedFile = new File(renamedFilePath);
            } else {
                logger.warn("failed to rename file " + downloadedFile.getAbsolutePath() + " to " + renamedFilePath);
                int indexOfExtension = renamedFilePath.lastIndexOf(".");
                String fileExtension = renamedFilePath.substring(indexOfExtension);
                for (int attempt = 1; attempt < 20; attempt++) {
                    String renamedFilePathAttepmt = renamedFilePath.substring(0, indexOfExtension) + "(" + attempt + ")" + fileExtension;
                    logger.info("retrying to rename file " + downloadedFile.getAbsolutePath() + " to " + renamedFilePathAttepmt);
                    if (downloadedFile.renameTo(new File(renamedFilePathAttepmt))) {
                        downloadedFile = new File(renamedFilePathAttepmt);
                        return downloadedFile;
                    }
                }
                logger.error("Failed to properly name the file for " + fileName);
            }
            return downloadedFile;
        } else {
            logger.error("buildGetFileRequest response: " + response.toString());
            return null;
        }
    }

    public MoodleForumDTO[] getForums(long courseId) {
        // this is working on the presumption that each forum id is equal to its course id, which was the case with test data
        Response response = buildRequest("&wsfunction=mod_forum_get_forums_by_courses&courseids[0]=" + courseId, MediaType.TEXT_PLAIN_TYPE, MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        if (response.getStatus() == HttpStatus.OK.value()) {
            return response.readEntity(MoodleForumDTO[].class);
        } else {
            logger.error("getForums response: " + response.toString());
            return null;
        }
    }

    public MoodleResourceDTO[] getResources(long courseId) {
        Response response = buildRequest("&wsfunction=mod_resource_get_resources_by_courses&courseids[0]=" + courseId, MediaType.TEXT_PLAIN_TYPE, MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        if (response.getStatus() == HttpStatus.OK.value()) {
            return response.readEntity(MoodleResourceArrayDTO.class).getResources();
        } else {
            logger.error("getForums response: " + response.toString());
            return new MoodleResourceDTO[0];
        }
    }

    public MoodleWikiDTO[] getWikis(long courseId) {
        Response response = buildRequest("&wsfunction=mod_wiki_get_wikis_by_courses&courseids[0]=" + courseId, MediaType.TEXT_PLAIN_TYPE, MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        if (response.getStatus() == HttpStatus.OK.value()) {
            return response.readEntity(MoodleWikiArrayDTO.class).getWikis();
        } else {
            logger.error("getForums response: " + response.toString());
            return new MoodleWikiDTO[0];
        }
    }

}
