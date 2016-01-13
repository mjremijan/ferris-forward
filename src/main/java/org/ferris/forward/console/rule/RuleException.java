package org.ferris.forward.console.rule;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class RuleException extends RuntimeException {

    public RuleException(String message) {
        super(message);
    }
    public RuleException(String message, Throwable t) {
        super(message, t);
    }
}
