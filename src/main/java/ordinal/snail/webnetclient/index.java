package ordinal.snail.webnetclient;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author d_mokharev
 */
@Named
@SessionScoped
public class index implements Serializable {

    private static final long serialVersionUID = -4574156835527730530L;

    public static final String HOST = "http://***.***.***.***:8080";
    public static final String SERVICE = "service/method";

    private String out;
    private String ip = "";
    private String url;

//<editor-fold defaultstate="collapsed" desc="getters and setters">
    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
//</editor-fold>

    public void post() throws Exception {
        call();
    }

    public void call() throws Exception {
        String halfUrl = HOST + SERVICE;

        String idUser = "id_user=" + "1";
        String xml = "<xml>";
        String xmlParam = "xml=" + xml;
        String params = idUser + "&" + xmlParam;
        DateAdapter adapter = new DateAdapter();
        params = params + "&date_s_begin=" + adapter.marshal(new Date());
        url = halfUrl + "/" + params;
        System.out.println(url);
        String xmlOut = CallWebService.instance().post(halfUrl, params);
        System.out.println(xmlOut);
        out = xmlOut;
    }

}
