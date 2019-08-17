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
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.stream.Stream;



public class CodeComplexity
{
   public int Cs = 0;
   public boolean isComment = false;
   public int braces = 0;
   public boolean isloop = false;
   public boolean isJava = false;
   public HashMap<String, Integer> results = new HashMap<>(); 
   
   /*HashSet used to identify if a word is a keyword in */
   private HashSet<String> keyWordSet = new HashSet<String>();

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
           // Checking strings and then removing them.
           Cs += checkStrings(line);
           int begin = line.indexOf("\"");
           line = line.substring(0,begin);
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
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])\\^(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length()));
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
       if (isJava) {
           ArrayList<String> jkeyList = new ArrayList<>();
           Collections.addAll(jkeyList, "assert", "boolean", "break", "byte", "case", "catch",
                   "char", "class", "continue", "default", "do", "double", "enum", "extends", "final", "finally",
                   "float", "for", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
                   "null", "package", "private", "protected", "short", "strictfp", "super", "switch",
                   "synchronized", "this", "transient", "void", "volatile", "while");

           Iterator<String> itr = jkeyList.iterator();

           while (itr.hasNext()) {
               String kw = itr.next();
               total = total + ((line.length() - line.replaceAll(kw, "").length()) / kw.length());
           }
       }
       else{
           ArrayList<String> ckeyList = new ArrayList<>();
           Collections.addAll(ckeyList, "break","long",
			"switch","case","enum","register","typedef","char","extern",
			"return","union","const","float","short","unsigned","continue",
			"for","signed","void","default","goto","sizeof","volatile","do",
			"if","while","asm","dynamic_cast","namespace",
			"reinterpret_cast","bool","explicit","static_cast",
			"catch","false","operator","template","class","friend",
			"private","this","const","cast","inline",
			"delete","mutable","protected","true","typeid","typename",
			"using","virtual","wchar_t");

           Iterator<String> itr = ckeyList.iterator();

           while (itr.hasNext()) {
               String kw = itr.next();
               total = total + ((line.length() - line.replaceAll(kw, "").length()) / kw.length());
           }
       }
              
       return total;
   }
   
   protected int checkStrings(String line){
       
       int total = 0;
       
       total = total + ((line.length() - line.replaceAll("(?<![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])\".*?(?![\\=\\<\\>\\!\\+\\-\\?\\|\\@\\#\\$\\%\\^\\&\\*\\/])", "").length())/2);
       
       return total;
   }
   
   

	public int Numbers(String line) {
		int programType = (isJava)?0:2;//set program type 0 for java and, 2 for C++.
		int numberCount = 0;
		String character = " ";
		// add a non variable value in-case first character is a number
		line = "#" + line;
		/*Get character value and remove it form line String*/
		character = String.valueOf(line.charAt(0));
		line = line.substring(1);
		
		//Loop to access iterate through line ends when line is empty.
		while(!line.isEmpty()) {
			//is character NOT the start of a identifier.
			if(!Pattern.matches(CodeSizeConstrants.VARIABLE_CHAR[programType],character)) {
				/*Get character value and remove it form line String*/
				character = String.valueOf(line.charAt(0));
				line = line.substring(1);
				//is character a number.
				if(Pattern.matches("[0-9.]",character)){
					//loop to find end of number.
					while(Pattern.matches("[0-9.]",character)) {
						
						//end loop if line is empty
						if(line.isEmpty()) {
							break;
						}
						/*Get character value and remove it form line String*/
						character = String.valueOf(line.charAt(0));
						line = line.substring(1);	
					}
					numberCount++;
					
					//end loop if line is empty
					if(line.isEmpty())
						break;				
					
				}
			}else{
				/*Get character value and remove it form line String*/
				character = String.valueOf(line.charAt(0));
				line = line.substring(1);
			}
			
			
		}
		int count = numberCount;
		return count;
	}
	
	public int identifiers(String line) {
		int programType = (isJava)?0:2;//set program type 0 for java and, 2 for C++.
		String character = " "; 	//holds Temporary character value used with regex statement.
		int namesCount = 0;  		//counter increased when an identifiers.
		String word = ""; 			//word String hold an word value used to identify keywords.
		initKeyWordSet(programType); //fill keyWordSet with keyword of given program type. 
		//Loop to access iterate through line ends when line is empty.
		while(!line.isEmpty()) {
			
			/*Get character value and remove it form line String*/
			line.trim();
			character = String.valueOf(line.charAt(0));
			line = line.substring(1);
			 /*is character the start of a identifier*/
			 if(Pattern.matches(CodeSizeConstrants.VARIABLE_START_WITH[programType],character)){
	
				word =  word + character;//add character to word
				
				/*Get character value and remove it form line String*/
				character = String.valueOf(line.charAt(0));
				line = line.substring(1);
				
				/*loop that check if the next character is part of 
				* the identifier and then adds to word */
				while(Pattern.matches(CodeSizeConstrants.VARIABLE_CHAR[programType],character)) {
						
					word =  word + character;//add character to word
					/*Get character value and remove it form line String*/
					character = String.valueOf(line.charAt(0));
					line = line.substring(1);
				}
				
				//If word is keyword reset word.
				if(keyWordSet.contains(word)) {
					word="";//Reset word value to empty. 
				}else {
					/*If word is not a keyword 
					reset word and increase namesCount.*/
					namesCount++;
					word="";	
				}
			 }
		 }
		 return namesCount;
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
   
   /*this method initializes the keyWordSet HashSet with values 
	 * of keywordList array*/
	private void initKeyWordSet(int programType) {
		for(String key : CodeSizeConstrants.KEY_WORD_LIST[programType])
			keyWordSet.add(key);
	}
    
}
