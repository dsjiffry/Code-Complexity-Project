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

   public CodeComplexity(){}
    
   public CodeComplexity(String path)
   {
       try
       {
           getEveryFile(path,"java");
       } catch (IOException e)
       {
           e.printStackTrace();
       }
   }



   /**
    * Used for directly passing code instead of directories
    * @param line The line of code to grade
    */
    public void codeOnly(String line)
    {
        forEveryLine(line);
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
               isComment = true;
               return;
           }
           line = line.substring(0,begin) + line.substring(end,line.length());

       }


       //Calling All calculation methods
       Cs += miscellaneousOperators(line);
       Cs += conditionalCS(line);
   }

   /**
    * Identifies Miscellaneous operators in line of Code.
    * @param line The line to check
    * @return The number of points for Cs
    */
   private int miscellaneousOperators(String line)
   {
       int total = 0;
       total = (int) (total + line.chars().filter(ch -> ch =='.').count());
       total = (int) (total + line.chars().filter(ch -> ch ==',').count());

       //Detecting ->
       total = total + ((line.length() - line.replace("->", "").length())/2);

       //Detecting ::
       total = total + ((line.length() - line.replace("::", "").length())/2);


       return total;
   }

   /**
    * Identifying Conditional control structures in code
    * @param line The code line to check
    * @return The number of points for Ctc
    */
   private int conditionalCS(String line)
   {
       int total = 0;

       if(line.contains("if"))
       {
           total++;

           //Handling && and & characters
           total = total + ((line.length() - line.replace("&&", "").length())/2);
           line = line.replace("&&","");
           total = (int) (total + line.chars().filter(ch -> ch =='&').count());

           //Handling || and | characters
           total = total + ((line.length() - line.replace("||", "").length())/2);
           line = line.replace("||","");
           total = (int) (total + line.chars().filter(ch -> ch =='|').count());

       }
       return total;
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
