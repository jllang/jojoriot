/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jojoriot.viitemanageri;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author John Lång <jllang@cs.helsinki.fi>
 */
public class MainTest {

    /**
     * Virta, johon testattavan Main-luokan komentorivitulosteet ohjataan.
     */
    private static ByteArrayOutputStream standardiulostulo;
    // Myöhemmin voidaan ehkä tarvita myös tätä:
//    private static ByteArrayOutputStream virheulostulo;

    public MainTest() {
    }

    @BeforeClass
    public static void setupClass() {
        standardiulostulo   = new ByteArrayOutputStream();
//        virheulostulo       = new ByteArrayOutputStream();
    }

    /**
     * Uudelleenohjaa System.outin staattisessa kentässä <em>standardiulostulo
     * </em> olevaan virtaan, jotta ohjelman komentorivitulosteita voidaan
     * testata.
     */
    private static void aloitaKaappaus() {
        System.setOut(new PrintStream(standardiulostulo));
//        System.setErr(new PrintStream(virheulostulo));
    }

    /**
     * Poistaa System.outin uudelleenohjauksen.
     */
    private static void lopetaKaappaus() {
        System.setOut(null);
//        System.setErr(null);
    }

    @Test
    public void testMain() {
        // Teen aluksi epäonnistuvan testin testatakseni systeemiä:
        aloitaKaappaus();
        Main.main(null);
//        assertEquals("Hoi maailma!", standardiulostulo.toString().trim());
        // Ja sitten nopea korjaus:
        assertEquals("Hei maailma!", standardiulostulo.toString().trim());
        lopetaKaappaus();
    }

}
