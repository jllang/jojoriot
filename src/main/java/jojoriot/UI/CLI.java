/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jojoriot.UI;

import java.util.Scanner;

/**
 *
 * @author janne
 */
public final class CLI {
    
    Scanner scanner;
    
    public CLI()
    {
        scanner = new Scanner(System.in);
    };
    
    
   
    public void addReference() {
        System.out.println("Anna titteli:");
        String title = scanner.nextLine();
        
    }
    
    public void printMainScreen() {
        System.out.println("Bibtex-juttu");
        System.out.println("1. Lisää referenssi");
    };
    
}

