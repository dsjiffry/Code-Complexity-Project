/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecomplexity;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Shehan
 */
public class TestNGSuite
{
    private CodeComplexity testing;

    public TestNGSuite()
    {
        testing = new CodeComplexity();
    }
    
    
    
    @Test
    public void testMiscellaneousOperators()
    {
        String testInput =  "i->10 - >;" +
                            "i::5 : :;" +
                            "i.2;" +
                            "i,5;";
        
        assertEquals(testing.miscellaneousOperators(testInput), 4);
    }
    
    @Test
    public void testLogicalOperators()
    {
        String testInput =  "i&&10 & &=" +
                            "i||5 | |=" +
                            "!true i!=5";
        
        assertEquals(testing.logicalOperators(testInput), 3);
    }
    
    @Test
    public void testAssignmentOperators()
    {
        String testInput =  "i += 10;\n" +
                            "i -= 5;\n" +
                            "i *= 2;\n" +
                            "i /= 5;\n" +
                            "i = 500;\n" +
                            "i != 500;\n" +
                            "i>>>=344;\n" +
                            "i |= 45;\n" +
                            "i &= 4;\n" +
                            "i %=6;\n" +
                            "i<<= 90;\n" +
                            "i >>=34;\n" +
                            "i^=9;";
        
        assertEquals(testing.assignmentOperators(testInput), 12);
    }
    

    @Test
    public void testManipulators()
    {
        String testInput =  "i += 10;\n" +
                            "cout<< 5;\n" +
                            "cin>> 2;\n" +
                            "cout << 5;\n" +
                            "cin >> 2;\n";
        testing.isJava = false;
        assertEquals(testing.manipulators(testInput), 4);
    }
    
    @Test
    public void testBitwise()
    {
        String testInput =  "i | 10;\n" +
                            "cout ~ 5;\n" +
                            "cin << 2;\n" +
                            "cin ^ 2;\n" +
                            "cout >> 5;\n" +
                            "cin <<< 2;\n" +
                            "cin >>> 2;\n";
        
        assertEquals(testing.bitwiseOperators(testInput), 7);
    }
    
    @Test
    public void testKeywords()
    {
        String testInput =  "public void setBooks();\n" +
                            "assert(setBooks())\n" + 
                            "enum\n" + 
                            "continue:\n" +
                            "transient\n";
        
        assertEquals(testing.keywords(testInput), 5);
    }
    
    @Test
    public void testStrings(){
        String testInput =  "System.out.println(\"Tis is a Strin\");\n" + 
                            "String bookname = \"Twilight\";\n";
        
        assertEquals(testing.checkStrings(testInput), 2);
    }
    
    @Test
    public void testArithmaticOperators(){
        String testInput =  "i + 5;\n" +
                            "i - 5;\n" +
                            "i * 5;\n" +
                            "i / 5;\n" +
                            "i % 10;\n" +
                            "i++ ;\n" +
                            "i-- ;\n" ;
        
        assertEquals(testing.arithmeticOperators(testInput), 7);
    }
    
    @Test
    public void testRelationalOperators(){
        String testInput =  "i == 5;\n" +
                            "i != 5;\n" +
                            "i > 5;\n" +
                            "i < 5;\n" +
                            "i <= 10;\n" +
                            "i >= 10 ;\n" ;
        
        assertEquals(testing.relationalOperators(testInput), 6);
    }
    
    @Test
    public void testNumbers(){
        String testInput =  "public int add() {\n"
        		+ "2+3=5;\n"
        		+ "4*5=20;\n"
        		+ "and20Puls30 = 50;\n"
        		+ "}";
        
        assertEquals(testing.Numbers(testInput), 7);
    }
    
    @Test
    public void testIdentifiers(){
        testing.isJava = true;
        String testInput =  "public class AddTwoNumbers {\r\n" + 
        		"\r\n" + 
        		"			   public static void main(String[] args) {\r\n" + 
        		"			        \r\n" + 
        		"			      int num1 = 5, num2 = 15, sum;\r\n" + 
        		"			      sum = num1 + num2;\r\n" + 
        		"\r\n" + 
        		"			      System.out.println(sum);\r\n" + 
        		"			   }\r\n" + 
        		"			};  " ;
        
        assertEquals(testing.identifiers(testInput), 14);
    }  

}
