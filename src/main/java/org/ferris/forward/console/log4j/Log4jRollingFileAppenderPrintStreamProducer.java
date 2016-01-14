package org.ferris.forward.console.log4j;

import java.io.OutputStream;
import java.util.Enumeration;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class Log4jRollingFileAppenderPrintStreamProducer {

    @Inject
    protected Logger log;

    @Produces
    protected Log4jRollingFileAppenderPrintStream getPrintStream() {
        OutputStream os = System.out;
        Enumeration enu = log.getAllAppenders();
        while (enu.hasMoreElements()) {
            Object o = enu.nextElement();
            if (o instanceof Log4jRollingFileAppender) {
                os = ((Log4jRollingFileAppender)o).getPrintStream();
            }
        }
        return new Log4jRollingFileAppenderPrintStream(os);
    }
}
