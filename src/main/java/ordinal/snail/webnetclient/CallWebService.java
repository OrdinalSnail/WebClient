package ordinal.snail.webnetclient;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 *
 */
public class CallWebService {

    private final static Logger LOGGER = LogManager.getLogger(CallWebService.class);

    private CallWebService() {
    }

    public static CallWebService instance() {
        return new CallWebService();
    }

    public String get(String url, String param) {
        try {
            WebTarget target = ClientBuilder.newClient().target(url);
            for (Map.Entry<String, String> entry : getRequestParams(param).entrySet()) {
                target = target.queryParam(entry.getKey(), entry.getValue());
            }
            return getAnswerFromResponse(target.request().get());
        } catch (ProcessingException ex) {
            //MessageController.sendMessage(FacesMessage.SEVERITY_ERROR, "Веб сервис не отвечает!", ex.getMessage());
            LOGGER.error(ex);
            return "";
        }
    }

    public String post(String url, String param, Object entity) {
        try {
            WebTarget target = ClientBuilder.newClient().target(url);
            for (Map.Entry<String, String> entry : getRequestParams(param).entrySet()) {
                target = target.queryParam(entry.getKey(), entry.getValue());
            }
            Response response = target.request(MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED)
                .post(Entity.entity(entity, MediaType.TEXT_XML));
            return getAnswerFromResponse(response);
        } catch (ProcessingException ex) {
            //MessageController.sendMessage(FacesMessage.SEVERITY_ERROR, "Веб сервис не отвечает!", ex.getMessage());
            LOGGER.error(ex);
            return "";
        }
    }

    public String post(String url, String param) {
        return post(url, getRequestParams(param));
    }

    public String post(String url, Map<String, String> params) {
        try {
            WebTarget target = ClientBuilder.newClient().target(url);
            MultivaluedMap postParams = new MultivaluedHashMap(params);
            Response response = target.request().post(Entity.form(postParams));
            System.out.println(response.toString());
            return getAnswerFromResponse(response);
        } catch (ProcessingException ex) {
            //MessageController.sendMessage(FacesMessage.SEVERITY_ERROR, "Веб сервис не отвечает!", ex.toString());
            LOGGER.error(ex);
            return "";
        }
    }

    private String getAnswerFromResponse(Response response) {
        switch (Response.Status.fromStatusCode(response.getStatus())) {
            case OK:
                return response.readEntity(String.class);
            default:
                return "Error: " + Response.Status.fromStatusCode(response.getStatus());
        }
    }

    private static Map<String, String> getRequestParams(String params) {
        Map<String, String> result = new HashMap<>();
        String[] paramsAr = params.split("&");
        for (String paramPair : paramsAr) {
            int indEq = paramPair.indexOf("=");
            if (indEq > 0) {
                String key = paramPair.substring(0, indEq);
                String value = paramPair.substring(indEq + 1);
                result.put(key, value);
            }
        }
        return result;
    }

}
