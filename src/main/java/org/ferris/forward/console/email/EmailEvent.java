package org.ferris.forward.console.email;

import java.util.List;
import java.util.Map;
import org.ferris.forward.console.rule.Rule;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class EmailEvent {

    public static final int GET_RULES = 10;
    public static final int PRINT_RULES = 15;
    
    public static final int GET_MESSAGES = 20;
    public static final int PRINT_MESSAGES = 25;

    public static final int FIND_MATCHES_TO_RULES = 30;
    public static final int PRINT_MATCHES_TO_RULES = 35;
    
    public static final int FORWARD_EMAIL_MESSAGES = 60;
    
    public static final int DELETE_INBOX_EMAILS = 70;

    protected List<Rule> rules;
    protected List<EmailMessage> messages;
    protected Map<Rule, List<EmailMessage>> matches;

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Rule> getRules() {
        return this.rules;
    }

    void setMessages(List<EmailMessage> messages) {
        this.messages = messages;
    }

    public List<EmailMessage> getMessages() {
        return this.messages;
    }

    public Map<Rule, List<EmailMessage>> getMatches() {
        return matches;
    }

    public void setMatches(Map<Rule, List<EmailMessage>> matches) {
        this.matches = matches;
    }
}
