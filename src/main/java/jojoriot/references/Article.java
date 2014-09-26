package jojoriot.references;

import java.util.HashSet;
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
    private static final Set<String> VALID_FIELDS = new HashSet<>();
    
    static {
        VALID_FIELDS.add("author");
        VALID_FIELDS.add("title");
        VALID_FIELDS.add("journal");
        VALID_FIELDS.add("year");
        VALID_FIELDS.add("volume");
        VALID_FIELDS.add("number");
        VALID_FIELDS.add("pages");
        VALID_FIELDS.add("month");
        VALID_FIELDS.add("note");
        VALID_FIELDS.add("key");
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
    public Article(String author, String title, String journal, String year,
            String volume, String number, String pages, String month,
            String note, String key) {
        super(VALID_FIELDS);
        
        // pakolliset kentät
        super.put("author", author);
        super.put("title", title);
        super.put("journal", journal);
        super.put("year", year);
        
        // ei oo pakko olla
        if(!volume.equals(""))
            super.put("volume", volume);
        if(!number.equals(""))
            super.put("number", number);
        if(!pages.equals(""))
            super.put("pages", pages);
        if(!month.equals(""))
            super.put("month", month);
        if(!note.equals(""))
            super.put("note", note);
        if(!key.equals(""))
            super.put("key", key);
    }
    
    
    @Override
    void checkvalue(final String key, final String value) {
        if((key.equals("author") || key.equals("title")
                || key.equals("journal") || key.equals("year"))
                    && value.equals("")){
            throw new IllegalArgumentException("Pakollinen kenttä puuttuu");
        }
    }
    
    /*
    public static Set<String> getFields(){
        return VALID_FIELDS;
    }
    */
}
