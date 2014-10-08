package jojoriot.references;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * A book with an explicit publisher.
 */
public class Book extends Reference {

    /**
     * List of required fields
     */
    public static final ArrayList<String> REQUIRED_FIELDS = new ArrayList<>();
    
    /**
     * List of optional fields
     */
    public static final ArrayList<String> OPTIONAL_FIELDS = new ArrayList<>();
    
    static {
        REQUIRED_FIELDS.add("identifier");
        
        REQUIRED_FIELDS.add("author");
        REQUIRED_FIELDS.add("title");
        REQUIRED_FIELDS.add("publisher");
        REQUIRED_FIELDS.add("year");
        
        OPTIONAL_FIELDS.add("volume");
        OPTIONAL_FIELDS.add("series");
        OPTIONAL_FIELDS.add("address");
        OPTIONAL_FIELDS.add("edition");
        OPTIONAL_FIELDS.add("month");
        OPTIONAL_FIELDS.add("note");
        OPTIONAL_FIELDS.add("key");
    }

    /**
     *
     * @param fields
     */
    public Book(final LinkedHashMap<String, String> fields) {
        super(REQUIRED_FIELDS, OPTIONAL_FIELDS, fields);
    }
}
