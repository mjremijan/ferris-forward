package org.ferris.forward.console.main;

import java.util.Arrays;
import java.util.List;
import javax.enterprise.event.Event;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.ferris.forward.console.email.EmailEvent;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class Main {

    public static void main(String[] args) {
        CDI<Object> cdi = CDI.getCDIProvider().initialize();
        Main main
            = cdi.select(Main.class).get();
        main.main(Arrays.asList(args));
    }

    @Inject
    protected Logger log;

    @Inject
    protected Event<EmailEvent> emailEvent;

    protected void main(List<String> args) {
        log.info("Forward application has started");

        log.debug("Firing EmailEvent");
        emailEvent.fire(new EmailEvent());
    }
}
