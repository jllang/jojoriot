package jojoriot.viitemanageri;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import jojoriot.IO.*;
import jojoriot.UI.*;
import jojoriot.references.*;
import jojoriot.viitemanageri.*;

/**
 *
 * @author John Lång <jllang@cs.helsinki.fi>
 */
public class MainTest {
    private Stub io;

    private void start(String... inputs) {
        io = new Stub(inputs);
        
        Session session = new Session();
        CLI ui = new CLI(io, session);
        ui.start();
    }

    @Test
    public void addsReference() {
        start("1", "asd", "asd", "asd", "2014", "asd", "asd", "asd", "asd", "asd", "asd", "3");
        assertTrue(io.getPrints().contains("Reference added:\n"));
    }

    @Test
    public void invalidReferenceNotAdded() {
        start("1", "asd", "asd", "asd", "", "", "", "", "", "" , "", "3");
        assertTrue(io.getPrints().contains("Adding reference failed!\n"));
    }

    @Test
    public void previewPrintsCorrectly() {
        start("1", "asd", "asd", "asd", "2014", "", "", "", "", "", "", "2", "3");
        assertTrue(io.getPrints().contains("author: asd\n"));
        assertTrue(io.getPrints().contains("title: asd\n"));
        assertTrue(io.getPrints().contains("journal: asd\n"));
        assertTrue(io.getPrints().contains("year: 2014\n"));
    }

    @Test
    public void krapulasaatana() {
        Article asd = new Article("asd", "asd", "asd", "2014", "asd", "asd", "asd", "asd", "asd", "asd");
        asd.delete("volume");
        asd.get("title");

        Session s = new Session();
        s.preview();
    }
}

