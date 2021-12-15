package ds;
 
/**
 *
 * @author oy
 */
public class Tree {
 
    public Node root;
    public Tree(){root = null;}
    public Tree (String root_data){ root = new Node(root_data);}  
 
    public void add_root (Node root){this.root = root;}
    public static void print(Node root)
    {
        System.out.println(root.getTag_value());
        for(Node n : root.getChildren())
        {
            print(n);
        }
    }
}
