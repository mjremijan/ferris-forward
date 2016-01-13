package org.ferris.forward.console.rule;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class InternetAddressAdapter extends XmlAdapter<String, InternetAddress> {

    @Override
    public InternetAddress unmarshal(String v) throws Exception {
        try {
            return new InternetAddress(v);
        } catch (AddressException e) {
            throw new RuntimeException(
                String.format("Problem unmarshalling Internet address \"%s\"", v)
                , e
            );
        }
    }

    @Override
    public String marshal(InternetAddress v) throws Exception {
        if (v == null) {
            return "";
        } else {
            return v.toString();
        }
    }

}
