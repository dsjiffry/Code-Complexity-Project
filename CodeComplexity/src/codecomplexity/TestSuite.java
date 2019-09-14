/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecomplexity;

public final class TestSuite
{
    public CodeComplexity testing;
    public TestSuite()
    {
        testing = new CodeComplexity();
        
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
        
        //Sprint 2
        testConditionalControlStructure();
        testnestingControlStructure();
        testswitchControlStructure();
        testtrycatchStructure();
        testIterativeControlStructure();
    }
    
    
    public void testMiscellaneousOperators()
    {
        String testInput =  "i->10 - >;" +
                            "i::5 : :;" +
                            "i.2;" +
                            "i,5;";
        
        assertCheck(testing.miscellaneousOperators(testInput) == 4);
        System.out.println("Miscellaneous Operator Test Successful");
    }
    
    public void testLogicalOperators()
    {
        String testInput =  "i&&10 & &=" +
                            "i||5 | |=" +
                            "!true i!=5";
        
        assertCheck(testing.logicalOperators(testInput) ==  3);
        System.out.println("Logical Operator Test Successful");
    }
    
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
        
        assertCheck(testing.assignmentOperators(testInput) == 12);
        System.out.println("Assignment Operator Test Successful");
    }
    

    public void testManipulators()
    {
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
        String testInput =  "public void setBooks();\n" +
                            "assert(setBooks())\n" + 
                            "enum\n" + 
                            "continue:\n" +
                            "transient\n"+
                            "new;\n"+
                            "println\n";
        
        testing.isJava = true;
        assertCheck(testing.keywords(testInput) == 7);
        System.out.println("Keywords Test Successful");
    }
    
    public void testStrings(){
        String testInput =  "System.out.println(\"Tis is a Strin\");\n" + 
                            "String bookname = \"Twilight\";\n";
        
        assertCheck(testing.checkStrings(testInput) == 2);
        System.out.println("Strings Test Successful");
    }
    
    public void testArithmaticOperators(){
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
        String testInput =  "public int add() {\n"
        		+ "2+3=5;\n"
        		+ "4*5=20;\n"
        		+ "and20Puls30 = 50;\n"
        		+ "}";
        
        assertCheck(testing.numbers(testInput) == 7);
        System.out.println("Find Numbers Test Successful");
    }
    
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
        
        assertCheck(testing.identifiers(testInput) == 14);
        System.out.println("Find identifiers Test Successful");
    }
    
    
    
    
//****************************************************************************************************************************
//--------------------------------------------------------- Sprint 2 ---------------------------------------------------------
//****************************************************************************************************************************    
    
    public void testConditionalControlStructure(){
        String testInput =  "if( a>b && a<c)\n" +
                            "if(a>b&&a<c)\n" +
                            "if( a>b || a<c)\n" +
                            "if(a>b||a<c)\n" +
                            "if(i & 1)\n" +
                            "if(i&1)\n" +
                            "if(i | 1)\n" +
                            "if(i|1)" ;
        
        assertCheck(testing.conditionalControlStructure(testInput) == 16);
        System.out.println("Conditional Control Structure Test Successful");
    }
    
    public void testnestingControlStructure()
    {
        String testInput =  "while (x <= 4) \n" +
                            "for (int x = 2; x <= 4; x++) \n" +
                            "if(a>b)" ;
        assertCheck(testing.nestingControlStructure(testInput) == 3);   
        System.out.println("Nesting Control Structure Test Successful");
    }
    
    public void testswitchControlStructure()
    {
        String testInput =  "switch(n){ \n" +
                            "case 1: System.out.println(n);\n" +
                            "case 2: System.out.println(n+2);\n" ;
        assertCheck(testing.switchControlStructure(testInput) == 2);   
        System.out.println("Switch Control Structure Test Successful");
    }
    
    public void testtrycatchStructure()
    {
        String testInput =  "try { \n" +
                            "accessFiles();}\n" +
                            "catch(FileNotFoundException e){\n" +
                            "System.out.println(\"File Not found\");}";
        assertCheck(testing.trycatchStructure(testInput) == 1);   
        System.out.println("Try Catch Structure Test Successful");
    }
    
    public void testIterativeControlStructure(){
        String testInput =  "for( a>b && a<c)\n" +
                            "while(a>b&&a<c)\n" +
                            "do { do something }" + 
                            "while( a>b || a<c)\n" ;
        
        assertCheck(testing.iterativeControlStructure(testInput) == 12);
        System.out.println("Iterative Control Structure Test Successful");
    }
    
    
//****************************************************************************************************************************
//--------------------------------------------------------- Methods ---------------------------------------------------------
//****************************************************************************************************************************
    
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