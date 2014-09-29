package jojoriot.IO;

import java.io.IOException;

/**
 * An Input/Output object that is responsible for I/O operations.
 *
 */
public interface IO {

    /**
     * Prints a string to the output without line break.
     *
     * @param string The string to be printed.
     * @throws java.io.IOException
     */
    void print(String string) throws IOException;

    /**
     * Reads a line from the input stream and parses it as an integer.
     *
     * @return
     * @throws java.io.IOException
     */
    int readInt() throws IOException;

    /**
     * Reads a line from the input stream to a string.
     *
     * @return
     * @throws java.io.IOException
     */
    String readLine() throws IOException;
}
