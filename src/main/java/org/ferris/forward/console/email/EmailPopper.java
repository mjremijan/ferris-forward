package org.ferris.forward.console.email;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import org.apache.log4j.Logger;
import static org.ferris.forward.console.email.EmailEvent.GET_MESSAGES;
import org.ferris.forward.console.retry.ExceptionRetry;
import org.jboss.weld.experimental.Priority;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailPopper {

    @Inject
    protected Logger log;
    
    @Inject @EmailProperty("pop3sHost")
    protected String host;
    
    @Inject @EmailProperty("pop3sPort")
    protected String port;
    
    @Inject @EmailProperty("pop3sFolder")
    protected String folder;
    
    @Inject @EmailProperty("username")
    protected String username;
    
    @Inject @EmailProperty("password")
    protected String password;
    
    @Inject
    protected Instance<EmailMessage> emailMessageInstance;
    
    @ExceptionRetry
    public void popMessages(
        @Observes @Priority(GET_MESSAGES) EmailEvent evnt
    ) throws MessagingException {
        log.info("ENTER");
        
        if (evnt.getRules().isEmpty()) {
            evnt.setMessages(Collections.emptyList());
        } else {
            log.debug(String.format("Popping \"%s\" from \"%s\"", folder, host));
            Session pop = null;
            {
                Properties props = new Properties();
                props.put("mail.host", host);
                props.put("mail.store.protocol", "pop3s");
                props.put("mail.pop3s.auth", "true");
                props.put("mail.pop3s.port", port);
                pop = Session.getDefaultInstance(props, null);
            }

            Store store = pop.getStore();
            store.connect(username, password);
            Folder inbox = store.getFolder(folder);
            inbox.open(Folder.READ_WRITE);        
            Message[] messages = inbox.getMessages();

            if (messages == null || messages.length == 0) {
                evnt.setMessages(Collections.emptyList());
            } else {
                evnt.setMessages(
                    Arrays.asList(messages)
                    .stream()
                    .map(m -> emailMessageInstance.get().setMessage(m))
                    .collect(Collectors.toList())
                );
            }            
        }
    }
}
