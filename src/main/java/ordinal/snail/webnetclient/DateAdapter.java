package ordinal.snail.webnetclient;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public Date unmarshal(String s) throws Exception {
        return FORMAT.parse(s);
    }

    @Override
    public String marshal(Date date) throws Exception {
        return FORMAT.format(date);
    }
}
