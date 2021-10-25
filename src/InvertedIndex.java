import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class InvertedIndex {

    private HashMap<String, ArrayList<Posting>> index;

    public InvertedIndex(){
        index = new HashMap<String, ArrayList<Posting>>();
    }

    public void addWord(String word,int position,int sceneNum, String sceneID, String playID){
        //If word is in collection, search posing list
        if(index.containsKey(word)){
            ArrayList<Posting> postingListPointer = index.get(word);
            Posting searchKey = new Posting(word ,position,sceneNum,sceneID,playID);
            //The scene number does not exist in the posting list
            int positionOfPosting = Collections.binarySearch(postingListPointer,searchKey, new SceneIdComp());
            //The posting for this scene number exists in the set of Postings for this word
            if(positionOfPosting >= 0){
                Posting pointer = postingListPointer.get(positionOfPosting);
                pointer.addPosition(position);

            }
            else{  //Is not contained in list of postings so it is added
                postingListPointer.add(searchKey);
            }
        }
        else{ //If word is not in collection make a posting list for it
            index.put(word, new ArrayList<Posting>());
            index.get(word).add(new Posting(word, position, sceneNum, sceneID, playID));
        }
    }

    public ArrayList<String> searchByPhrase(String phrase){
        phrase = phrase.toLowerCase();
        String[] words = phrase.split(" ");
        //System.out.println(words[0]);
        int lengthToBeValid = words.length;
        ArrayList<Posting>[] candidate = new ArrayList[words.length];
        //Getting posting lists for words in phrase
        for(int i=0; i < words.length ; i++){
            if(index.containsKey(words[i])){
                candidate[i] = new ArrayList<Posting>();
                candidate[i] = index.get(words[i]);
            }
            else {
                System.out.println("word does not exists in index: " + words[i]);
                System.out.println("Therefore there is not a match that exists ");
                return null; //WORD DOES NO EXISTS IN INDEX
            }
            }
        //The size of matches will be as large as the first word of the phrase
        ArrayList<Posting>[] findPhrase = new ArrayList[candidate[0].size()];



        //Puts first word of the first posting list in the find phrase
        for(int l=0; l < candidate[0].size(); l++){
            //Posting temp = candidate[0].get(l);
            findPhrase[l] = new ArrayList<Posting>();
            findPhrase[l].add(candidate[0].get(l));
        }




        //Base iteration for building of posting list of matches
        //Also serves as the position offset
        for(int i=1; i < words.length; i++){
            //Iterate though each of the possible phrases
            for(int j=0; j < findPhrase.length; j++ ){
                int docId = findPhrase[j].get(0).getSceneNum();
                //Matching based on sceneID
                int posOfMatch = Collections.binarySearch(candidate[i],findPhrase[j].get(0), new SceneIdComp());
                if (posOfMatch >= 0) {
                    //Match for scene found that are the same scene ID
                    ArrayList<Integer> positionsOfMatch = candidate[i].get(posOfMatch).getWordPositions();
                    ArrayList<Integer> positionsOfBase = findPhrase[j].get(0).getWordPositions();
                    for(int pos: positionsOfBase){
                        if(positionsOfMatch.contains(pos + i)){
                            findPhrase[j].add(candidate[i].get(posOfMatch));
                            //System.out.println("Sucsessfully adding addition");
                            break;
                        }
                    }
                }
                else{
                    //no match found for that scene ID
                    continue;
                }

            }
        }

        ArrayList<String> scenesThatAreValid = new ArrayList<String>();
        for(int i=0; i < findPhrase.length; i++){
            if(findPhrase[i].size() == words.length){
                scenesThatAreValid.add(findPhrase[i].get(0).getSceneID());
                System.out.println(findPhrase[i].get(0).getSceneID() + " SceneNum: " + findPhrase[i].get(0).getSceneNum() + " POS: " + findPhrase[i].get(0).getWordPositions().get(0));
            }
        }
        return scenesThatAreValid;




        /* Old Code start
        ArrayList<Posting> baseList = index.get(words[0]); //Make a list of postings that the first word is contained in, the set of phrases that exists must be in this set.


        for(int i = 1; i < words.length; i++){ //Go through the phrase, this is the position offset for comparing positions
            ArrayList<Posting> nextWord = index.get(words[i]);
            for(int j=0; j < baseList.size(); j++){
                Posting curr = baseList.get(j);
                int scene = curr.getSceneNum();
                //Checking to see if the scene num exists in the set of next word
                int positionOfPosting = Collections.binarySearch(nextWord,curr, new SceneIdComp());
                if(positionOfPosting >= 0){
                    Posting existInNextWord = nextWord.get(positionOfPosting);
                    ArrayList<Integer> compareSet = existInNextWord.getWordPositions();
                    ArrayList<Integer> baseSet = curr.getWordPositions();
                    boolean flag = false;
                    for(int l = 0; l < compareSet.size(); l++){
                        if(baseSet.contains(compareSet.get(l) - i)){ //Check to see if there exists a word in the position at the specified location
                            flag = true;
                        }
                    }
                    if(!flag){
                        baseList.remove(j); //There exists these words but not in the position they need to be
                    }
                }
                else{
                    //Does not exists in same scene ID
                    baseList.remove(j);
                }
            }
        }
        for(int i=0; i < baseList.size(); i++){
            System.out.println(baseList.get(i).getPlayID());
        }
        Old Code end */
    }

    public ArrayList<String> theOrThou(){
        ArrayList<String> scenesThatMatch = new ArrayList<String>();
        ArrayList<Posting> thee;
        ArrayList<Posting> thou;
        ArrayList<Posting> you;
        if(index.containsKey("the") && index.containsKey("thou") && index.containsKey("you")){
            thee = index.get("thee");
            thou = index.get("thou");
            you = index.get("you");
        }
        else{
            return null;
        }
        int numOccOfTheeThou;
        for(int i=0; i < you.size(); i++){
            int docId = you.get(i).getSceneNum();
            int pos1 = Collections.binarySearch(thee,you.get(i), new SceneIdComp());
            int pos2 = Collections.binarySearch(thou,you.get(i), new SceneIdComp());
            numOccOfTheeThou = 0;
            if(pos1 >= 0 && pos2 >= 0){
                numOccOfTheeThou = thee.get(pos1).getNumOcc();
                numOccOfTheeThou += thou.get(pos2).getNumOcc();
            } else if(pos1 >= 0 && pos2 <= 0){
                numOccOfTheeThou = thee.get(pos1).getNumOcc();
            } else if(pos1 <= 0 && pos2 >= 0){
                numOccOfTheeThou = thou.get(pos2).getNumOcc();
            }else{
                continue;
            }
            if(numOccOfTheeThou > you.get(i).getNumOcc()){
                scenesThatMatch.add(you.get(i).getSceneID());
            }
        }
        for(String s: scenesThatMatch){
            System.out.println(s);
        }
        return scenesThatMatch;
    }

    public HashMap<String, ArrayList<Posting>> getIndex(){
        return index;
    }

    public int getNumOcc(String word){
        if(index.containsKey(word)){
            ArrayList<Posting> postings = index.get(word);
            int count = 0;
            for(Posting p: postings){
                count += p.getNumOcc();
            }
            return count;
        }
        else{
            return -1;
        }
    }


}

