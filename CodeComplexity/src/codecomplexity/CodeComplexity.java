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
import java.util.stream.Stream;

public class CodeComplexity
{
   public int Cs = 0;
   public boolean isComment = false;
   public int braces = 0;
   public boolean isloop = false;
   public boolean isJava = false;

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

       System.out.println(path.getFileName()+": Cs -> "+Cs);
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


       //Calling All calculation methods
       Cs += miscellaneousOperators(line);
       Cs += LogicalOperators(line);
   }

   
   //////////////////////////////////////////////// Identifing Operators  ////////////////////////////////////////////////
   
   /**
    * Identifies Miscellaneous operators in line of Code.
    * @param line The line to check
    * @return The number of points for Cs
    */
   private int miscellaneousOperators(String line)
   {
       int total = 0;
       if(!isJava)  //Is C++
       {
            //Detecting &
            total = total + ((int)line.chars().filter(ch -> ch =='&').count())*2;
            //Detecting .
            total = total + ((int)line.chars().filter(ch -> ch =='*').count())*2;  
       }
       
       //Detecting .
       total = total + ((int)line.chars().filter(ch -> ch =='.').count());
       //Detecting ,
       total = total + ((int)line.chars().filter(ch -> ch ==',').count());
       //Detecting ->
       total = total + ((line.length() - line.replace("->", "").length())/2);
       //Detecting ::
       total = total + ((line.length() - line.replace("::", "").length())/2);

       return total;
   }
   
   /**
    * Identifies Logical operators in line of Code.
    * @param line The line to check
    * @return The number of points for Cs
    */
   private int LogicalOperators(String line)
   {
       int total = 0;
       
       //Detecting &&
       total = total + ((line.length() - line.replace("&&", "").length())/2);
       //Detecting ||
       total = total + ((line.length() - line.replace("||", "").length())/2);
       //Detecting !
       total = total + ((int)line.chars().filter(ch -> ch =='!').count());


       return total;
   }
   
   
   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   
   
   
   
   
   
   
   
   
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
    
}
