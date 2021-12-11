/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author oy
 */
public class Xmfile {
   static String content , tag;
   static int sz;
   static Stack<String> stk= new Stack<>();  
   static String [] str;
  
   
   public static void reader() throws FileNotFoundException
    {
        content="";
        str = null;
        File file = new File ("C:\\Users\\oy\\Desktop\\DS\\note2.txt");
        Scanner scan = new Scanner(file);
       // scan.nextLine();
        while(scan.hasNextLine())
            content = content.concat(scan.nextLine()+"\n");
        sz = content.length(); 
        str = content.split("\n");
    }
  
   
   
   
   public static boolean valid(String content)
   {
       
       for(int i=0 ; i<sz ; i++)
       {
           if((Character.compare(content.charAt(i),'<')==0)) // content[i]==
           {
               tag = node('>',i , content , sz);
               System.out.println(tag);
               if(!(Character.compare(tag.charAt(0) , '/')==0))
                   stk.push(tag);
               else if( !stk.empty() && stk.peek().equals(tag.substring(1)) )
                       stk.pop();
               else return false;
           }
       }
       return stk.empty();
   }
   
   
   
   
   private static String node (char c , int pos  , String which , int len)
   {
       String name="";
       for(int i=pos+1 ; i<sz ; i++)
           if(!(Character.compare(which.charAt(i),c)==0))
               name+=which.charAt(i);
           else break;
       return name;
   }
   
   
   
   
   public static void expand () throws FileNotFoundException, IOException
   {
       String spaces ="" , upadted_content = "";
       boolean flag = false;
       for(String line : str)
       {
           if(!line.equals(""))
           if((Character.compare(line.charAt(0),'<')==0) && (Character.compare(line.charAt(1),'/')==0))
           {
               spaces = spaces.replaceFirst("    ", "");
               flag = true;
           }
          upadted_content = upadted_content.concat(spaces + line +"\n");
          if(flag){spaces+="    "; flag=false;}
           int len = line.length();
           for(int i=0 ; i<len ; i++)
            {
                if((Character.compare(line.charAt(i),'<')==0)) // content[i]==
                {
                    tag = node('>',i , line , len);
                    if(!(Character.compare(tag.charAt(0) , '/')==0))
                        spaces+="    ";
                    else spaces = spaces.replaceFirst("    ", "");
                }
           }
           
       }
       FileWriter writer = new FileWriter("C:\\Users\\oy\\Desktop\\DS\\note2.txt");
       writer.write(upadted_content);
       writer.close();
   }
   
   
   
   
   public static void compress() throws IOException
   {
       String upadted_content = "";
       for(String line : str)
       {
           upadted_content = upadted_content.concat(line.trim()+"\n");
       }
       FileWriter writer = new FileWriter("C:\\Users\\oy\\Desktop\\DS\\note3.txt");
       writer.write(upadted_content);
       writer.close();
   }
   
}

