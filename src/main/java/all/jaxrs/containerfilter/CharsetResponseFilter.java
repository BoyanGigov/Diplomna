package all.jaxrs.containerfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;

public class CharsetResponseFilter implements ContainerResponseFilter {

    private static Logger logger = LoggerFactory.getLogger(CharsetResponseFilter.class);
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CHARSET = "charset";

// jersey 1.x version
//    @Override
//    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
//        MediaType mediaType = response.getMediaType();
//        if (mediaType != null) {
//            String contentType = mediaType.toString();
//            if (!contentType.contains(CHARSET)) {
//                contentType = contentType + ";charset=utf-8";
//                contentType = contentType + ";" + CHARSET + "=utf-8"; // TODO: is above line neceessary?
//                response.getHttpHeaders().putSingle(CONTENT_TYPE, contentType);
//                logger.info("Jersey CharsetResponseFilter set response charset to utf-8");
//            }
//        }
//        return response;
//    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) {
        MediaType mediaType = containerResponseContext.getMediaType();
        if (mediaType != null) {
            String contentType = mediaType.toString();
            if (!contentType.contains(CHARSET)) {
                contentType = contentType + ";charset=utf-8";
                contentType = contentType + ";" + CHARSET + "=utf-8"; // TODO: is above line neceessary?
                containerResponseContext.getHeaders().putSingle(CONTENT_TYPE, contentType);
                logger.info("Jersey CharsetResponseFilter set response charset to utf-8");
            }
        }
//        return containerResponseContext;
    }
}
