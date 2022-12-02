package GUI;

import ClientSide.Client;
import ServerSide.Question;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GUI extends JFrame implements ActionListener {

    private JPanel panel1 = new JPanel();			//panel1 har en JLabel (dvs bara text)
    private JPanel panel2 = new JPanel();			//panel2 har svars- eller kategoriknappar
    private JPanel panel3 = new JPanel();			//panel3 har okButton eller Avsluta

    private JLabel label1 = new JLabel("Spelet Startar Strax");

    private JButton okButton = new JButton("Nästa fråga");
    private JButton quit = new JButton("Avsluta");

    private List<JButton> answerList = new ArrayList<>();	//Lista med svarsknappar
    private List<JButton> categoryList = new ArrayList<>();		//Lista med kategoriknappar


    Color[] c = {new Color(51,153,255),new Color(51,153,255), new Color(51,153,255), new Color(51,153,255)};

    public GUI(){

      setLayout(new GridLayout(3, 1));


        label1.setFont(new Font(label1.getFont().getName(), label1.getFont().getStyle(), 15));
        label1.setForeground(Color.white);
        panel1.add(label1);
        panel1.setBackground(new java.awt.Color(51, 204, 255));
        panel2.setBackground(new java.awt.Color(51,204,255));
        panel3.setBackground(new java.awt.Color(51, 204, 255));
        label1.setBackground(new java.awt.Color(51, 204, 255));


        this.getContentPane().setBackground(new java.awt.Color(51, 204, 255));
        this.setLocationRelativeTo(null);
        this.setSize(500,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        this.setBackground(new java.awt.Color(51, 204, 255));

        add(panel1); panel1.setVisible(true);
        add(panel2); panel2.setVisible(false);
        add(panel3); panel3.setVisible(false);

        setVisible(true);
    }

    public void waitingForOpponent(String score) {

        label1.setText("Motståndare spelar : " + "du har " + score + " poäng");
        label1.setForeground(Color.white);
        panel2.setVisible(false);
        panel3.setVisible(false);
        revalidate();
    }


    public void categorySetup(String[] categories, Client client) {
        label1.setText("Välj kategori");
        label1.setForeground(Color.white);
        panel2.setVisible(true);
        panel3.setVisible(false);
        /*boolean rund = true;*/

        panel2.setLayout(new GridLayout(2, 2));



        for (int i=0; i < categories.length; i++) {
            JButton b = new JButton();
            b.setFont(new Font("Poppins", Font.BOLD, 17));
            b.setText(categories[i]);

            b.setBackground(c[i]);
            /*b.setBorder(new LineBorder(new Color(51, 204, 255),5, rund));*/
            b.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
            b.addActionListener(client);
            categoryList.add(b);
            panel2.add(b);
        }

        revalidate();
    }
    public void removeCategoryGUI() {

        for (JButton b : categoryList) {
            panel2.remove(b);
        }

        panel2.revalidate();
        panel2.repaint();
    }

    public void setupQuestionGUI(Question question, Client client) {

        panel2.setVisible(true);
        panel3.setVisible(false);
        panel3.add(okButton);

        panel2.setLayout(new GridLayout(2, 2));

        //shuffle answers
        List<String> shuffledList = Arrays.asList(question.getAnswers());
        Collections.shuffle(shuffledList);

        for (int i=0; i < shuffledList.size(); i++) {
            JButton b = new JButton();
            b.setFont(new Font("Poppins", Font.BOLD, 17));
            b.setLayout(new FlowLayout());
            b.setForeground(Color.white);
            b.setBackground(new Color(102,102,102));
            /*b.setBorder(new SoftBevelBorder(BevelBorder.RAISED,new Color(204,204,204), new Color(51,51,51)));*/
            /*b.setBorder(BorderFactory.createLineBorder(new Color(51,204,255),5));*/
            b.setText(shuffledList.get(i));
            b.addActionListener(client);


            answerList.add(b);
            panel2.add (b);
        }

        revalidate();
    }
    public void removeQuestionGUI() {

        for (JButton b : answerList) {
            panel2.remove(b);

        }

        panel2.revalidate();
        panel2.repaint();
    }


    public void setupScoreGUI(int i1, int i2){

        panel2.setVisible(false);
        panel3.remove(okButton);
        panel3.add(quit);
        panel3.setVisible(true);

        label1.setFont(new Font("Poppins", Font.BOLD, 20));
        label1.setForeground(Color.white);
        label1.setText("Spelare 1: " + i1 + " poäng  -  Spelare 2: " + i2 + " poäng");
        revalidate();
        repaint();
    }


    public JPanel getPanel1() {
        return panel1;
    }

    public JPanel getPanel2() {
        return panel2;
    }

    public JPanel getPanel3() {
        return panel3;
    }

    public JLabel getLabel1() {
        return label1;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getQuit() {
        return quit;
    }

    public List<JButton> getCategoryList() {
        return categoryList;
    }

    public List<JButton> getAnswerAlt() {
        return answerList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
