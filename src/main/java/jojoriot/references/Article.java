package jojoriot.references;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Article is a type of Reference (extends abstract class Reference)
 *
 * @author Jouko
 */
public class Article extends Reference {
    
    /**
     * Set of acceptable fields
     * 
     */
    public static final ArrayList<String> REQUIRED_FIELDS = new ArrayList<String>();
    public static final ArrayList<String> OPTIONAL_FIELDS = new ArrayList<String>();
    
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
     * Required fields that will be created even if left empty:
     * @param author
     * @param title
     * @param journal
     * @param year
     * 
     * Optional fields that won't be created if left empty:
     * @param volume
     * @param number
     * @param pages
     * @param month
     * @param note
     * @param key 
     */
    public Article(LinkedHashMap<String, String> requiredFields, LinkedHashMap<String, String> optionalFields) {
        super(REQUIRED_FIELDS, OPTIONAL_FIELDS);
        
        // pakolliset kentät
        for(Entry<String, String> entry : requiredFields.entrySet()) {
            entry.getKey();
            super.put(entry.getKey(), entry.getValue());
        }
        
        // valinnaiset kentät
        for(Entry<String, String> entry : optionalFields.entrySet()) {
            entry.getKey();
            super.put(entry.getKey(), entry.getValue());
        }
        
        
     
    }
    
    
    @Override
    void checkvalue(final String key, final String value) {
        if((key.equals("author") || key.equals("title")
                || key.equals("journal") || key.equals("year"))
                    && value.equals("")){
            throw new IllegalArgumentException("Pakollinen kenttä puuttuu");
        }
    }

    public static ArrayList<String> getRequiredFields(){
        return REQUIRED_FIELDS;
    }
    
    
    public static ArrayList<String> getOPtionalFields(){
        return OPTIONAL_FIELDS;
    }
    
}
