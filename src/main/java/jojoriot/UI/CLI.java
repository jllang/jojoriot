package jojoriot.UI;

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
            out.print("\n1. Add reference\n2. Preview references\n3. Exit\n> ");

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
            out.print(entry.getKey() + ": " + entry.getValue() + "\n");
        }
    }

    public void previewReferences() {
        final ArrayList<Reference> references = session.getReferences();

        for(final Reference ref : references) {
            printReference(ref);
            out.print("\n");
        }
    }

    public void addReference() {


        LinkedHashMap<String, String> requiredFields = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> optionalFields = new LinkedHashMap<String, String>();

        for(String field : Article.REQUIRED_FIELDS) {

            String value = "";

            while(value.equals("")) {
                out.print(field+"*: ");
                value = in.nextLine();

                if (value.equals("")) {
                    out.print("Required field!\n");
                }
            }

            requiredFields.put(field, value);
        }

        for(String field : Article.OPTIONAL_FIELDS) {
            out.print(field+": ");
            String value = in.nextLine();

            optionalFields.put(field, value);
        }


        out.print("\n");

        Article art = null;

        try {

            art = new Article(requiredFields, optionalFields);

            session.add(art);

            out.print("Reference added:\n");
            printReference(art);

        } catch (IllegalArgumentException e) {
            out.print("Adding reference failed!\n");

        }


        /*
        io.print("Author: ");
        String author = io.readLine();

        io.print("Title: ");
        String title = io.readLine();

        io.print("Journal: ");
        String journal = io.readLine();

        io.print("Year: ");
        String year = io.readLine();

        io.print("Volume: ");
        String volume = io.readLine();

        io.print("Number: ");
        String number = io.readLine();

        io.print("Pages: ");
        String pages = io.readLine();

        io.print("Month: ");
        String month = io.readLine();

        io.print("Note: ");
        String note = io.readLine();

        io.print("Key: ");
        String key = io.readLine();

                */

        /*
        io.print("\n");

        Article art = null;
        boolean failed = false; // Used for something?

        try {

            art = new Article(author, title, journal, year, volume,
                    number, pages, month, note, key);

            session.add(art);

            io.print("Reference added:\n");
            printReference(art);

        } catch (IllegalArgumentException e) {
            io.print("Adding reference failed!\n");

        }

                */
    }
}
