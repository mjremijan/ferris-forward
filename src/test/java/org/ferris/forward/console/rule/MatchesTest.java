package org.ferris.forward.console.rule;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.mail.Message;
import org.ferris.forward.console.email.EmailMessage;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class MatchesTest {

    public MatchesTest() {
    }

    @Test
    public void filterMatchesToDelete() throws Exception
    {
        Map<Rule, List<EmailMessage>> matches
            = new HashMap<>();

        EmailMessage mercury, venus, pepsi;
        EmailMessage earth, red, orange, yellow, coke, ski;
        {
            Rule r = new Rule();
            r.setDelete(Boolean.TRUE);

            matches.put(r, new LinkedList<>());
            {
                Message m = Mockito.mock(Message.class);
                Mockito.when(m.getSubject()).thenReturn("mercury");
                mercury = Mockito.spy(new EmailMessage(m));
                mercury.setForwarded(true);
                matches.get(r).add(mercury);
            }
            {
                Message m = Mockito.mock(Message.class);
                Mockito.when(m.getSubject()).thenReturn("venus");
                venus = Mockito.spy(new EmailMessage(m));
                venus.setForwarded(true);
                matches.get(r).add(venus);
            }
            {
                Message m = Mockito.mock(Message.class);
                Mockito.when(m.getSubject()).thenReturn("earth");
                earth = Mockito.spy(new EmailMessage(m));
                earth.setForwarded(false);
                matches.get(r).add(earth);
            }
        }
        {
            Rule r = new Rule();
            r.setDelete(Boolean.FALSE);

            matches.put(r, new LinkedList<>());
            {
                Message m = Mockito.mock(Message.class);
                Mockito.when(m.getSubject()).thenReturn("red");
                red = Mockito.spy(new EmailMessage(m));
                red.setForwarded(true);
                matches.get(r).add(red);
            }
            {
                Message m = Mockito.mock(Message.class);
                Mockito.when(m.getSubject()).thenReturn("orange");
                orange = Mockito.spy(new EmailMessage(m));
                orange.setForwarded(true);
                matches.get(r).add(orange);
            }
            {
                Message m = Mockito.mock(Message.class);
                Mockito.when(m.getSubject()).thenReturn("yellow");
                yellow = Mockito.spy(new EmailMessage(m));
                yellow.setForwarded(true);
                matches.get(r).add(yellow);
            }
        }
        {
            Rule r = new Rule();
            r.setDelete(Boolean.TRUE);

            matches.put(r, new LinkedList<>());
            {
                Message m = Mockito.mock(Message.class);
                Mockito.when(m.getSubject()).thenReturn("coke");
                matches.get(r).add(new EmailMessage(m));
                coke = Mockito.spy(new EmailMessage(m));
                coke.setForwarded(false);
                matches.get(r).add(coke);
            }
            {
                Message m = Mockito.mock(Message.class);
                Mockito.when(m.getSubject()).thenReturn("pepsi");
                matches.get(r).add(new EmailMessage(m));
                pepsi = Mockito.spy(new EmailMessage(m));
                pepsi.setForwarded(true);
                matches.get(r).add(pepsi);
            }
            {
                Message m = Mockito.mock(Message.class);
                Mockito.when(m.getSubject()).thenReturn("ski");
                matches.get(r).add(new EmailMessage(m));
                ski = Mockito.spy(new EmailMessage(m));
                ski.setForwarded(false);
                matches.get(r).add(ski);
            }
        }

        matches.entrySet()
        .stream()
            .filter(es -> es.getKey().getDelete().equals(Boolean.TRUE))
            .map(es -> es.getValue())
            .flatMap(m -> m.stream())
            .filter(m -> m.isForwarded())
            .forEach(m -> m.expunge())
        ;

        Mockito.verify(mercury, Mockito.times(1)).expunge();
        Mockito.verify(venus, Mockito.times(1)).expunge();
        Mockito.verify(earth, Mockito.times(0)).expunge();
        Mockito.verify(red, Mockito.times(0)).expunge();
        Mockito.verify(orange, Mockito.times(0)).expunge();
        Mockito.verify(yellow, Mockito.times(0)).expunge();
        Mockito.verify(coke, Mockito.times(0)).expunge();
        Mockito.verify(pepsi, Mockito.times(1)).expunge();
        Mockito.verify(ski, Mockito.times(0)).expunge();

//        List<EmailMessage> messagesWhereDeleteIsTrue
//            = matches.entrySet()
//                .stream()
//                .filter(es -> es.getKey().getDelete().equals(Boolean.TRUE))
//                .map(ab -> ab.getValue())
//                .flatMap(eml -> eml.stream())
//                .collect(Collectors.toList());
//
//        Assert.assertEquals(6, messagesWhereDeleteIsTrue.size());
//
//        List<String> subjects
//            = messagesWhereDeleteIsTrue
//                .stream()
//                .map(em -> em.getSubject())
//                .collect(Collectors.toList());
//
//        Assert.assertTrue(subjects.contains("mercury"));
//        Assert.assertTrue(subjects.contains("venus"));
//        Assert.assertTrue(subjects.contains("earth"));
//        Assert.assertTrue(subjects.contains("ski"));
//        Assert.assertTrue(subjects.contains("coke"));
//        Assert.assertTrue(subjects.contains("pepsi"));
    }
}
