/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecomplexity;
import org.testng.Assert;
import org.testng.annotations.Test;   

/**
 *
 * @author Shehan
 */
public class TestSuite
{
    public TestSuite()
    {
        testMiscellaneousOperators();
        testLogicalOperators();
        testAssignmentOperators();
    }
    
    
    @Test
    public void testMiscellaneousOperators()
    {
        CodeComplexity testing = new CodeComplexity();
        String testInput =  "i->10 - >;" +
                            "i::5 : :;" +
                            "i.2;" +
                            "i,5;";
        
        Assert.assertEquals(testing.miscellaneousOperators(testInput), 4, "Miscellaneous Operator Test Failed");
        System.out.println("Miscellaneous Operator Test Successful");
    }
    
    @Test
    public void testLogicalOperators()
    {
        CodeComplexity testing = new CodeComplexity();
        String testInput =  "i&&10 & &=" +
                            "i||5 | |=" +
                            "!true i!=5";
        
        Assert.assertEquals(testing.logicalOperators(testInput), 3, "Logical Operator Test Failed");
        System.out.println("Logical Operator Test Successful");
    }
    
    @Test 
    public void testAssignmentOperators()
    {
        CodeComplexity testing = new CodeComplexity();
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
        
        Assert.assertEquals(testing.assignmentOperators(testInput), 12, "Assignment Operator Test Failed");
        System.out.println("Assignment Operator Test Successful");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args)
    {
        new TestSuite();
    }
}
