/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jojoriot.references;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Only tests for creating a new instance of Article class (which basicly
 * means testing checkValue and put methods).
 * Method tests are in ReferenceTest class.
 *
 * @author Jouko
 */
public class ArticleTest {
    
    Article art;
    LinkedHashMap<String, String> requiredFields;
    LinkedHashMap<String, String> optionalFields;
    
    public ArticleTest() {
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
    public void artikkelinLuontiOnnistuu(){
        requiredFields.put("author", "asd");
        requiredFields.put("title", "asd");
        requiredFields.put("journal", "asd");
        requiredFields.put("year", "asd");
        optionalFields.put("volume", "asd");
        
        art = new Article("asd", requiredFields, optionalFields);
        assertNotNull(art);
    }
    @Test
    public void artikkelinLuontiEpaonnistuuVaadittavaPuuttuu(){
        requiredFields.put("author", "asd");
        requiredFields.put("title", "asd");
        requiredFields.put("journal", "");
        requiredFields.put("year", "asd");
        
        try {
            art = new Article("asd", requiredFields, optionalFields);
        } catch (Exception e) {
        }
        
        assertNull(art);
    }
    @Test
    public void artikkelinLuontiEpaonnistuuVaaraKentanNimi(){
        requiredFields.put("authooor", "asd");
        requiredFields.put("title", "asd");
        requiredFields.put("journal", "");
        requiredFields.put("year", "asd");
        
        try {
            art = new Article("asd", requiredFields, optionalFields);
        } catch (Exception e) {
        }
        
        assertNull(art);
    }
    
}
