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

    private void previewReferences() {
        final ArrayList<Reference> references = session.getReferences();

        if (references.isEmpty()) {
            out.println("(No references.)");
            return;
        }

        for(final Reference ref : references) {
            out.println(ref.toPlaintextString());
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
            final Reference ref = session.getReference(identifier);

            for (final Map.Entry<String, String> entry :
                    ref.getData().entrySet()) {
                String field = entry.getKey();
                field += ref.isRequiredField(field) ? "*" : "";
                out.print(field + " (" + entry.getValue() + "): ");

                final String value = in.nextLine();
                if (value.equals(" ")) {
                    try {
                        ref.delete(entry.getKey());
                    } catch (final IllegalArgumentException e) {
                        out.println(e.getMessage());
                        return;
                    }
                } else if (!value.isEmpty()) {
                    ref.put(entry.getKey(), value);
                }
            }
        } catch (final NoSuchElementException e) {
            out.println(e.getMessage());
            return;
        }

        out.println("Reference \"" + identifier + "\" edited.");
    }

    private void addReference() {
        final LinkedHashMap<String, String> fields = new LinkedHashMap<>();

        out.print("1. Article\n2. Book\n3. Inproceedings\n> ");
        final int type = in.nextInt();

        final List<String> required, optional;
        switch (type) {
            case 1:
                required = Article.REQUIRED_FIELDS;
                optional = Article.OPTIONAL_FIELDS;
                break;
            case 2:
                required = Book.REQUIRED_FIELDS;
                optional = Book.OPTIONAL_FIELDS;
                break;
            case 3:
                required = Inproceedings.REQUIRED_FIELDS;
                optional = Inproceedings.OPTIONAL_FIELDS;
                break;
            default:
                out.println("A non-existent option \"" + type + "\" selected!");
                return;
        }

        in.nextLine();

        out.print("Mandatory field are marked with *\n");
        for (final String field : required) {
            String value = "";

            while (value.equals("")) {
                out.print(field + "*: ");
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

        for (final String field : optional) {
            out.print(field+": ");
            String value = in.nextLine();

            fields.put(field, value);
        }

        out.print("\n");

        try {
            final Reference ref;
            switch (type) {
                case 1:
                    ref = new Article(fields);
                    break;
                case 2:
                    ref = new Book(fields);
                    break;
                case 3:
                    ref = new Inproceedings(fields);
                    break;
                default:
                    // This cannot happen since the range of the value has
                    // already been checked.
                    throw new AssertionError();
            }

            session.add(ref);

            out.print("Reference added:\n");
            out.println(ref.toPlaintextString());

        } catch (final IllegalArgumentException e) {
            out.print("Adding reference failed!\n");
        } catch (final AssertionError f) {

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
        final String filepath = in.nextLine();
        try {
            session.save(filepath);
            out.print("File exported to: " + filepath + "\n");
        } catch (IOException e) {
            out.print("Exporting bibtex file failed!\n");
        }
    }
}
