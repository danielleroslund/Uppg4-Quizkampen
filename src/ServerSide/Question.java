package ServerSide;

import java.io.Serializable;

public class Question implements Serializable {

    private String question;
    private String a;
    private String b;
    private String c;
    private String d;
    private int correctAnswer;

    public Question(String question, String a, String b, String c, String d, int correctAnswer){
        this.question = question;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.correctAnswer = correctAnswer;
    }

    public String[] getAnswers(){
        return new String[]{a,b,c,d};
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        if(correctAnswer == 1){
            return a;
        }
        if(correctAnswer == 2){
            return b;
        }
        if(correctAnswer == 3){
            return c;
        }
        if(correctAnswer == 4){
            return d;
        }
        return "";
    }

}
