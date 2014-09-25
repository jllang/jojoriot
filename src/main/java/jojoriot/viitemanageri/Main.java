package jojoriot.viitemanageri;
import jojoriot.IO.Console;
import jojoriot.UI.CLI;

/**
 *
 * @author John Lång <jllang@cs.helsinki.fi>
 */
public class Main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Console io = new Console();
        Session session = new Session();
        CLI ui = new CLI(io, session);
        
        ui.start();
    }
}
