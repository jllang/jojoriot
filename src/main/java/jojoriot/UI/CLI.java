package jojoriot.UI;

import java.util.Scanner;

/**
 * 
 * @author janne
 */
public final class CLI implements UI {
    
    private final Scanner scanner;
    
    public CLI() {
        scanner = new Scanner(System.in);
    };
    
    @Override
    public void start() {
        System.out.println("Viitemanageri!");
        
        while (true) {
            System.out.print("\n1. Lisää viite\n2. Poistu\n> ");
            int command = Integer.parseInt(scanner.nextLine());
            
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
        System.out.print("Anna titteli: ");
        String title = scanner.nextLine();
    }
}
