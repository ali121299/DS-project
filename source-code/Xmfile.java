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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author oy
 */
public class Xmfile {

    private String address , content , output_file_address = "";
    private String[] str;
    public ArrayList<String> labels;
    private boolean Is_compressed;

    public Xmfile() {
        Is_compressed = false;
        labels = new ArrayList<String>();
    }

    public Xmfile(String address) {
        this();
        this.address = address;
    }

    public void print() {
        for (String line : labels) {
            System.out.println(line);
        }
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

    public String opener() {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        address = filename;
        return filename;
    } // O(1)

    public void reader() throws FileNotFoundException {
        content = "";
        File file = new File(address);
        Scanner scan = new Scanner(file);
       //scan.nextLine();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if(line.length()>5 && line.substring(0, 5).equals("<?xml")) continue;
            content = content.concat(line.trim() + "\n");
        }
        str = content.split("\n");
    } // O(n) where n is number of lines in file
    
    public void consistency () throws IOException{
        String output;
        int line = valid();
        if(str.length+1==line) output = "xmfile is valid (error free).";
        else output = "xmfile is invalid error in line " + line + ".";
        writer(output , false);
    } // O(m) where m is number of words in file 

    private int valid() {
       
        Stack<String> stk = new Stack<>();
        int line = 1;
        for (String word : labels) {
            if(word.equals("\n")) line++;
            if ((Character.compare(word.charAt(0), '<') == 0)) // content[i]=='<'
            {
                if (!(Character.compare(word.charAt(1), '/') == 0)) {
                    stk.push(word.substring(1));
                } else if (!stk.empty() && stk.peek().equals(word.substring(2))) {
                    stk.pop();
                } else {
                    return line;
                }
            }
        }
        if(!stk.empty()) line++;
        return line;
    } // O(m) where m is number of words in file
    
    public void Error_corrector() throws IOException{
        Stack<String> stk = new Stack<>();
        for(int i=0 ; i<labels.size(); i++)
        {
            String word = labels.get(i);
            if ((Character.compare(word.charAt(0), '<') == 0)) // content[i]=='<'
            {
                if (!(Character.compare(word.charAt(1), '/') == 0)) {
                    stk.push(word.substring(1));
                } else if (!stk.empty() && stk.peek().equals(word.substring(2))) {
                    stk.pop();
                } else
                    {
                        while(!stk.empty() && !stk.peek().equals(word.substring(2)))
                        {
                            labels.add(i, "</"+stk.peek());
                            stk.pop();
                            ++i;
                        }
                        if(!stk.empty()) stk.pop();
                        else labels.remove(i);
                    }
            }
        }
        while(!stk.empty())  {labels.add("</"+stk.peek()); stk.pop();}
        rewrite();
    }
    
    private void rewrite() throws IOException{
        String update="";
        for(String word : labels)
        {
            if ((Character.compare(word.charAt(0), '<') == 0)) word +=">";
            update+=word;
        }
        writer(update, false);
    }  // O(m) where m is number of words in file

    private String node(char c, int pos, String which, int len) {
        String name = "";
        if((Character.compare(c, '>') == 0))
        {
            for (int i = pos; i < len; i++) {
                if (!( (Character.compare(which.charAt(i), c) == 0) || (Character.compare(which.charAt(i), ' ') == 0) ) ) {
                    name += which.charAt(i);
                } else {
                    break;
                }
            }
        }
        else 
        {
            for (int i = pos; i < len; i++) {
                if (!(Character.compare(which.charAt(i), c) == 0)) {
                    name += which.charAt(i);
                } else {
                    break;
                }
            }
        }
        return name;
    } // O(k) where k is number of characters in line

    public void expand() throws FileNotFoundException, IOException {
       // if (!Is_compressed) return;
        String spaces = "" , updated_content=""; 
        for(int i=0 ; i<labels.size(); i++)
        {
            String label = labels.get(i);
            if ((Character.compare(label.charAt(0), '<') == 0)){
                if (!(Character.compare(label.charAt(1), '/') == 0))
                    spaces += "    ";
                else
                {
                    if(labels.get(i-1).equals("\n"))   updated_content = updated_content.substring(0 , updated_content.length()-4);
                    spaces = spaces.replaceFirst("    ", "");
                }label+=">";
            }
            updated_content += label;
            if(label.equals("\n")) updated_content+=spaces;
        }
        writer(updated_content, false);
    } // O(m) where m is number of words in file

    public void compress() throws IOException {
        if (Is_compressed) {
            return;
        }
        String upadted_content = "";
        int i = 0;
        for (String line : str) {
            upadted_content = upadted_content.concat(line.trim());
        }
        writer(upadted_content, true);
        
    } // O(n) where n is number of lines in file

    public void writer(String upadted_content, boolean yes) throws IOException {
        FileWriter w = new FileWriter(output_file_address);
        w.write(upadted_content);
        w.close();
        Is_compressed = yes;
    } // O(1)

    public void parsing() {
        for (String line : str) {
            for (int i = 0; i < line.length(); i++) {
                String tag;
                if ((Character.compare(line.charAt(i), '<') == 0)) {
                    tag = node('>', i, line, line.length());
                    i += tag.length();
                    labels.add(tag);
                    while((Character.compare(line.charAt(i),' ') == 0))
                    {
                        String key , value;
                        key = "<";
                        key += node('=', i+1, line, line.length());
                        labels.add(key);
                        i += key.length()+1;
                        
                        value = node('"', i+1, line, line.length());
                        labels.add(value);
                        i += value.length()+2;
                        
                        key = "</" + key.substring(1);
                        labels.add(key);
                    }
                } else {
                    tag = node('<', i, line, line.length());
                    i += tag.length() - 1;
                    labels.add(tag);
                }
            }
                labels.add("\n");
        }
    } // O(Z) where z in number of characters in line

    public void display(javax.swing.JTextArea jTextArea2) {
        jTextArea2.setText("");
        try {
            // TODO add your handling code here:    
            File file = new File(output_file_address);
            Scanner scan = new Scanner(file);
            // scan.nextLine();
            while (scan.hasNextLine()) {
                jTextArea2.append(scan.nextLine() + "\n");
            }
            jTextArea2.requestFocus();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewJFrame1.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // O(n) where n is number of lines in file
    
    public void create_output_file(String filename){
        String [] arr = filename.split("\\\\");
        output_file_address = "";
        for(int i=0 ; i<arr.length-1 ; ++i)
            output_file_address += arr[i] + "\\";
        output_file_address += "XML-EDITOR-OUTPUT.txt";
        System.out.println(output_file_address);     
    }

}
