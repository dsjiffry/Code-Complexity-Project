/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecomplexity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

public class CodeComplexity
{
   public int Cs = 0;
   public boolean isComment = false;
   public int braces = 0;
   public boolean isloop = false;
   public boolean isJava = false;
   public HashMap<String, Integer> results = new HashMap<>(); 

   public CodeComplexity(){}
    
   public CodeComplexity(String path,String type)
   {
       try
       {
           if(type.equalsIgnoreCase("java"))
           {
               isJava = true;
               getEveryFile(path,"java");
           }
           else
           {
               getEveryFile(path,"cpp");
           }
           
       } catch (IOException e)
       {
           e.printStackTrace();
       }
   }



   /**
    * Identifying every file in a location by extension
    * @param path The folder which the method will scan recursively
    * @param extension The extension of the files to be scanned
    * @throws IOException
    */
   private void getEveryFile(String path, String extension) throws IOException
   {
       final String exten = extension.toLowerCase();

       Files.walk(Paths.get(path))
               .filter(p -> p.getFileName().toString().endsWith("."+exten))
               .forEach(this::forEveryFile);
   }

   /**
    * Identifying every line of the given file
    * @param path path to the file
    */
   private void forEveryFile(Path path)
   {
       Cs = 0;
       try (Stream<String> stream = Files.lines(Paths.get(path.toString()))) {
           stream.forEach(this::forEveryLine);
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
       
       results.put(path.getFileName().toString(), Cs);
   }

   /**
    * Used to execute methods on the string passed
    * @param line the code line on which the methods will be executed
    */
   private void forEveryLine(String line)
   {
       if(line.trim().isEmpty())
       {
           return;
       }
       if(isComment)
       {
           if (!line.contains("*/"))
           {
               return;
           }
           else
           {
               //multiline comment is over so reset isComment
               isComment = false;
               int end = line.indexOf("*/");
               line = line.substring(end,line.length());
           }
       }

       // Excluding import Statements
       if(line.contains("import"))
       {
           return;
       }

       // Excluding comments
       if(line.contains("//"))
       {
           int begin = line.indexOf("//");
           if(begin==0)
           {
               return;
           }
           line = line.substring(0,begin);
       }
       if(line.contains("/*"))
       {
           int begin = line.indexOf("/*");
           int end = line.indexOf("*/");
           if(end == -1)
           {
               //isComment true means the multiline comment is not over.
               isComment = true;
               return;
           }
           line = line.substring(0,begin) + line.substring(end,line.length());

       }
       if(line.contains("\""))
       {
           // TODO Identify and grade strings
       }


       //Calling All calculation methods
      Cs += miscellaneousOperators(line);
      Cs += logicalOperators(line);
      Cs += assignmentOperators(line);
      Cs += arithmeticOperators(line);
      Cs += relationalOperators(line);
      Cs += manipulators(line);
      Cs += bitwiseOperators(line);
      Cs += keywords(line);
      Cs += checkStrings(line);
               
      

   }

   
   //////////////////////////////////////////////// Identifing Operators  ////////////////////////////////////////////////
   
   /**
    * Identifies Miscellaneous operators in line of Code.
    * @param line The line to check
    * @return The number of points for Cs
    */
   protected int miscellaneousOperators(String line)
   {
       int total = 0;
       if(!isJava)  //Is C++
       {
            // Multiply by 2 since each operator is awarded 2 marks
            //Detecting &
            total = total + ((line.length() - line.replaceAll("(?<!&)&(?![&=])", "").length()))*2;
            //Detecting .
            total = total + ((line.length() - line.replaceAll("\\*(?!=)", "").length()))*2;  
       }
       
       //Detecting .
       total = total + ((line.length() - line.replaceAll("\\.", "").length()));
       //Detecting ,
       total = total + ((line.length() - line.replaceAll(",", "").length()));
       //Detecting ->
       total = total + ((line.length() - line.replaceAll("->", "").length())/2);
       //Detecting ::
       total = total + ((line.length() - line.replaceAll("::", "").length())/2);

       return total;
   }
   
   /**
    * Identifies Logical operators in line of Code.
    * @param line The line to check
    * @return The number of points for Cs
    */
   protected int logicalOperators(String line)
   {
       int total = 0;
       
       //Detecting &&
       total = total + ((line.length() - line.replaceAll("&&", "").length())/2);
       //Detecting ||
       total = total + ((line.length() - line.replaceAll("\\|\\|", "").length())/2);
       //Detecting !
       total = total + ((line.length() - line.replaceAll("!(?!=)", "").length()));


       return total;
   }
   
   /**
    * Identifies Assignment operators in line of Code.
    * @param line The line to check
    * @return The number of points for Cs
    */
   protected int assignmentOperators(String line)
   {
       int total = 0;
       
       //Detecting +=
       total = total + ((line.length() - line.replaceAll("\\+=", "").length())/2);
       //Detecting -=
       total = total + ((line.length() - line.replaceAll("\\-=", "").length())/2);
       //Detecting *=
       total = total + ((line.length() - line.replaceAll("\\*=", "").length())/2);
       //Detecting /= 
       total = total + ((line.length() - line.replaceAll("\\/=", "").length())/2);
       //Detecting = 
       total = total + ((line.length() - line.replaceAll("(?<![=\\+\\-\\*/!><%&^|])=(?![&=])", "").length()));
       //Detecting >>>=
       total = total + ((line.length() - line.replaceAll(">>>=", "").length())/4);
       //Detecting |=
       total = total + ((line.length() - line.replaceAll("\\|=", "").length())/2);
       //Detecting &=
       total = total + ((line.length() - line.replaceAll("&=", "").length())/2);
       //Detecting %=
       total = total + ((line.length() - line.replaceAll("%=", "").length())/2);
       //Detecting <<=
       total = total + ((line.length() - line.replaceAll("<<=", "").length())/2);
       //Detecting >>=
       total = total + ((line.length() - line.replaceAll("(?<!>)>>=", "").length())/2);
       //Detecting ^=
       total = total + ((line.length() - line.replaceAll("\\^=", "").length())/2);
       
       return total;
   }
  
   /**
    * Identifies manipulators in line of Code.
    * @param line The line to check
    * @return The number of points for Cs
    */
   protected int manipulators(String line)
   {
       int total = 0;
       line = line.replaceAll(" ", "");
       if(!isJava)  //Is C++
       {
           //Detecting 'cout<<'
           total = total + ((line.length() - line.replaceAll("cout<<", "").length())/6);
           //Detecting 'cin>>'
           total = total + ((line.length() - line.replaceAll("cin>>", "").length())/5);
       }
       return total;
   }
   
   /**
    * Identify arithmetic operators in a line of code
    * @param line The line to check
    * @return The number of points for Cs
    */
   protected int arithmeticOperators(String line)
   {
       int total = 0;
       
       //Detect +
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])[+](?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length()));
       //Detect -
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])[-](?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length()));
       //Detect *
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])[*](?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length()));
       //Detect /
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])[/](?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length()));
       //Detect %
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])[%](?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length()));
       //Detect ++
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])\\++(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length())/2);
       //Detect --
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])\\--(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length())/2);


       return total;
   }
   
   /**
    * identify relational operators in a line of code
    * @param line The line to check
    * @return The number of points for Cs
    */
   protected int relationalOperators(String line)
   {
       int total = 0;
        
       //Detect ==
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])==(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length())/2);
       //Detect !=
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])!=(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length())/2);
       //Detect >
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])>(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length()));
       //Detect <
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])<(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length()));
       //Detect >=
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])>=(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length())/2);
       //Detect <=
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])<=(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length())/2);

       return total;
   }
   
   protected int bitwiseOperators(String line){
       
       int total = 0;
       
       //Detect |
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])[|](?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length()));
       //Detect ^ Does not work needs to be fixed
       //total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])^(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length()));
       //Detect ~
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])[~](?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length()));
       //Detect <<
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])<<(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length())/2);
       //Detect >>
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])>>(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length())/2);
       //Detect <<<
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])<<<(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length())/3);
       //Detect >>>
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])>>>(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length())/3);

       
       return total;
   }
   
   protected int keywords(String line){
       
       int total = 0;
       
       //List of Keywords
       ArrayList<String> keyList = new ArrayList<>();
       Collections.addAll(keyList, "assert", "boolean", "break", "byte", "case", "catch",
               "char", "class", "continue", "default", "do", "double", "enum", "extends", "final", "finally", 
               "float", "for", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
               "null", "package", "private", "protected", "short", "strictfp", "super", "switch", 
               "synchronized", "this", "transient", "void", "volatile", "while");
       
       Iterator<String> itr = keyList.iterator();
       
       while (itr.hasNext()) { 
           String kw = itr.next();
           total = total + ((line.length() - line.replaceAll(kw, "").length()) / kw.length());
       }
              
       return total;
   }
   
   protected int checkStrings(String line){
       
       int total = 0;
       
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])\".*?(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length())/2);
       
       return total;
   }
 
   
   
   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   
   /*
        Regex Expressions Explained:
            !(?!=)                              '!' not followed by '='
            (?<!&)&(?![&=])                     '&' not preceded by a '&' or followed by characters { &= }
            \*(?!=)                             '*' not followed by a '='
            (?<![=\+\-\!/*><%&^|])=(?![&=])     '=' not preceded by characters { =\+\-\*!/><%&^|' } or followed by '='
            (?<!>)>>=                           '>>=' not preceded by '>'
        
   */
   
   
   
   
   
   
   
   
   
   
   
   
   
   /**
   * Used for directly passing code instead of directories
   * @param line The line of code to grade
   */
   public void codeOnly(String line)
   {
       forEveryLine(line);
   }
 
   public int getGrades()
   {
        return Cs;
   }
   
   public void resetGrades()
   {
        Cs = 0;
   }
   public HashMap<String, Integer> getResults()
   {
        return results;
   }
    
}
