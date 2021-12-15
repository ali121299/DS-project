public class Converter {
    public static Tree xml_to_tree (ArrayList<String> labels)
    {
        Tree xml_tree = new Tree();
        Stack<Node> stk = new Stack<>();
        for(String label : labels)
        {
            if((Character.compare(label.charAt(0),'<')==0))
                if(!(Character.compare(label.charAt(1),'/')==0))
                {
                   Node tag = new Node(label); 
                   if(!stk.empty()) stk.peek().add_child(tag);
                   else xml_tree.add_root(tag);
                   stk.push(tag);
                }
                else stk.pop();
            else {Node tag = new Node(label); stk.peek().add_child(tag);}
        }
        return xml_tree;
    }
 
 
}
