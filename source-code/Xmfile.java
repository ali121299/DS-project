/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *
 * @author oy
 */
public class Xmfile {
   static String content ;
   static int sz;
   static Stack<String> stk= new Stack<>();  
   public Xmfile(){content=""; sz=0;}
   public static void reader() throws FileNotFoundException
    {
        content="";
        File file = new File ("C:\\Users\\oy\\Desktop\\DS\\note.txt");
        Scanner scan = new Scanner(file);
       // scan.nextLine();
        while(scan.hasNextLine())
            content = content.concat(scan.nextLine()+"\n");
        sz = content.length(); 
    }
  
   public static boolean valid(String content)
   {
       String tag;
       for(int i=0 ; i<sz ; i++)
       {
           if((Character.compare(content.charAt(i),'<')==0)) // content[i]==
           {
               tag = node('>',i);
               if(!(Character.compare(tag.charAt(0) , '/')==0))
                   stk.push(tag);
               else if( !stk.empty() && stk.peek().equals(tag.substring(1)) )
                       stk.pop();
               else return false;
           }
       }
       return stk.empty();
   }
   
   private static String node (char c , int pos )
   {
       String name="";
       for(int i=pos+1 ; i<sz ; i++)
           if(!(Character.compare(content.charAt(i),c)==0))
               name+=content.charAt(i);
           else break;
       return name;
   }
   
   
}

