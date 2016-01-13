package org.ferris.forward.console.rule;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.forward.console.email.EmailEvent;
import org.ferris.forward.console.email.EmailMessage;
import org.jboss.weld.experimental.Priority;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RuleMatcher {
    
    @Inject
    protected Logger log;
    
    protected void findMatchesToRules(
        @Observes @Priority(EmailEvent.FIND_MATCHES_TO_RULES) EmailEvent evnt
    ) {
        log.info("ENTER");
        
        Map<Rule, List<EmailMessage>> matches
            = new HashMap<>();
        
        for (Rule rule : evnt.getRules()) {
            matches.put(rule, new LinkedList<>());
            for (EmailMessage message : evnt.getMessages()) {
                if (rule.matches(message)) {
                    matches.get(rule).add(message);
                }
            }
        }
        
        evnt.setMatches(matches);
    }
}
