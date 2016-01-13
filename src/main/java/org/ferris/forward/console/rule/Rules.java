package org.ferris.forward.console.rule;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@XmlRootElement(name="Rules")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rules {
    
    @XmlElement(name="Rule")
    private List<Rule> rules = null;

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
