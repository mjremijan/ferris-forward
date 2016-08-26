package org.ferris.forward.console.rule;

import java.util.List;
import org.apache.log4j.Logger;
import org.ferris.forward.console.application.ApplicationDirectory;
import org.ferris.forward.console.conf.ConfDirectory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RuleHandlerTest {

    @Mock
    Logger log;

    RuleHandler handler;

    @Before
    public void before() {
        handler = new RuleHandler();
        handler.log = log;
        handler.rulesFile = new RulesFile(
            new ConfDirectory(new ApplicationDirectory("src/test/resources"))
        );
    }

    @Test
    public void unmarshall() throws Exception {
        List<Rule> rules
            = handler.getRules();

        Assert.assertEquals(4, rules.size());
        {
            Rule r = rules.get(0);
            Assert.assertEquals("Real Name <forward.to.me@somewhere.org>", r.getTo().toString());
            Assert.assertEquals("^regular.*expression[pattern](match)", r.getFrom().pattern());
            Assert.assertEquals("^regular.*expression[pattern](match)", r.getSubject().pattern());
            Assert.assertTrue(r.getDelete());
        }
        {
            Rule r = rules.get(1);
            Assert.assertEquals("Real Name <forward.to.me@somewhere.org>", r.getTo().toString());
            Assert.assertEquals("^regular.*expression[pattern](match)", r.getFrom().pattern());
            Assert.assertNull(r.getSubject());
            Assert.assertTrue(r.getDelete());
        }
        {
            Rule r = rules.get(2);
            Assert.assertEquals("Real Name <forward.to.me@somewhere.org>", r.getTo().toString());
            Assert.assertNull(r.getFrom());
            Assert.assertEquals("^regular.*expression[pattern](match)", r.getSubject().pattern());
            Assert.assertTrue(r.getDelete());
        }
        {
            Rule r = rules.get(3);
            Assert.assertEquals("Real Name <forward.to.me@somewhere.org>", r.getTo().toString());
            Assert.assertEquals("^regular.*expression[pattern](match)", r.getFrom().pattern());
            Assert.assertEquals("^regular.*expression[pattern](match)", r.getSubject().pattern());
            Assert.assertFalse(r.getDelete());
        }
    }



}
