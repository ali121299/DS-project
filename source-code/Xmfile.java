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

    private String address;
    private String[] str;
    public ArrayList<String> labels;
    private boolean Is_compressed;
    public int y;

    public Xmfile() {
        Is_compressed = false;
        labels = new ArrayList<String>();
    }

    public Xmfile(String address) {
        this();
        this.address = address;
    }

    public void print() {
        for (String line : str) {
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
    }

    public void reader() throws FileNotFoundException {
        String content = "";
        File file = new File(address);
        Scanner scan = new Scanner(file);
        //scan.nextLine();
        while (scan.hasNextLine()) {
            content = content.concat(scan.nextLine().trim() + "\n");
        }
        str = content.split("\n");
    } // create str

    public boolean valid() {
       
        Stack<String> stk = new Stack<>();
        for (String line : labels) {
            if ((Character.compare(line.charAt(0), '<') == 0)) // content[i]=='<'
            {
                String tag = node('>', 0, line, line.length());
                if (!(Character.compare(tag.charAt(1), '/') == 0)) {
                    stk.push(tag.substring(1));
                } else if (!stk.empty() && stk.peek().equals(tag.substring(2))) {
                    stk.pop();
                } else {
                    return false;
                }
            }
        }
        return stk.empty();
    }

    private String node(char c, int pos, String which, int len) {
        String name = "";
        for (int i = pos; i < len; i++) {
            if (!(Character.compare(which.charAt(i), c) == 0)) {
                name += which.charAt(i);
            } else {
                break;
            }
        }
        return name;
    }

    public void expand() throws FileNotFoundException, IOException {
        if (!Is_compressed) {
            return;
        }
        String spaces = "", upadted_content = "";
        boolean flag = false;
        int y = 0;
        for (String line : str) {
            if (!line.equals("")) {
                if ((Character.compare(line.charAt(0), '<') == 0) && (Character.compare(line.charAt(1), '/') == 0)) {
                    spaces = spaces.replaceFirst("    ", "");
                    flag = true;
                }
            }
            upadted_content = upadted_content.concat(spaces + line + "\n");
            //str[y++] = spaces+line;
            if (flag) {
                spaces += "    ";
                flag = false;
            }
            int len = line.length();
            for (int i = 0; i < len; i++) {
                if ((Character.compare(line.charAt(i), '<') == 0)) // content[i]==
                {
                    String tag = node('>', i, line, len);
                    if (!(Character.compare(tag.charAt(1), '/') == 0)) {
                        spaces += "    ";
                    } else {
                        spaces = spaces.replaceFirst("    ", "");
                    }
                }
            }
        }
        writer(upadted_content, false);
    }

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
    }

    public void writer(String upadted_content, boolean yes) throws IOException {
        FileWriter w = new FileWriter("C:\\Users\\oy\\Desktop\\DS\\js.txt");
        w.write(upadted_content);
        w.close();
        Is_compressed = yes;
    }

    public void parsing() {
        for (String line : str) {
            for (int i = 0; i < line.length(); i++) {
                String tag;
                if ((Character.compare(line.charAt(i), '<') == 0)) {
                    tag = node('>', i, line, line.length());
                    i += tag.length();
                } else {
                    tag = node('<', i, line, line.length());
                    i += tag.length() - 1;
                }
                labels.add(tag);
            }
        }
    } // create labels

    public void display(javax.swing.JTextArea jTextArea2) {
        jTextArea2.setText("");
        try {
            // TODO add your handling code here:    
            File file = new File("C:\\Users\\oy\\Desktop\\DS\\js.txt");
            Scanner scan = new Scanner(file);
            // scan.nextLine();
            while (scan.hasNextLine()) {
                jTextArea2.append(scan.nextLine() + "\n");
            }
            jTextArea2.requestFocus();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewJFrame1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
