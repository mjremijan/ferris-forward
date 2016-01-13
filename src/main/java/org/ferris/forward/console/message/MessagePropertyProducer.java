package org.ferris.forward.console.message;

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
public class MessagePropertyProducer {
    private Properties props;
    
    @Inject
    public MessagePropertyProducer(ConfDirectory confDir) throws Exception {
        props = new Properties();
        props.load(new FileInputStream(new File(confDir,"message.properties")));
    }
    
    @Produces @MessageProperty
    public String produceMessage(InjectionPoint ip) {        
        MessageProperty m = ip.getAnnotated().getAnnotation(MessageProperty.class);
        return props.getProperty(m.value(), "-UNKNOWN-");
    }
}
