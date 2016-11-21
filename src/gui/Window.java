package gui;

import ia.Agent;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alex on 05/11/2016.
 */
public class Window extends JFrame {

    private static Window window;
    private JPanel forest;

    public static Window getInstance(){
        if(window == null){
            window = new Window();
        }
        return window;
    }

    private Window(){
        setTitle("Magic Forest");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container container = getContentPane();
        setSize(Constants.WIDTH,Constants.HEIGHT);
        forest = new Forest();
        container.add(forest);


        JPanel storyBoard = new JPanel();
        storyBoard.setBackground(Color.GRAY);
        storyBoard.setSize(Constants.WIDTH,100);
        //action button
        JButton move = new JButton("move");
        move.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("move");
                Agent.getInstance().findMove();
            }
        });
        storyBoard.add(move,BorderLayout.WEST);
        JButton newLevel = new JButton("generate level");
        newLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("generate level");
                Agent.getInstance().newLevel();
            }
        });
        storyBoard.add(newLevel,BorderLayout.WEST);
        //Story label
        JLabel storyField = new JLabel("bienvenue dans la fôret !!!");
        storyBoard.add(storyField);

        container.add(storyBoard,BorderLayout.SOUTH);

        setVisible(true);
    }


    public void newForest() {
        Container c = this.getContentPane() ;
        c.remove(0);
        c.repaint();
        forest = new Forest();
        c.add(forest,0);
        this.repaint();
        this.revalidate();
    }

}
