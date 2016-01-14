package org.ferris.forward.console.log4j;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class Log4jRollingFileAppenderPrintStream extends PrintStream {

    public Log4jRollingFileAppenderPrintStream(OutputStream os) {
        super(os);
    }
}
