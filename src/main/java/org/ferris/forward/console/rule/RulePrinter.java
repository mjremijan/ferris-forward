package org.ferris.forward.console.rule;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.forward.console.email.*;
import static org.ferris.forward.console.email.EmailEvent.PRINT_RULES;
import org.jboss.weld.experimental.Priority;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RulePrinter {

    @Inject
    protected Logger log;

    public void printRules(
        @Observes @Priority(PRINT_RULES) EmailEvent evnt
    ) {
        log.info("ENTER");
        log.info(String.format("Rule count: %d", evnt.getRules().size()));
        evnt.getRules().forEach(r -> log.info("  " + r.toString()));
    }
}
