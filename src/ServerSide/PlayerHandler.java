package ServerSide;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

public class PlayerHandler {

    private Player p1, p2;

    private QuestionDatabase questionDatabase = new QuestionDatabase();

    private Question question;

    private Properties gameProperties;

    String category;
    String[] categoryChoices;
    int[] scores = new int[2];
    List<Question> fourQuestions;

    int rounds;
    int questionsPerRound;

    public PlayerHandler(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;

        propertiesLoad();

        p1.sendObject("player1");
        p2.sendObject("player2");
        p2.sendObject(new Waiting(String.valueOf(p2.getScore())));

        runGame();
        sendScore(p1,p2);
    }

    public void waitModel(Player p1, Player p2){
        String waiting = "waiting";
        p1.getScore();
        p2.getScore();
    }

    private void runGame(){ // Fixar hur ronderna ska startas beroende på jämt eller ojämt antal

        if(rounds > 1 && rounds % 2 == 0){
            for(int i = 0; i < rounds/2; i++){
                roundStartPlayer1();
                roundStartPlayer2();
            }
        } else if (rounds > 2 && rounds % 2 != 0) {
            for(int i = 0; i <(rounds - 1) / 2; i++){
                roundStartPlayer1();
                roundStartPlayer2();
            }
            roundStartPlayer1();
        }
        else{
            roundStartPlayer1();
            roundStartPlayer2();
        }
    }

    private void roundStartPlayer1(){ // Skickar kategori alternativ, hämtar frågor i vald kategori, visar score och skickar samma frågor till andra spelaren
        sendCategoryChoices(p1);
        sendQuestions(p1, category);

        p1.sendObject(new Waiting(String.valueOf(p1.getScore())));
        sendQuestions(p2);
    }

    private void roundStartPlayer2(){

        sendCategoryChoices(p2);
        sendQuestions(p2, category);

        p2.sendObject(new Waiting(String.valueOf(p2.getScore())));
        sendQuestions(p1);
    }

    private void sendCategoryChoices(Player p){ // Hämtar frågorna i form av Array, skickar den till clientsidan, tar emot svar
        categoryChoices = questionDatabase.getCategories();
        p.sendObject(categoryChoices);
        category = p.checkClickedButton();

        waitingForAnswerClick(p);

    }

    // Skickar frågorna en och en till spelaren som inte har fått välja kategori
    private void sendQuestions(Player p) {

        for(int i = 0; i < questionsPerRound; i++) {

            question = fourQuestions.get(i);

            p.sendObject(question);

            if (p.checkClickedButton().equals(question.getCorrectAnswer())) {
                p.scoreCounter();
            }

            waitingForAnswerClick(p);

        }
    }

    // här sätts frågorna i frågelistan efter att en spelare har valt kategori, sen skickas frågorna en och en
    private void sendQuestions(Player p, String category) {


        fourQuestions = questionDatabase.getQuestions(category);
        for(int i = 0; i < questionsPerRound; i++) {

            question = fourQuestions.get(i);

            p.sendObject(question);

            if (p.checkClickedButton().equals(question.getCorrectAnswer())) {
                p.scoreCounter();
            }

            waitingForAnswerClick(p);

        }
    }

    private void sendScore(Player p1, Player p2) {

        this.scores[0] = p1.getScore();
        this.scores[1] = p2.getScore();
        this.p1.sendObject(scores);
        this.p2.sendObject(scores);
    }

    //Kollar om spelaren har klickat på ett svars alternativ
    private void waitingForAnswerClick(Player p) {

        if (p.checkClickedButton().equals("ready")) {}
    }


    public void propertiesLoad() {
        Properties properties = new Properties();
        properties = new Properties();
        try{
            properties.load(new FileInputStream("src/ClientSide/GameSetup.properties"));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        String roundsString = String.valueOf(properties.getProperty("ROUNDS", "2"));
        String questionsPerRoundString = properties.getProperty("QUESTIONS","4");

        rounds = Integer.parseInt(roundsString);
        questionsPerRound = Integer.parseInt(questionsPerRoundString);

        if (questionsPerRound > 6 || questionsPerRound < 1) {
            questionsPerRound = 2;
        }

    }

}
