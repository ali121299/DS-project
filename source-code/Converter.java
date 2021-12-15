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
    
    
    
 public static String tree_to_jason(Node root , String content , String spaces)
    {
        spaces+="    ";
        content = content.concat(spaces + "\"" + root.getTag_value().substring(1) + "\":");
        boolean Is_children_repeated = children_repeated(root.getChildren());
        boolean Is_value = Is_child_value(root.getChildren());
 
        if(!Is_value && !Is_children_repeated)
        { 
           content += "{\n";
           content = call_children(root.getChildren(), content, spaces);
           content = content.concat( spaces + "        " + "}" );
        }
 
 
        else if(Is_children_repeated)
        {
            content += "[\n";
            ArrayList<Node> grand_children = new ArrayList<Node>();
            spaces+="    ";
            if(!Is_child_value(root.getChildren().get(0).getChildren()))
            {
                int number_of_children = root.getChildren().size() , child_number=1; 
                for(Node kid: root.getChildren() )
                {
                    content = content.concat(spaces + "{\n");
                    grand_children = kid.getChildren();
                    content = call_children(grand_children, content, spaces);
                    content = content.concat("\n" + spaces + "}" );
                    if(child_number++<number_of_children) content += ",";
                    content+="\n";
                }
            }
            else 
            {
                int number_of_children = root.getChildren().size() , child_number=1; 
                for(Node kid: root.getChildren() )
                {
                    content = content.concat( spaces + "\"" + kid.getChildren().get(0).getTag_value() + "\"" );
                    if(child_number++<number_of_children) content += ",";
                    content+="\n";
                }
            }
            content = content.concat("\n" + spaces + "        ]" );
        }
 
 
        else if (Is_value) {content =  content.concat("\"" + root.getChildren().get(0).getTag_value() + "\"");}
        return content;
    }
 
    
    
   
    
}
