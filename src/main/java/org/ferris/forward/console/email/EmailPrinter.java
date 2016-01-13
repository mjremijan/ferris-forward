package org.ferris.forward.console.email;

import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import static org.ferris.forward.console.email.EmailEvent.PRINT_MATCHES_TO_RULES;
import static org.ferris.forward.console.email.EmailEvent.PRINT_MESSAGES;
import org.jboss.weld.experimental.Priority;


/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailPrinter {
    
    @Inject
    protected Logger log;
    
    
    public void printAllEmailMessages (
        @Observes @Priority(PRINT_MESSAGES) EmailEvent evnt
    ) { 
        log.info("ENTER");
        log.info(String.format(
            "Message count: %d", evnt.getMessages().size()));
        
        AtomicInteger cnt = new AtomicInteger(1);
        evnt.getMessages().forEach(
            em ->                 
                log.info(
                    String.format(
                        "%nMESSAGE #%d%n  from=\"%s\"%n  subject=\"%s\""
                        , cnt.getAndAdd(1)
                        , em.getFrom()
                        , em.getSubject()
                    )
                )
        );        
    }
    
    public void printMatchesToRules(
        @Observes @Priority(PRINT_MATCHES_TO_RULES) EmailEvent evnt
    ) {
        log.info("ENTER");
        
        StringBuilder sp 
            = new StringBuilder();
        sp.append("\n");
        
        evnt.getMatches().entrySet()
            .stream()
            .filter(e -> e.getValue().size() > 0)
            .forEach(e -> {
                if (!e.getValue().isEmpty()) {
                    sp.append(String.format("FORWARD TO: %s%n", e.getKey().getTo().toString()));
                    e.getValue().forEach(t -> sp.append(String.format("  MESSAGE%n    from=\"%s\"%n    subject=\"%s\"%n", t.getFrom().toString(), t.getSubject())));
                }
            })        
        ;
        
        log.info(sp.toString());
    }
}
