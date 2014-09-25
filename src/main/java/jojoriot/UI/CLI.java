package jojoriot.UI;

import jojoriot.IO.IO;
import jojoriot.references.Article;
import jojoriot.viitemanageri.Session;

/**
 * 
 * @author janne
 */
public final class CLI implements UI {
    
    private final IO io;
    private final Session session;
    
    public CLI(IO io, Session session) {
        this.io = io;
        this.session = session;
    };
    
    @Override
    public void start() {
        io.print("Viitemanageri!\n");
        
        while (true) {
            io.print("\n1. Add reference\n2. Preview references\n3. Exit\n> ");
            
            int command;
            try {
                command = io.readInt();
            } catch (NumberFormatException e) {
                return;
            }
            
            switch (command) {
                case 1:
                    addReference();
                    break;
                    
                case 2:
                    break;
                    
                case 3:
                    return;
            }
        }
    }
    
    public void addReference() {
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
        
        try {
            session.add(new Article(author, title, journal, year, volume,
                    number, pages, month, note, key));
            
            io.print("Reference added!\n");
        } catch (IllegalArgumentException e) {
            io.print("Adding reference failed!\n");
        }
    }
}
