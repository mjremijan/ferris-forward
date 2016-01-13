package org.ferris.forward.console.rule;

import java.io.File;
import javax.inject.Inject;
import org.ferris.forward.console.conf.*;

public class RulesFile extends File {

    private static final long serialVersionUID = 7491901906021288631L;

    @Inject
    public RulesFile(ConfDirectory conf) {
        super(conf, "rules.xml");
    }
}
