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
    private String  address;
    private String [] str ;
    public ArrayList<String>labels;
    private boolean Is_compressed;
    public int y;

   
   public Xmfile(){Is_compressed = false; labels = new ArrayList<String>();}
   public Xmfile(String address){this();this.address = address;}

   public void reader() throws FileNotFoundException
    {
        String content="";
        File file = new File (address);
        Scanner scan = new Scanner(file);
       // scan.nextLine();
        while(scan.hasNextLine())
            content = content.concat(scan.nextLine()+"\n");
        str = content.split("\n");
    }
  
   public boolean valid()
   {
        y = 1;
       Stack<String> stk= new Stack<>(); 
       for(String line : str)
       {
           for(int i=0 ; i<line.length() ; i++)
           if((Character.compare(line.charAt(i),'<')==0)) // content[i]=='<'
           {
               String tag = node('>',i , line , line.length());
               if(!((Character.compare(tag.charAt(0) , '<')==0) && (Character.compare(tag.charAt(1) , '/')==0)))
                    stk.push(tag.substring(1));
               else if( !stk.empty() && stk.peek().equals(tag.substring(2)) )
                    stk.pop();
               else return false;
           }
           y++;
       }
       return stk.empty();
   }
   
   private String node (char c , int pos  , String which , int len)
   {
       String name="";
       for(int i=pos ; i<len ; i++)
           if(!(Character.compare(which.charAt(i),c)==0))
               name+=which.charAt(i);
           else break;
       return name;
   }
   
   public void expand () throws FileNotFoundException, IOException
   {
       if(!Is_compressed) return;
       String spaces ="" , upadted_content = "";
       boolean flag = false;
       int y=0;
       for(String line : str)
       {
           if(!line.equals(""))
           if((Character.compare(line.charAt(0),'<')==0) && (Character.compare(line.charAt(1),'/')==0))
           {
               spaces = spaces.replaceFirst("    ", "");
               flag = true;
           }
          upadted_content = upadted_content.concat(spaces + line +"\n");
          //str[y++] = spaces+line;
          if(flag){spaces+="    "; flag=false;}
           int len = line.length();
           for(int i=0 ; i<len ; i++)
            {
                if((Character.compare(line.charAt(i),'<')==0)) // content[i]==
                {
                    String tag = node('>',i , line , len);
                    if(!(Character.compare(tag.charAt(1) , '/')==0))
                        spaces+="    ";
                    else spaces = spaces.replaceFirst("    ", "");
                }
           }
       }
       writer(upadted_content , false);
   }
   
   public void compress() throws IOException
   {
       if(Is_compressed) return;
       String upadted_content = "";
       int i=0;
       for(String line : str)
       {
           upadted_content = upadted_content.concat(line.trim()+"\n");
           str[i++] = line.trim();
       }
       writer(upadted_content , true);
   }
   
   public void writer(String upadted_content , boolean yes) throws IOException
   {
       FileWriter w= new FileWriter("C:\\Users\\oy\\Desktop\\DS\\js.txt");
       w.write(upadted_content);
       w.close();
       Is_compressed = yes;
   }
   
   public void print()
   {
       for(String line : str)
           System.out.println(line);
   }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIs_compressed(boolean Is_compressed) {
        this.Is_compressed = Is_compressed;
    }

    public String getAddress() {
        return address;
    }

    public boolean isIs_compressed() {
        return Is_compressed;
    }
   
    public void parsing ()
    {
        for(String line : str)
        {
            for(int i=0 ; i<line.length() ; i++)
            {
                String tag;
                if((Character.compare(line.charAt(i),'<')==0))
                {
                    tag = node('>' , i , line , line.length()); 
                    i+=tag.length();
                }
                else
                {
                    tag = node('<' , i , line , line.length());
                    i+=tag.length()-1;
                }
                labels.add(tag);
            }
        }
    }
    
}

