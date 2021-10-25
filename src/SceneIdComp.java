import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SceneIdComp implements Comparator<Posting> {


    @Override
    public int compare(Posting p1, Posting p2){
        if (p1.getSceneNum() == p2.getSceneNum()) {
            return 0;
        }
        else if (p1.getSceneNum() > p2.getSceneNum()) {
            return 1;
        }
        else if (p1.getSceneNum() < p2.getSceneNum()) {
            return -1;
        }
        return -1;
    }
}
