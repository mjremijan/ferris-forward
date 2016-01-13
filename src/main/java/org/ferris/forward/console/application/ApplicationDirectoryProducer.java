package org.ferris.forward.console.application;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.enterprise.inject.Produces;

public class ApplicationDirectoryProducer {

    @Produces
    public ApplicationDirectory getApplicationDirectory() throws URISyntaxException {
        URL jarURL = ApplicationDirectory.class.getProtectionDomain().getCodeSource().getLocation();
        URI jarURI = jarURL.toURI();
        File jarFile = new File(jarURI);
        File appFile = jarFile.getParentFile().getParentFile();
        ApplicationDirectory appDir = new ApplicationDirectory(appFile.getAbsolutePath());
        return appDir;
    }
}
