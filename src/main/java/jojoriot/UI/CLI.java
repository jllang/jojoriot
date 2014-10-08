package jojoriot.UI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import jojoriot.references.Article;
import jojoriot.references.Book;
import jojoriot.references.Inproceedings;
import jojoriot.references.Reference;
import jojoriot.viitemanageri.Session;

/**
 * CLI is a textual implementation of User Interface.
 */
public final class CLI implements UI {

    private final Scanner in;
    private final PrintStream out;
    private final Session session;

    public CLI(final Scanner in, final PrintStream out, final Session session) {
        this.in = in;
        this.out = out;
        this.session = session;
    }

    @Override
    public void start() {
        out.print("Viitemanageri!\n");
        while (true) {
            out.print("\n1. Add reference"
                    + "\n2. Preview references"
                    + "\n3. Preview references in BibTeX format"
                    + "\n4. Save to file"
                    + "\n5. Delete reference"
                    + "\n6. Import BibText file"
                    + "\n7. Edit reference"
                    + "\n8. Exit"
                    + "\n> ");

            final int command;
            try {
                command = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                out.print("Please input a number.");
                continue;
            }

            switch (command) {
                case 1:
                    addReference();
                    break;
                case 2:
                    previewReferences();
                    break;
                case 3:
                    previewBibtext();
                    break;
                case 4:
                    exportBibtex();
                    break;
                case 5:
                    deleteReference();
                    break;
                case 6:
                    importBibtext();
                    break;
                case 7:
                    editReference();
                    break;
                case 8:
                    out.print("Thank you for using Viitemanageri!");
                    return;
                default:
                    out.print("Unknown command.");
            }
        }
    }

    private void printReference(final Reference ref) {
        final Map<String, String> referenceData = ref.getData();
        
        out.print("\n");
        for(final Map.Entry<String, String> entry : referenceData.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                out.print("    " + entry.getKey() + ": " + entry.getValue()
                        + "\n");
            }
        }
    }

    private void previewReferences() {
        final ArrayList<Reference> references = session.getReferences();

        if (references.isEmpty()) {
            out.println("(No references.)");
            return;
        }

        for(final Reference ref : references) {
            printReference(ref);
        }
    }

    private void previewBibtext(){
        final ArrayList<Reference> references = session.getReferences();

        if (references.isEmpty()) {
            out.println("(No references.)");
            return;
        }

        for(final Reference ref : references) {
            out.print(ref.toBibtexString() + "\n");
        }
    }

    private void editReference() {
        out.print("Which reference will be edited?\n> ");
        final String identifier = in.nextLine();
        try {
            Reference ref = session.getReference(identifier);
            
            for (final Map.Entry<String, String> entry :
                    ref.getData().entrySet()) {
                String field = entry.getKey();
                field += ref.isRequiredField(field) ? "*" : "";
                out.print(field + " (" + entry.getValue() + "): ");
                
                String value = in.nextLine();
                if (value.equals(" ")) {
                    try {
                        ref.delete(entry.getKey());
                    } catch (IllegalArgumentException e) {
                        out.println(e.getMessage());
                        return;
                    }
                } else if (!value.isEmpty()) {
                    ref.put(entry.getKey(), entry.getValue());
                }
            }
        } catch (NoSuchElementException e) {
            out.println(e.getMessage());
            return;
        }
        
        out.println("Reference \"" + identifier + "\" edited.");
    }

    private void addReference() {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>();

        out.print("1. Article\n2. Book\n3. Inproceedings\n> ");
        int type = in.nextInt();
        
        List<String> required, optional;
        if (type == 1) {
            required = Article.REQUIRED_FIELDS;
            optional = Article.OPTIONAL_FIELDS;
        } else if (type == 2) {
            required = Book.REQUIRED_FIELDS;
            optional = Book.OPTIONAL_FIELDS;
        } else {
            required = Inproceedings.REQUIRED_FIELDS;
            optional = Inproceedings.OPTIONAL_FIELDS;
        }
        
        in.nextLine();
        
        out.print("Mandatory field are marked with *\n");        
        for (String field : required) {
            String value = "";

            while(value.equals("")) {
                out.print(field+"*: ");
                value = in.nextLine();

                if (value.equals("")) {
                    out.print("Required field!\n");
                }
                
                if (field.equals("identifier") && 
                        !session.isUniqueIdentifier(value)) {
                    out.print("Not a unique identifier!\n");
                    value = "";
                }
            }

            fields.put(field, value);
        }

        for (String field : optional) {
            out.print(field+": ");
            String value = in.nextLine();

            fields.put(field, value);
        }

        out.print("\n");

        try {
            Reference ref;
            if (type == 1) {
                ref = new Article(fields);
            } else if (type == 2) {
                ref = new Book(fields);
            } else {
                ref = new Inproceedings(fields); 
            }
            
            session.add(ref);

            out.print("Reference added:\n");
            printReference(ref);

        } catch (IllegalArgumentException e) {
            out.print("Adding reference failed!\n");
        }
    }

    private void deleteReference() {
        out.print("Which reference will be deleted?\n> ");
        final String identifier = in.nextLine();
        try {
            session.delete(identifier);
        } catch (NoSuchElementException e) {
            out.println(e.getMessage());
            return;
        }
        out.println("Reference \"" + identifier + "\" deleted.");
    }
    
    private void importBibtext(){
        out.print("Give filename (unsaved references will be lost!):\n>");
        final String filename = in.nextLine();
        
        try {
            session.load(filename);
            out.print("References in \""+filename+"\" imported!");
        } catch (FileNotFoundException e) {
            out.print("File not found!\n");
        }catch (Exception e) {
            out.print("File corrupted!\n");
        }
    }

    private void exportBibtex() {
        out.print("Enter filename:\n> ");
        String filepath = in.nextLine();
        try {
            session.save(filepath);
            out.print("File exported to: " + filepath + "\n");
        } catch (IOException e) {
            out.print("Exporting bibtex file failed!\n");
        }
    }
}
