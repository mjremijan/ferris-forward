package org.ferris.forward.console.rule;

import java.util.Collections;
import java.util.List;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import static javax.xml.bind.JAXBContext.newInstance;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import org.ferris.forward.console.email.EmailEvent;
import static org.ferris.forward.console.email.EmailEvent.GET_RULES;
import org.jboss.weld.experimental.Priority;


/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RuleHandler {

    @Inject
    protected Logger log;
    
    @Inject
    protected RulesFile rulesFile;
    
    protected List<Rule> getRules() throws JAXBException {
        log.info("ENTER");
        JAXBContext context
            = newInstance(Rules.class);
        Unmarshaller unmarshaller
            = context.createUnmarshaller();

        log.debug(String.format("Unmarshalling rules file %s", rulesFile.getAbsoluteFile()));
        Rules rules
            = (Rules) unmarshaller.unmarshal(rulesFile);
        
        return (rules.getRules() == null) ? Collections.emptyList() : rules.getRules();
        
    }
    
    protected void loadRules(
        @Observes @Priority(GET_RULES) EmailEvent evnt
    ) throws JAXBException {
        log.info("ENTER");
        evnt.setRules(getRules());
    }
}
