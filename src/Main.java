import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        InvertedIndex index = new InvertedIndex();
        JSONParser parser = new JSONParser();
        Path path = Paths.get("src/shakespeare-scenes.json");
        path = path.toAbsolutePath();
        ArrayList<Integer> sceneSize = new ArrayList<Integer>();

        Object obj = parser.parse(new FileReader(String.valueOf(path)));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray objs = (JSONArray) jsonObject.get("corpus");
        Iterator<JSONObject> iterator = objs.iterator();
        while (iterator.hasNext()) {
            JSONObject innerObj = (JSONObject) iterator.next();
            String playId = (String) innerObj.get("playId");
            String sceneId = (String) innerObj.get("sceneId");
            int sceneNum = (int)(long) innerObj.get("sceneNum");
            String text = (String) innerObj.get("text");
            String[] tokenized = text.split(" ");


            sceneSize.add(tokenized.length);
            for(int i=0; i < tokenized.length; i++){
                index.addWord(tokenized[i], i, sceneNum, sceneId, playId);
            }
            if(tokenized.length == 47){
                System.out.println(sceneId);
            }
            if(tokenized.length == 8002){
                System.out.println(sceneId);
            }


        }

        int avg = 0;
        for(int size: sceneSize){
            avg += size;
        }
        avg = avg / sceneSize.size();
        System.out.println("Average size: " + avg);
        System.out.println("Smallest size: " + Collections.min(sceneSize));
        System.out.println("Largest size: " + Collections.max(sceneSize));

        //HashMap<String, ArrayList<Posting>> in = index.getIndex();

        //ArrayList<String> venice = index.searchByPhrase("venice");
        //ArrayList<String> rome = index.searchByPhrase("rome");
        //ArrayList<String> denmark = index.searchByPhrase("denmark");
        //ArrayList<String> soldier = index.searchByPhrase("soldier");
        //ArrayList<String> goneril = index.searchByPhrase("goneril");
        //ArrayList<String> letSlip = index.searchByPhrase("let slip");
        //ArrayList<String> wherefore = index.searchByPhrase("wherefore art thou romeo");
        //ArrayList<String> poorYorick = index.searchByPhrase("poor yorick");
        //ArrayList<String> theOrThou = index.theOrThou();

       //File textFile = new File("phrase2.txt");
        //FileWriter writer = new FileWriter("phrase2.txt");

       // for(String s: letSlip){
       //     writer.write(s + "\n");
       // }

       // writer.close();
/*
        ArrayList<Posting> test = new ArrayList<Posting>();
        test.add(new Posting("test", 2,2, "test scene","test play"));
        test.add(new Posting("test", 1,6, "test scene","test play"));
        test.add(new Posting("test", 5,8, "test scene","test play"));

        Posting searchKey = new Posting("test", 0,8,"bla","bla");

        int index1 = Collections.binarySearch(test,searchKey, new SceneIdComp());
        System.out.println(index1);
*/
    }
}
