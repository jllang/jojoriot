package jojoriot.UI;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import jojoriot.references.Article;
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
                    + "\n5. Exit"
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
                    out.print("Thank you for using Viitemanageri!");
                    return;
                default:
                    out.print("Unknown command.");
            }
        }
    }

    private void printReference(final Reference ref) {
        final Map<String, String> referenceData = ref.getData();

        for(final Map.Entry<String, String> entry : referenceData.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                out.print("    " + entry.getKey() + ": " + entry.getValue()
                        + "\n");
            }
        }
    }

    private void previewReferences() {
        final ArrayList<Reference> references = session.getReferences();

        for(final Reference ref : references) {
            printReference(ref);
        }
    }

    public void previewBibtext(){
        final ArrayList<Reference> references = session.getReferences();

        for(final Reference ref : references) {
            out.print(ref.toBibtexString() + "\n");
        }
    }

    public void addReference() {
        String identifier = "";
        LinkedHashMap<String, String> fields = new LinkedHashMap<>();

        out.print("Mandatory field are marked with *\n");

        while(identifier.equals("")){
            out.print("identifier*: ");
            identifier = in.nextLine();

            if (identifier.equals("")) {
                out.print("Required field!\n");
            }
        }

        for(String field : Article.REQUIRED_FIELDS) {
            String value = "";

            while(value.equals("")) {
                out.print(field+"*: ");
                value = in.nextLine();

                if (value.equals("")) {
                    out.print("Required field!\n");
                }
            }

            fields.put(field, value);
        }

        for(String field : Article.OPTIONAL_FIELDS) {
            out.print(field+": ");
            String value = in.nextLine();

            fields.put(field, value);
        }

        out.print("\n");

        try {
            Article article = new Article(identifier, fields);

            session.add(article);

            out.print("Reference added:\n");
            printReference(article);

        } catch (IllegalArgumentException e) {
            out.print("Adding reference failed!\n");
        }
    }
    
    public void exportBibtex() {
        out.print("Enter filename:\n> ");
        String filepath = in.nextLine();
        try {
            session.export(filepath);
            out.print("File exported to: " + filepath + "\n");
        } catch (IOException e) {
            out.print("Exporting bibtex file failed!\n");
        }
    }
}
