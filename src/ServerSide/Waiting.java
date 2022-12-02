package ServerSide;

import java.io.Serializable;

public class Waiting implements Serializable {

    private String score;

    public Waiting(String score){
        this.score = score;
    }

    public String getStatus(){
        return "wait";
    }

    public String getScore(){
        return score;
    }

}
