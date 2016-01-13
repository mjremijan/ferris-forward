package org.ferris.forward.console.log4j;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.apache.log4j.Logger;

public class Log4jLoggerProducer {

    @Produces
    public Logger getLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getBean().getBeanClass());
    }

}
