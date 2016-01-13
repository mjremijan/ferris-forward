package org.ferris.forward.console.rule;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class PatternAdapter extends XmlAdapter<String, Pattern> {

    @Override
    public Pattern unmarshal(String v) throws Exception {
        try {
            return Pattern.compile(v);
        } catch (PatternSyntaxException e) {
            throw new RuntimeException(
                String.format("Problem unmarshalling regular expression \"%s\"", v)
                , e
            );
        }
    }

    @Override
    public String marshal(Pattern v) throws Exception {
        if (v == null) {
            return "";
        } else {
            return v.pattern();
        }
    }

}
