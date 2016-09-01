package org.ferris.forward.console.email;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import org.apache.log4j.Logger;
import static org.ferris.forward.console.email.EmailEvent.CLOSE;
import static org.ferris.forward.console.email.EmailEvent.DELETE_FORWARDED_EMAIL_MESSAGES;
import static org.ferris.forward.console.email.EmailEvent.GET_FOLDER;
import org.ferris.forward.console.mail.Pop;
import org.ferris.forward.console.retry.ExceptionRetry;
import org.jboss.weld.experimental.Priority;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailFolderHandler {

    @Inject
    protected Logger log;

    @Inject
    @EmailProperty("pop3sFolder")
    protected String folder;

    @Inject
    @EmailProperty("username")
    protected String username;

    @Inject
    @EmailProperty("password")
    protected String password;

    @Inject
    @Pop
    protected Session pop;

    @ExceptionRetry
    public void observeGetFolder(
        @Observes @Priority(GET_FOLDER) EmailEvent evnt
    ) throws MessagingException {
        log.info("ENTER");
        if (evnt.getRules().isEmpty()) {
            evnt.setFolder(new EmailFolder());
        } else {            
            Store store = pop.getStore();
            store.connect(username, password);
            Folder inbox = store.getFolder(folder);
            evnt.setFolder(new EmailFolder(store, inbox));
        }
    }
    
    
    @ExceptionRetry
    public void observeDeleteForwarded(
        @Observes @Priority(DELETE_FORWARDED_EMAIL_MESSAGES) EmailEvent evnt
    ) throws MessagingException {
        log.info("ENTER");
        evnt.getFolder().expungeForwarded();
    }
    
    
    @ExceptionRetry
    public void observeClose(
        @Observes @Priority(CLOSE) EmailEvent evnt
    ) throws MessagingException {
        log.info("ENTER");
        evnt.getFolder().close(true);
    }
}
