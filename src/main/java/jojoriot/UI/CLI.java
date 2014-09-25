package jojoriot.UI;

import jojoriot.IO.IO;
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
            io.print("\n1. Lisää viite\n2. Poistu\n> ");
            int command = io.readInt();
            
            switch (command) {
                case 1:
                    addReference();
                    break;
                    
                case 2:
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
        
        // session.add(new Reference());
    }
}
