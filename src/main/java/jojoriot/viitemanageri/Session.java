package jojoriot.viitemanageri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jojoriot.references.Reference;

/**
 *
 */
public class Session {
    // Tämä luokka pitää stubata CLI-testejä varten.

    /**
     * List of references made in this session.
     */
    private final ArrayList<Reference> references;

    public Session() {
        references = new ArrayList<>();
    }

    /**
     * Adds a new reference
     * @param ref
     */
    public void add(final Reference ref) {
        references.add(ref);
    }

    /**
     * Removes a reference from session
     * @param ref
     */
    public void remove(final Reference ref) {
        references.remove(ref);
    }

    /**
     *
     * @return
     */
    public ArrayList<Reference> getReferences() {
        return references;
    }

    /**
     * Exports all the references to a bibtex file specified by the argument.
     * The contents of this file shall be overwritten.
     *
     * @param path Location of the file to be written into.
     * @throws java.io.FileNotFoundException If the file given by the user was
     * not found.
     */
    public void export(final String path) throws FileNotFoundException {
        final File file = new File(path);
        final PrintWriter pw = new PrintWriter(file);
        for (final Reference reference : references) {
            pw.println(reference.toBibtexString());
        }
        pw.close();
    }
}
