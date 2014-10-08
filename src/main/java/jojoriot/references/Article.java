package jojoriot.references;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * An article from a journal or magazine.
 */
public class Article extends Reference {

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
        REQUIRED_FIELDS.add("journal");
        REQUIRED_FIELDS.add("year");
        
        OPTIONAL_FIELDS.add("volume");
        OPTIONAL_FIELDS.add("number");
        OPTIONAL_FIELDS.add("pages");
        OPTIONAL_FIELDS.add("month");
        OPTIONAL_FIELDS.add("note");
        OPTIONAL_FIELDS.add("key");
    }

    /**
     *
     * @param fields
     */
    public Article(final LinkedHashMap<String, String> fields) {
        super(REQUIRED_FIELDS, OPTIONAL_FIELDS, fields);
    }
}
