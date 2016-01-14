package org.ferris.forward.console.email;

import javax.activation.DataHandler;
import javax.enterprise.context.Dependent;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Dependent
public class EmailMessage {

    protected Message message;
    
    protected boolean forwarded; 
    
    public EmailMessage(Message m) {
        this.message = m;
        this.forwarded = false;
    }

    public String getSubject() {
        try {
            String subject = message.getSubject();
            if (subject == null) {
                subject = "";
            }
            return subject;
        } catch (Exception e) {
            throw new RuntimeException("Problem getting subject", e);
        }

//        String subject = "";
//        
//        String[] subjectHeaders 
//            = message.getHeader("Subject");
//        
//        if (subjectHeaders == null) {
//            log.warn("Subject headers array is null");
//        } 
//        else
//        if (subjectHeaders.length == 0) {
//            log.warn("Subject headers array is empty");
//        }
//        else {
//            String subjectHeader = subjectHeaders[0];
//            if (subjectHeader == null) {
//                subjectHeader = "";
//            }
//            subject = subjectHeader;
//        }
//        log.info(String.format("Returning subject=\"%s\"",subject));
//        return subject;
    }

    public Address getFrom() {
        try {
            Address[] froms
                = message.getFrom();
            if (froms == null || froms.length == 0) {
                throw new MessagingException(
                    "The 'froms' array is null or empty so I have no idea who sent the message"
                );
            }
            return froms[0];
        } catch (MessagingException e) {
            throw new RuntimeException("Problem getting from address", e);
        }
    }

    public DataHandler getDataHandler() {
        try {
            return message.getDataHandler();
        } catch (MessagingException e) {
            throw new RuntimeException("Problem getting DataHandler", e);
        }
    }
    
    public void setForwarded(boolean b) {
        this.forwarded = b;
    }
    
    public boolean isForwarded() {        
        return this.forwarded;
    }
    
    public void expunge() {
        try {
            this.message.setFlag(Flags.Flag.DELETED, true);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}
