package ClientSide;

import GUI.GUI;
import ServerSide.Question;
import ServerSide.Waiting;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements ActionListener {

    GUI gui = new GUI();
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private int port = 55555;
    private String ip = "127.0.0.1";
    private Socket connection;

    String fromServer;
    Question question;
    String[] categories;

    boolean ready = false;
    boolean inGame = true;

    private Object objFromServer;

    private Client() {

        setupConnection();
        setupWriter();
        setupReader();

        waitOnOpponent();
        InGame();

    }

    private void setupConnection() {
        try {
            connection = new Socket(ip, port); //socket
        } catch (UnknownHostException e) {
            System.out.println("IP-adressen för ett värdnamn kunde inte fastställas");
        } catch (IOException e) {
            System.out.println("kunde inte kontakta server");
        }
    }
    private void setupWriter() {

        try {
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupReader() {

        try {
            in = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitOnOpponent() {
        while (true) {
            try {
                fromServer = (String) in.readObject(); //läser in titeln på gui
                if(fromServer.equals("player1")) {
                    gui.setTitle("Spelare 1");
                    break;
                }
                else if(fromServer.equals("player2")) {
                    gui.setTitle("Spelare 2");
                    break;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                break;
            }
        }
    }


    private void InGame() {

        while(inGame) {
            try {
                objFromServer = in.readObject();
                if (objFromServer instanceof Waiting) {
                    Waiting waiting = (Waiting) objFromServer;
                    if (waiting.getStatus().equalsIgnoreCase("wait")) {
                        gui.waitingForOpponent(waiting.getScore());
                    }
                }
                else if (objFromServer instanceof String[]) {			//Categories
                    gui.categorySetup((String[])objFromServer,this);
                    waitForReady();
                    gui.removeCategoryGUI();
                }
                else if (objFromServer instanceof Question) {			//Question
                    setupQuestion();
                    waitForReady();
                    gui.removeQuestionGUI();
                }
                else if (objFromServer instanceof int[]) {

                    int[] scores =  (int[]) objFromServer;
                    gui.setupScoreGUI(scores[0], scores[1]);
                    gui.getQuit().addActionListener(this);
                }
                else if (objFromServer == null) {
                    System.out.println("objFromServer is null");
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }

        }

    }

    private void setupQuestion() {

        question = (Question) objFromServer;

        gui.getOkButton().addActionListener(this);
        gui.getPanel3().setVisible(false);
        gui.getAnswerAlt();

        gui.getLabel1().setText(question.getQuestion());
        gui.getPanel1().revalidate();

        gui.setupQuestionGUI(question,this);


        gui.revalidate();
        ready = false;
    }

    private void waitForReady() {
        while(!ready) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            out.writeObject("ready");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ready = false;
    }


    public static void main(String[] args) {
        new Client();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // if source är en button, gå till den kategory man har tryckt på
        if (gui.getCategoryList().contains((e.getSource()))) {
            for (JButton b : gui.getCategoryList()) {
                if (e.getSource() == b) {
                    ButtonText(b);
                }
            }
            ready = true;
        }
        else if(gui.getAnswerAlt().contains((e.getSource()))) { // rätt svar sätts till grön

            for (JButton b : gui.getAnswerAlt()) {
                if (b.getText().equals(question.getCorrectAnswer())) {
                    b.setBackground(new java.awt.Color(0,255,51));
                    gui.getPanel2().repaint();
                }
                if (e.getSource() == b) {
                    if (!b.getText().equals(question.getCorrectAnswer())) { //fel svar sätts till röd, rätt svar sätts till grön
                        b.setBackground(new java.awt.Color(255,0,0));
                        gui.getPanel2().repaint();
                    }
                    ButtonText(b); // Man kan bara välja svar en gång
                    for (JButton bA : gui.getAnswerAlt()) {
                        bA.removeActionListener(this);
                    }
                    gui.getPanel3().setVisible(true);
                    gui.getPanel3().revalidate();
                }
            }
        }
        if (e.getSource() == gui.getOkButton()) { // gå vidare knappen
            gui.getPanel3().setVisible(false);
            gui.getPanel3().revalidate();
            ready = true;
        }
        if (e.getSource() == gui.getQuit()) {
            System.exit(0);
        }
    }


    private void ButtonText(JButton b) {  // Tar text från knapp och till playerhandler

        try {
            out.writeObject(b.getText()); out.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
