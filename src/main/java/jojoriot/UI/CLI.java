package jojoriot.UI;

import java.util.Scanner;
import jojoriot.IO.IO;

/**
 * 
 * @author janne
 */
public final class CLI implements UI {
    
    private final IO io;
    
    public CLI(IO io) {
        this.io = io;
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
        io.print("Anna titteli: ");
        String title = io.readLine();
    }
}
