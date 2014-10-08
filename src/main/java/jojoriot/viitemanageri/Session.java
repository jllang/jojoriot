package jojoriot.viitemanageri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import jojoriot.references.Article;
import jojoriot.references.Book;
import jojoriot.references.Inproceedings;
import jojoriot.references.Reference;

/**
 *
 */
public class Session {
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
     * Deletes a previously added reference that matches the given key.
     *
     * @param identifier Identifies the reference to be deleted.
     * @throws NoSuchElementException If the requested reference was not found.
     */
    public void delete(final String identifier) throws NoSuchElementException {
        for (Reference reference : references) {
            if (reference.getIdentifier().equals(identifier)) {
                remove(reference);
                return;
            }
        }
        throw new NoSuchElementException("Identifier \"" + identifier + "\" "
                + "does not match any reference.");
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
     * 
     * @param identifier
     * @return 
     */
    public Reference getReference(final String identifier)
            throws NoSuchElementException {
        for (Reference ref : references) {
            if (identifier.equals(ref.getIdentifier())) {
                return ref;
            }
        }
        
        throw new NoSuchElementException("Identifier \"" + identifier + "\" "
                + "does not match any reference.");
    }

    /**
     * Exports all the references to a bibtex file specified by the argument.
     * The contents of this file shall be overwritten.
     *
     * @param path Location of the file to be written into.
     * @throws java.io.FileNotFoundException If the file given by the user was
     * not found.
     */
    public void save(final String path) throws FileNotFoundException {
        final File file = new File(path);
        final PrintWriter pw = new PrintWriter(file);
        for (final Reference reference : references) {
            pw.println(reference.toBibtexString());
        }
        pw.close();
    }

    /**
     * Imports a Bibtext file.
     *
     * @param path
     * @throws FileNotFoundException
     */
    public void load(final String path) throws FileNotFoundException{
        String filu = "";
 
        final Scanner sc = new Scanner(new File(path));

        while(sc.hasNext()){
            filu += sc.nextLine();
        }

        ArrayList<String> al = new ArrayList<String>();
        int l = 0;
        while(filu.length() > 1){
            l = endOfCurlyBracket(filu, filu.indexOf("{")+1) + 1;
            al.add(filu.substring(0,l -1));
            filu = filu.substring(l-1);
        }
        references.clear();

        for(int i = 0; i < al.size();i++){
            convertAndAddBibtextReference(al.get(i));
        }
    }

    /**
     * Converts bibtext String to a Reference object and
     * places it in references ArrayList.
     *
     * @param ref
     */
    private void convertAndAddBibtextReference(final String ref){
        int cur = 1;
        final String type = ref.substring(cur, cur = ref.indexOf("{"));
        final String code = ref.substring(cur + 1, cur = ref.indexOf(","));
        String key, value;
        
        LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        fields.put("identifier", code);
        
        while(ref.charAt(cur+2) != '}'){
            key = ref.substring(cur + 2, cur = ref.indexOf("=", cur)).trim();
            value = ref.substring(cur = ref.indexOf("{", cur) + 1,
                    cur = endOfCurlyBracket(ref, cur) - 1);
            fields.put(key, value);
        }

        if(type.equalsIgnoreCase("article")){
            references.add(new Article(fields));
        } else if(type.equalsIgnoreCase("book")){
            references.add(new Book(fields));
        } else if(type.equalsIgnoreCase("inproceedings")){
            references.add(new Inproceedings(fields));
        }

    }

    /**
     * Searches and returns the index of the closing bracket for the last
     * opening curly bracket before the starting point.
     *
     * @param s
     * @param start
     * @return
     */
    private static int endOfCurlyBracket(final String s, final int start){
        if(s.indexOf("{", start) > 0 && s.indexOf("{", start) < s.indexOf("}", start)){
            return endOfCurlyBracket(s, endOfCurlyBracket(s, s.indexOf("{", start)+1));
        }else{
            return s.indexOf("}", start) +1;
        }
    }

    /**
     * Checks for potential identifier uniqueness
     *
     * @param identifier
     * @return
     */
    public boolean isUniqueIdentifier(final String identifier) {
        for (Reference ref : references) {
            if (identifier.equals(ref.getIdentifier())) {
                return false;
            }
        }

        return true;
    }

}
