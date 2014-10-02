package jojoriot.references;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

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
     * @param identifier
     * @param fields
     */
    public Article(final String identifier,
            final LinkedHashMap<String, String> fields) {
        super(identifier, REQUIRED_FIELDS, OPTIONAL_FIELDS);

        for (final Entry<String, String> entry : fields.entrySet()) {
            super.put(entry.getKey(), entry.getValue());
        }
        
        for (String field : REQUIRED_FIELDS) {
            if (super.get(field) == null) {
                throw new IllegalArgumentException("Missing required field \""
                        + field + "\".");
            }
        }
    }
}
