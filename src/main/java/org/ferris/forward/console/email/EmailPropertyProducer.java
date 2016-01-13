package org.ferris.forward.console.email;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import org.ferris.forward.console.conf.ConfDirectory;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailPropertyProducer {
    private Properties props;
    
    @Inject
    public EmailPropertyProducer(ConfDirectory confDir) throws Exception {
        props = new Properties();
        props.load(new FileInputStream(new File(confDir,"email.properties")));
    }
    
    @Produces @EmailProperty
    protected String produceMessage(InjectionPoint ip) {        
        EmailProperty m = ip.getAnnotated().getAnnotation(EmailProperty.class);
        return props.getProperty(m.value(), "-UNKNOWN-");
    }
}
