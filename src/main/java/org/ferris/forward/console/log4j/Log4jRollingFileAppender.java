package org.ferris.forward.console.log4j;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Log4jRollingFileAppender extends org.apache.log4j.RollingFileAppender {

    public Log4jRollingFileAppender() throws URISyntaxException {
        // This code assumes the following directory structure
        //
        // /project
        //    /bin
        //    /lib
        //      ferris-project-app-1.0.0.0-SNAPSHOT.jar
        //    /logs
        //
        // So the the log directory will be 1 directory up from where the 
        // JAR file is located.
        URL jarURL = this.getClass().getProtectionDomain().getCodeSource().getLocation();
        URI jarURI = jarURL.toURI();
        File jarFile = new File(jarURI);
        File parentDir = jarFile.getParentFile().getParentFile();
        File logsDir = new File(parentDir, "logs");
        File logFile = new File(logsDir, "application.log");
        super.setFile(logFile.toString());
    }
    
    private OutputStream os;
    
    @Override
    protected OutputStreamWriter createWriter(OutputStream os) {
        this.os = os;
        return super.createWriter(os);
    }
    
    public PrintStream getPrintStream() {
        if (os == null) {
            throw new IllegalStateException("The OutputSream property is null");
        } else {
            return new PrintStream(this.os);
        }
    }

}
