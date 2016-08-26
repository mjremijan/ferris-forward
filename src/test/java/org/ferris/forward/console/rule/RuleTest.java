package org.ferris.forward.console.rule;

import java.util.regex.Pattern;
import javax.mail.internet.InternetAddress;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RuleTest {

    public RuleTest() {
    }

    @Test
    public void objectAsStringDefaultConstructor() throws Exception {
        Rule rule = new Rule();

        Pattern from = Pattern.compile("from pattern");
        Pattern subject = Pattern.compile("subject pattern");
        InternetAddress to = new InternetAddress("Rita Red <r.red@junit.org>");

        rule.setFrom(from);
        rule.setSubject(subject);
        rule.setTo(to);

        String actual
            = rule.toString();

        Assert.assertEquals("[Rule: to=\"Rita Red <r.red@junit.org>\" from=\"from pattern\" subject=\"subject pattern\" delete=\"true\"]", actual);

    }

    @Test
    public void objectAsString() throws Exception {
        Rule rule = new Rule();

        Pattern from = Pattern.compile("from pattern");
        Pattern subject = Pattern.compile("subject pattern");
        InternetAddress to = new InternetAddress("Rita Red <r.red@junit.org>");

        rule.setFrom(from);
        rule.setSubject(subject);
        rule.setTo(to);
        rule.setDelete(Boolean.FALSE);

        String actual
            = rule.toString();

        Assert.assertEquals("[Rule: to=\"Rita Red <r.red@junit.org>\" from=\"from pattern\" subject=\"subject pattern\" delete=\"false\"]", actual);

    }

    @Test
    public void stringLiteral() {
        Assert.assertFalse("0".equals(null));
        Assert.assertFalse("0".equals("foo"));
        Assert.assertTrue("0".equals("0"));
    }

}
