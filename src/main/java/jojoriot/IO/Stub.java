package jojoriot.IO;

import java.util.ArrayList;

/**
 *
 * @author riku
 */
public class Stub implements IO {
    private final ArrayList<String> prints;
    private final String[] inputs;
    private int i;
    
    public Stub(String... inputs) {
        this.inputs = inputs;
        i = 0;
        
        prints = new ArrayList<>();
    }
    
    @Override
    public void print(String string) {
        prints.add(string);
    }

    @Override
    public int readInt() {
        return Integer.parseInt(readLine());
    }

    @Override
    public String readLine() {
        if (i < inputs.length) {
            return inputs[i++];
        } else {
            return "";
        }
    }
    
    public ArrayList<String> getPrints() {
        return prints;
    }
}
