package ds;
 
import java.util.*;
 
/**
 *
 * @author oy
 */
public class Node {
    private String tag_value;
    private ArrayList<Node> children;
 
    public String getTag_value() {
        return tag_value;
    }
 
    public ArrayList<Node> getChildren() {
        return children;
    }
 
    public Node (){children = new ArrayList<Node>();}
    public Node(String tag_value){this(); this.tag_value = tag_value;}
 
    public void add_child (String tag)
    {
        Node n = new Node (tag);
        this.children.add(n);
    }
    public void add_child (Node tag)
    {
        this.children.add(tag);
    }
}
