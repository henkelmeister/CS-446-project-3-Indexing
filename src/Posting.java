import java.util.ArrayList;

public class Posting {

    private String playID;
    private String sceneID;
    private String word;
    private int numOcc;
    private int sceneNum;
    private ArrayList<Integer> wordPositions;

    public Posting(String word, int position,int sceneNum, String sceneID, String playID){
        this.word = word;
        this.sceneID = sceneID;
        this.sceneNum = sceneNum;
        this.playID = playID;
        this.numOcc = 1;
        this.wordPositions = new ArrayList<Integer>();
        wordPositions.add(position);
    }

    public String getWord(){
        return word;
    }

    public String getSceneID(){
        return sceneID;
    }

    public String getPlayID(){
        return playID;
    }

    public int getNumOcc(){
        return wordPositions.size();
    }

    public void incNumOcc() {
        numOcc++;
    }

    public int getSceneNum(){
        return sceneNum;
    }

    public void addPosition(int pos){
        wordPositions.add(pos);
    }

    public ArrayList<Integer> getWordPositions(){
        return wordPositions;
    }



}
