package org.ferris.forward.console.email;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;
import static org.ferris.forward.console.email.EmailEvent.FORWARD_EMAIL_MESSAGES;
import org.ferris.forward.console.log4j.Log4jRollingFileAppender;
import org.ferris.forward.console.retry.ExceptionRetry;
import org.ferris.forward.console.rule.Rule;
import org.jboss.weld.experimental.Priority;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailForwarder {
    @Inject
    protected Logger log;
    
    @Inject @EmailProperty("host")
    protected String host;
    
    @Inject @EmailProperty("port")
    protected String port;
    
    @Inject @EmailProperty("username")
    protected String username;
    
    @Inject @EmailProperty("password")
    protected String password;
    
    @Inject @EmailProperty("emailAddress")
    protected String emailAddress;
    
    
    public void forward(
        @Observes @Priority(FORWARD_EMAIL_MESSAGES) EmailEvent evnt
    ){
        log.info(String.format("ENTER"));
        if (evnt.getMatches().isEmpty()) {
            log.info("No matches so no emails to forward.");
            return;
        } 
        
//        Map.Entry<Rule, List<EmailMessage>> entry 
//            = evnt.getMatches().entrySet().iterator().next();
//        try {
//            forward(entry.getKey(), entry.getValue().get(0));
//        } catch (MessagingException | IOException ex) { 
//            log.warn("oops", ex);
//        }
        
//        evnt.getMatches().entrySet().forEach(a -> {            
//            a.getValue().forEach(
//                b -> { 
//                    try { 
//                        forward(a.getKey(),b); 
//                    } catch (MessagingException | IOException ex) { 
//                        log.warn("oops", ex);
//                    } 
//                }
//            );
//        });
    }
    
    @ExceptionRetry
    protected void forward(Rule rule, EmailMessage emailMessage) 
    throws MessagingException, IOException
    {
        log.info(String.format("ENTER"));

        Session smtp = null;
        {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.smtp.socketFactory.port", port);
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.port", port);
            
            smtp = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        }
        smtp.setDebug(true);
        smtp.setDebugOut(getPrintStream());
        
        
//        // Original part
//        BodyPart originalMessagePart = new MimeBodyPart();
//        originalMessagePart.setText("Original message:\n\n");
        
        // Forwarded part
        BodyPart forwardMessagePart = new MimeBodyPart();
        forwardMessagePart.setDataHandler(emailMessage.getDataHandler());
        
        // Multipart to combine all parts
        Multipart content = new MimeMultipart();
        {
            //content.addBodyPart(originalMessagePart);
            content.addBodyPart(forwardMessagePart);
        }
        
        
        MimeMessage mimeMessage = new MimeMessage(smtp);
        {            
            // Date
            mimeMessage.setSentDate(new Date());
            
            // From
            InternetAddress ia = new InternetAddress(emailAddress) {{ setPersonal("Forward"); }};            
            mimeMessage.setFrom(ia);
            
            // Reply to
            mimeMessage.setReplyTo(new InternetAddress[] {new InternetAddress(emailAddress)});
            
            // To
            mimeMessage.setRecipient(Message.RecipientType.TO, rule.getTo());
            
            // Subject
            mimeMessage.setSubject(emailMessage.getSubject());
            
            // Content
            mimeMessage.setContent(content);
        }
        
        
        log.info(String.format("Attempt to forward message \"%s\" to \"%s\"", emailMessage.getSubject(), rule.getTo().toString()));
        Transport.send(mimeMessage);   
    }
    
    
    protected PrintStream getPrintStream() {
        Enumeration enu = log.getAllAppenders();
        while (enu.hasMoreElements()) {
            Object o = enu.nextElement();
            if (o instanceof Log4jRollingFileAppender) {
                return ((Log4jRollingFileAppender)o).getPrintStream();
            }
        }
        return System.out;
    }
}
