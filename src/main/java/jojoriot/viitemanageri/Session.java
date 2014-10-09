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
        for (final Reference reference : references) {
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
        for (final Reference ref : references) {
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
        final Scanner sc = new Scanner(new File(path));
        final StringBuilder sb = new StringBuilder();

        while(sc.hasNext()){
            sb.append(sc.nextLine());
        }

        String content = sb.toString();
        
        content = content.replace("\\\"{a}", "ä");
        content = content.replace("\\\"{A}", "Ä");
        content = content.replace("\\\"{o}", "ö");
        content = content.replace("\\\"{O}", "Ö");
        content = content.replace("\\aa", "å");
        content = content.replace("\\AA", "Å");

        final ArrayList<String> bibtex = new ArrayList<>();
        int split;
        while(content.length() > 1){
            split = endOfCurlyBracket(content, content.indexOf("{") + 1) + 1;
            bibtex.add(content.substring(0, split - 1));
            content = content.substring(split - 1);
        }
        references.clear();

        for (final String rawObject : bibtex) {
            convertAndAddBibtextReference(rawObject);
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

        final LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        fields.put("identifier", code);

        while (ref.charAt(cur + 2) != '}'){
            key = ref.substring(cur + 2, cur = ref.indexOf("=", cur)).trim();
            value = ref.substring(cur = ref.indexOf("{", cur) + 1,
                    cur = endOfCurlyBracket(ref, cur) - 1);
            fields.put(key, value);
        }

        switch (type.toLowerCase()) {
            case "article":
                references.add(new Article(fields));
                break;
            case "book":
                references.add(new Book(fields));
                break;
            case "inproceedings":
                references.add(new Inproceedings(fields));
                break;
            default:
                throw new AssertionError();
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
        final int leftBracket = s.indexOf("{", start);
        final int rightBracket = s.indexOf("}", start);
        if(leftBracket > 0 && leftBracket < rightBracket){
            return endOfCurlyBracket(s, endOfCurlyBracket(s, leftBracket + 1));
        }else{
            return rightBracket + 1;
        }
    }

    /**
     * Checks for potential identifier uniqueness
     *
     * @param identifier
     * @return
     */
    public boolean isUniqueIdentifier(final String identifier) {
        for (final Reference ref : references) {
            if (identifier.equals(ref.getIdentifier())) {
                return false;
            }
        }

        return true;
    }

}
