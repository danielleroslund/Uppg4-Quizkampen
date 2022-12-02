package ServerSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player {

    private Socket connection;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    protected int score = 0;

    private String answer;
    private String checkAnswer;

    Player(Socket socket){
        connection = socket;

        try{
            out = new ObjectOutputStream(connection.getOutputStream()); out.flush();
            in = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    protected void sendObject(Object object){
        try{
            out.writeObject(object);
            out.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    protected String checkClickedButton(){
        checkAnswer = answer;
        try{
            while(true){
                answer = (String)in.readObject();
                if(!answer.equals(checkAnswer)){
                    break;
                }
            }
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(0);
        }

        return answer;
    }

    protected void scoreCounter(){
        score++;
    }

    protected int getScore(){
        return score;
    }


}
