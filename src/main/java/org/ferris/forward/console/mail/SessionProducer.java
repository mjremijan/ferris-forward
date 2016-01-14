package org.ferris.forward.console.mail;

import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import org.apache.log4j.Logger;
import org.ferris.forward.console.email.EmailProperty;
import org.ferris.forward.console.log4j.Log4jRollingFileAppenderPrintStream;
import org.ferris.forward.console.retry.ExceptionRetry;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@ApplicationScoped
public class SessionProducer 
{
    @Inject
    protected Logger log;
    
    @Inject @EmailProperty("pop3sHost")
    protected String popHost;
    
    @Inject @EmailProperty("pop3sPort")
    protected String popPort;
    
    @Inject @EmailProperty("username")
    protected String username;
    
    @Inject @EmailProperty("password")
    protected String password;     
    
    @Inject @EmailProperty("host")
    protected String smtpHost;
    
    @Inject @EmailProperty("port")
    protected String smtpPort;
    
    @Inject
    Log4jRollingFileAppenderPrintStream printStream;
    
    protected Session pop;    
    protected Session smtp;
    
    @Produces @Pop
    @ExceptionRetry
    public Session getPopSession() {
        log.info("ENTER");
        if (pop == null) {
            Properties props = new Properties();
            props.put("mail.host", popHost);
            props.put("mail.store.protocol", "pop3s");
            props.put("mail.pop3s.auth", "true");
            props.put("mail.pop3s.port", popPort);
            pop = Session.getDefaultInstance(props, null);
        }        
        return pop;
    }
    
    @Produces @Smtp
    @ExceptionRetry
    protected Session getSession() throws MessagingException {
        log.info("ENTER");
        if(smtp == null)
        {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", smtpHost);
            props.setProperty("mail.smtp.socketFactory.port", smtpPort);
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.port", smtpPort);
            
            smtp = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            smtp.setDebug(true);
            smtp.setDebugOut(printStream);
        }
        
        return smtp;
    }
}
