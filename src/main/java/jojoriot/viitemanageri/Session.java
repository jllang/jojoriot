package jojoriot.viitemanageri;

import java.util.ArrayList;
import jojoriot.references.Reference;

/**
 *
 * @author riku
 */
public class Session {
    private final ArrayList<Reference> references;
    
    public Session() {
        references = new ArrayList<>();
    }
    
    public void add(Reference ref) {
        references.add(ref);
    }
    
    public ArrayList<Reference> getReferences() {
        return references;
    }
    
    public String preview() {
        return "";
    }
}
