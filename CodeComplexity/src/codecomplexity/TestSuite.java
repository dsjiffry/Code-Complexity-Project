/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecomplexity;

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
        testManipulators();
        testBitwise();
        testKeywords();
        testStrings();
        testArithmaticOperators();
        testRelationalOperators();
        testIdentifiers();
        testNumbers();
    }
    
    
    public void testMiscellaneousOperators()
    {
        CodeComplexity testing = new CodeComplexity();
        String testInput =  "i->10 - >;" +
                            "i::5 : :;" +
                            "i.2;" +
                            "i,5;";
        
        assertCheck(testing.miscellaneousOperators(testInput) == 4);
        System.out.println("Miscellaneous Operator Test Successful");
    }
    
    public void testLogicalOperators()
    {
        CodeComplexity testing = new CodeComplexity();
        String testInput =  "i&&10 & &=" +
                            "i||5 | |=" +
                            "!true i!=5";
        
        assertCheck(testing.logicalOperators(testInput) ==  3);
        System.out.println("Logical Operator Test Successful");
    }
    
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
        
        assertCheck(testing.assignmentOperators(testInput) == 12);
        System.out.println("Assignment Operator Test Successful");
    }
    

    public void testManipulators()
    {
        CodeComplexity testing = new CodeComplexity();
        String testInput =  "i += 10;\n" +
                            "cout<< 5;\n" +
                            "cin>> 2;\n" +
                            "cout << 5;\n" +
                            "cin >> 2;\n";
        
        assertCheck(testing.manipulators(testInput) == 4);
        System.out.println("Manipulators Test Successful");
    }
    
    public void testBitwise()
    {
        CodeComplexity testing = new CodeComplexity();
        String testInput =  "i | 10;\n" +
                            "cout ~ 5;\n" +
                            "cin << 2;\n" +
                            "cin ^ 2;\n" +
                            "cout >> 5;\n" +
                            "cin <<< 2;\n" +
                            "cin >>> 2;\n"    ;
        
        assertCheck(testing.bitwiseOperators(testInput) == 7);
        System.out.println("Bitwise Test Successful");
    }
    
    public void testKeywords(){
         CodeComplexity testing = new CodeComplexity();
        String testInput =  "public void setBooks();\n" +
                            "assert(setBooks())\n" + 
                            "enum\n" + 
                            "continue:\n" +
                            "transient\n"
                            ;
        
        assertCheck(testing.keywords(testInput) == 5);
        System.out.println("Keywords Test Successful");
    }
    
    public void testStrings(){
         CodeComplexity testing = new CodeComplexity();
        String testInput =  "System.out.println(\"Tis is a Strin\");\n" + 
                            "String bookname = \"Twilight\";\n";
        
        assertCheck(testing.checkStrings(testInput) == 2);
        System.out.println("Strings Test Successful");
    }
    
    public void testArithmaticOperators(){
        CodeComplexity testing = new CodeComplexity();
        String testInput =  "i + 5;\n" +
                            "i - 5;\n" +
                            "i * 5;\n" +
                            "i / 5;\n" +
                            "i % 10;\n" +
                            "i++ ;\n" +
                            "i-- ;\n" ;
        
        assertCheck(testing.arithmeticOperators(testInput) == 7);
        System.out.println("Arithmatic Operators Test Successful");
    }
    
    public void testRelationalOperators(){
        CodeComplexity testing = new CodeComplexity();
        String testInput =  "i == 5;\n" +
                            "i != 5;\n" +
                            "i > 5;\n" +
                            "i < 5;\n" +
                            "i <= 10;\n" +
                            "i >= 10 ;\n" ;
        
        assertCheck(testing.relationalOperators(testInput) == 6);
        System.out.println("Relational Operators Test Successful");
    }
    
    
    public void testNumbers(){
        CodeComplexity testing = new CodeComplexity();
        String testInput =  "public int add() {\n"
        		+ "2+3=5;\n"
        		+ "4*5=20;\n"
        		+ "and20Puls30 = 50;\n"
        		+ "}";
        
        //assertCheck(testing.Numbers(testInput, CodeSizeConstrants.PROGRAM_TYPE_JAVA) == 7);
        System.out.println("Find Numbers Test Successful");
    }
    
    public void testIdentifiers(){
        CodeComplexity testing = new CodeComplexity();
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
        
        //assertCheck(testing.identifiers(testInput, CodeSizeConstrants.PROGRAM_TYPE_JAVA) == 14);
        System.out.println("Find identifiers Test Successful");
    }
    
    
    
    
    
    
    
    //*******************************************************************************************************************************
    // Methods
    
    /**
     * To avoid passing -ea in VM arguments
    */
    public void assertCheck(boolean bool)
    {
        if(!bool)
        {
            //Getting the calling methods name from stacktrace
            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[2];
            String methodName = e.getMethodName();
            
            
            System.err.println("Assertion Fail in "+methodName);
            System.exit(-1);
        }
    }
    
    public static void main(String[] args)
    {
        new TestSuite();
    }
}