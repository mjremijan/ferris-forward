package org.ferris.forward.console.email;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailFolder {
    protected Holder holder;

    public EmailFolder() {
        holder = new EmptyHolder();
    }

    public EmailFolder(Store store, Folder folder) throws MessagingException {
        holder = new DefaultHolder(store, folder);
    }

    public void close(boolean expunge) {
        try {
            holder.close(expunge);
            holder = new EmptyHolder();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<EmailMessage> getMessages() {
        try {
            return holder.getMessages();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public void expungeForwarded() {
        holder.expungeForwarded();
    }
    
    interface Holder {
        List<EmailMessage> getMessages() throws MessagingException;
        void close(boolean expunge) throws MessagingException;
        void expungeForwarded();
    }

    class DefaultHolder implements Holder {

        Store store;
        Folder folder;
        List<EmailMessage> messages;
        
        public DefaultHolder(Store store, Folder folder) throws MessagingException {
            this.store = store;
            this.folder = folder;
            this.folder.open(Folder.READ_WRITE);
            
            Message[] msgArray = folder.getMessages();
            if (messages == null || msgArray.length == 0) {
                messages = Collections.emptyList();
            } else {
                messages = Arrays.asList(msgArray)
                    .stream()
                    .map(m -> new EmailMessage(m))
                    .collect(Collectors.toList());
            }
        } 

        @Override
        public List<EmailMessage> getMessages() throws MessagingException {
            return messages;
        }

        @Override
        public void close(boolean expunge) throws MessagingException {
            folder.close(expunge);
            store.close();
        }

        @Override
        public void expungeForwarded() {
            messages.stream()
                .filter(m->m.isForwarded())
                .forEach(EmailMessage::expunge);
        }
    }

    class EmptyHolder implements Holder {

        @Override
        public List<EmailMessage> getMessages() throws MessagingException {
            return Collections.emptyList();
        }

        @Override
        public void close(boolean expunge) throws MessagingException {
        }
        
        @Override
        public void expungeForwarded() {
        }
    }
}
