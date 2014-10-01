/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jojoriot.references;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for methods in abstract Reference class
 * excluding put and checkValue.
 *
 * @author Jouko
 */
public class ReferenceTest {
    
    Article art;
    LinkedHashMap<String, String> requiredFields;
    LinkedHashMap<String, String> optionalFields;
    
    public ReferenceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        requiredFields = new LinkedHashMap<String, String>();
        optionalFields = new LinkedHashMap<String, String>();
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void bibTextinLuontiOnnistuu(){
        requiredFields.put("author", "asd");
        requiredFields.put("title", "asd");
        requiredFields.put("journal", "asd");
        requiredFields.put("year", "asd");
        
        art = new Article("asd", requiredFields, optionalFields);
        
        assertEquals("@Article{asd,\nauthor = {asd},\ntitle = {asd},\njournal = {asd},\nyear = {asd},\n}", art.toBibtexString());
        
    }
    
    public class ReferenceImpl extends Reference {

        public ReferenceImpl() {
            super("", null, null);
        }

        public void checkvalue(String key, String value) throws IllegalArgumentException {
        }
    }
    
}
