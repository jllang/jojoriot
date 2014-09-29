package jojoriot.UI;

import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import jojoriot.IO.*;
import jojoriot.references.*;
import jojoriot.viitemanageri.*;

public class CLITest {
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

        ArrayList<String> prints = io.getPrints();
        assertTrue(prints.contains("author: asd\n"));
        assertTrue(prints.contains("title: asd\n"));
        assertTrue(prints.contains("journal: asd\n"));
        assertTrue(prints.contains("year: 2014\n"));
    }

    @Test
    public void nonNumberCommandFails() {
        start("a", "3");
        assertTrue(io.getPrints().contains("Please input a number."));
    }

    @Test
    public void numericNonCommandFails() {
        start("4", "3");
        assertTrue(io.getPrints().contains("Unknown command."));
    }

    @Test
    public void programExitsNicely() {
        start("3");
        assertTrue(io.getPrints().contains("Thank you for using Viitemanageri!"));
    }
}
