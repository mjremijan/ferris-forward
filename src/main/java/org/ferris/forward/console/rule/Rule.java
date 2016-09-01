package org.ferris.forward.console.rule;

import java.util.regex.Pattern;
import javax.mail.internet.InternetAddress;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.ferris.forward.console.email.EmailMessage;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@XmlRootElement(name="Rule")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rule {
    
    @XmlElement(name = "ForwardAddress", required = true)  
    @XmlJavaTypeAdapter(InternetAddressAdapter.class)
    private InternetAddress to;
    
    @XmlElement(name = "FromPattern")
    @XmlJavaTypeAdapter(PatternAdapter.class)
    private Pattern from;
    
    @XmlElement(name = "SubjectPattern")
    @XmlJavaTypeAdapter(PatternAdapter.class)
    private Pattern subject;

    @Override
    public String toString() {
        return String.format(
            "[Rule: to=\"%s\" from=\"%s\" subject=\"%s\"]"
            , (to == null) ? "<NULL>" : to.toString()
            , (from == null) ? "<NULL>" : from.pattern()
            , (subject == null) ? "<NULL>" : subject.pattern()
        );        
    }
    
    public InternetAddress getTo() {
        return to;
    }

    public void setTo(InternetAddress to) {
        this.to = to;
    }
    
    public Pattern getFrom() {
        return from;
    }

    public void setFrom(Pattern from) {
        this.from = from;
    }
    
    public Pattern getSubject() {
        return subject;
    }

    public void setSubject(Pattern subject) {
        this.subject = subject;
    }
    
    public boolean matches(EmailMessage message) {
        boolean fromMatches = true;
        if (getFrom() != null) {
            fromMatches
                = getFrom().matcher(message.getFrom().toString()).matches();
        }
        
        boolean subjectMatches = true;
        if (getSubject()!= null) {
            subjectMatches
                = getSubject().matcher(message.getSubject()).matches();
        }
        
        return fromMatches && subjectMatches;
    }
}
