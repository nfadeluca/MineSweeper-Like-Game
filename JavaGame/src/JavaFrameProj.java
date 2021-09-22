package window;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import sun.audio.*;
import java.io.*;

public class JavaFrameProj {

    public static void main(String[] args) {

        DisplayFrame game = new DisplayFrame();

    }

    public static void playBG() {
        try {
            File file = new File(".\\bgmusic" + ".wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
            Thread.sleep(clip.getMicrosecondLength());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}

class DisplayFrame {

    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JPanel gameOver = new JPanel();
    JTextField score = new JTextField();
    JButton [] buttons = new JButton[210];

    public static void playExplosion() {
        try {
            File file = new File(".\\explosion" + ".wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
            Thread.sleep(1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void setUpMenu(JFrame frame) {

        JPanel menu = new JPanel();
        frame.add(menu);
        menu.setBackground(Color.BLACK);
        menu.setLayout(null);

        JLabel title = new JLabel("MINE");
        JLabel title1 = new JLabel("SWEEPER");
        title.setFont(new Font("Bahnschrift", Font.BOLD, 60));
        title1.setFont(new Font("Bahnschrift", Font.BOLD, 80));
        title.setForeground(Color.GREEN);
        title1.setForeground(Color.GREEN);
        title.setBounds(285,150,500,150);
        title1.setBounds(180,230,500,200);
        menu.add(title);
        menu.add(title1);

        JButton play = new JButton();
        play.setIcon(new javax.swing.ImageIcon((".\\play.png")));
        play.setFont(new Font("Bahnschrift", Font.BOLD, 30));
        play.setBounds(280,430,160,95);
        menu.add(play);

        play.addActionListener(new ActionListener(){@Override public void actionPerformed(ActionEvent e){
            frame.remove(menu);
            frame.add(panel);
            panel.updateUI();
        }});

    }

    public void setUpGameOver(JPanel panel) {

        panel.setBackground(Color.BLACK);
        JLabel overScore = new JLabel("Your Score: "+score.getText());
        overScore.setForeground(Color.GREEN);
        panel.add(overScore);
        overScore.setBounds(215,240,500,150);
        overScore.setFont(new Font("Bahnschrift", Font.BOLD, 50));

        JLabel gameOverText = new JLabel("Game Over");
        panel.add(gameOverText);
        gameOverText.setBounds(240,120,300,150);
        gameOverText.setFont(new Font("Bahnschrift", Font.BOLD, 50));
        gameOverText.setForeground(Color.GREEN);

        JLabel retryText = new JLabel("Retry?");
        panel.add(retryText);
        retryText.setBounds(310,360,300,150);
        retryText.setFont(new Font("Bahnschrift", Font.BOLD, 40));
        retryText.setForeground(Color.GREEN);

        JButton yes = new JButton("YES");
        JButton no = new JButton("NO");
        yes.setFont(new Font("Bahnschrift", Font.BOLD, 20));
        no.setFont(new Font("Bahnschrift", Font.BOLD, 20));
        yes.setBounds(220,540,130,88);
        yes.setIcon(new javax.swing.ImageIcon((".\\yes.png")));
        no.setBounds(400,540,130,88);
        no.setIcon(new javax.swing.ImageIcon((".\\no.png")));
        panel.add(yes);
        panel.add(no);

        yes.addActionListener(new ActionListener(){@Override public void actionPerformed(ActionEvent e){
            frame.dispose();
            new DisplayFrame();
        }});
        no.addActionListener(new ActionListener(){@Override public void actionPerformed(ActionEvent e){
            frame.dispose();
            System.exit(1);
        }});

    }

    public void fillButtons(JPanel panel){

        int width = 50, height = 50;
        int x=0, y=0;

        for(int i=0;i<210;i++){

            if (i%15==0) {y+=50;x=0;}
            JButton button = new JButton();
            buttons[i]= button;

            button.setIcon(new javax.swing.ImageIcon((".\\unknownbox.png")));
            panel.add(button);

            int chanceBomb = (int)(java.lang.Math.random()*15);
            button.addActionListener(new ActionListener(){@Override public void actionPerformed(ActionEvent e){

                if (chanceBomb != 1) {
                    button.setVisible(false);
                    score.setText(String.valueOf(Integer.parseInt(score.getText())+1));
                } else if (chanceBomb==1) {
                    button.setIcon(new javax.swing.ImageIcon((".\\bombbox.png")));
                    button.setBorderPainted(false);
                }
                if (chanceBomb==1){
                    playExplosion();
                    frame.remove(panel);
                    frame.add(gameOver);
                    setUpGameOver(gameOver);
                }
            }});
            button.setBounds(x,y,width,height);
            x += 50;
        }
    }

    public void buildScoreBoard(JPanel panel) {

        score.setFocusable(false);
        score.setBounds(650,13,40,25);
        score.setFont(new Font("Bahnschrift", Font.BOLD, 24));
        score.setForeground(Color.GREEN);
        score.setBackground(Color.BLACK);
        panel.add(score);

        JLabel scorelabel = new JLabel("Score:");
        scorelabel.setFont(new Font("Bahnschrift", Font.BOLD, 18));
        scorelabel.setBounds(585,9,60,30);
        scorelabel.setForeground(Color.GREEN);
        panel.add(scorelabel);

    }

    public DisplayFrame(){

        frame.add(panel);
        setUpMenu(frame);
        frame.setSize(765,788);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameOver.setLayout(null);

        JLabel title = new JLabel("Minesweeper");
        title.setFont(new Font("Bahnschrift", Font.BOLD, 30));
        title.setBounds(270,-28,300,100);
        title.setForeground(Color.GREEN);

        buildScoreBoard(panel);
        score.setText("0");

        panel.add(title);
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);

        fillButtons(panel);

        frame.setVisible(true);
    }

}