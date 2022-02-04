/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.*;

/**
 *
 * @author oy
 */
public class JsonOPerator {
    
    public static ArrayList<ArrayList<String>> Json_read(String location) throws FileNotFoundException, IOException, ParseException{
        
        ArrayList<ArrayList<String>> graph =new ArrayList<ArrayList<String>>();
        JSONParser jasonparser = new JSONParser();
        FileReader read = new FileReader(location);
        JSONObject jsonfile = (JSONObject)jasonparser.parse(read);
        JSONArray array = (JSONArray) jsonfile.get("users");
        for(int i=1 ; i<=array.size() ; i++){
            ArrayList<String> follower_List = new ArrayList<String>();
            JSONObject user = (JSONObject)array.get(i-1);
            JSONArray followers = (JSONArray) user.get("followers");
            for(int y=0 ; y<followers.size() ; y++){
                JSONObject follower = (JSONObject)followers.get(y);
                String follower_id = (String)follower.get("id");
                follower_List.add(follower_id);
            }graph.add(follower_List);
        }
        return graph;
    }
    
    
    public static String translate_graph_to_text (ArrayList<ArrayList<String>> graph){
        String text_representation="";
        for(int i=1 ; i<=graph.size() ; i++){
            text_representation += "followers of user" + i + " :" + "   ";
            for(int y=0 ; y<graph.get(i-1).size() ; y++)
                text_representation += graph.get(i-1).get(y) + "    ";
            text_representation += "\n";
        }return text_representation;
    }
    
    
}
