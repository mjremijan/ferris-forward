package org.ferris.forward.console.conf;

import java.io.File;
import javax.inject.Inject;
import org.ferris.forward.console.application.ApplicationDirectory;
import static org.ferris.forward.console.application.ApplicationSubDirectory.conf;
import static java.lang.String.format;

public class ConfDirectory extends File {

    private static final long serialVersionUID = 7491901906021288631L;

    @Inject
    public ConfDirectory(ApplicationDirectory ad) {
        super(ad, format("%s", conf));
    }
}
