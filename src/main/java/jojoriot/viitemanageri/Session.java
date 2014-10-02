package jojoriot.viitemanageri;

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
}
