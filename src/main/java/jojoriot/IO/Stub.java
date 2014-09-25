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
        return Integer.parseInt(inputs[i++]);
    }

    @Override
    public String readLine() {
        return inputs[i++];
    }
    
    public ArrayList<String> getPrints() {
        return prints;
    }
}
