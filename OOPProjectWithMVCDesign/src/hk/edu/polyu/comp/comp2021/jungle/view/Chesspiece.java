package hk.edu.polyu.comp.comp2021.jungle.view;

import hk.edu.polyu.comp.comp2021.jungle.model.Rules;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * This section design ideas were referenced: https://blog.csdn.net/cnlht/article/details/8205733
 * Java实践（十二）——中国象棋
 *
 * A class describes chess pieces on the
 * @see hk.edu.polyu.comp.comp2021.jungle.view.Chessboard
 * A instance of <code>Chesspiece</code> has following priorities:
 * 1. team          Inner enum <code>Team</code>
 * @see Chesspiece.Team
 * 2. priority      Inner enum <code>Priority</code>
 * @see Chesspiece.Priority
 * 3. name          Inner enum <code>ChessPieceName</code>
 * @see Chesspiece.ChessPieceName
 * 4. chessboard    which chess board this belongs to?
 * @see hk.edu.polyu.comp.comp2021.jungle.view.Chessboard.DrawGrids
 * 5. rules         which rule this statisfies?
 * @see Rules
 */
public class Chesspiece extends JLabel implements Serializable {
    /**
     * Inner Enum <code>Priority</code> describes the priority of each animal.
     * Priorities:
     * Rat       |       1
     * Cat       |       2
     * Dog       |       3
     * Wolf      |       4
     * Leopard   |       5
     * Tiger     |       6
     * Lion      |       7
     * Elephant  |       8
     */
    public enum Priority implements Serializable{
        trapped(0),
        rat(1),
        cat(2),
        dog(3),
        wolf(4),
        leopard(5),
        tiger(6),
        lion(7),
        elephant(8);

        private int priority;

        Priority(int priority){ this.priority = priority;}

        public int getPriority(){
            return priority;
        }
    }
    /**
     * Inner enum <code>CheckerName</code> describes the chinese name of each animal.
     * Names:
     * Rat       |       鼠
     * Cat       |       猫
     * Dog       |       狗
     * Wolf      |       狼
     * Leopard   |       豹
     * Tiger     |       虎
     * Lion      |       狮
     * Elephant  |       象
     *
     */
    public enum ChessPieceName implements Serializable{
        rat("\u9f20"),
        cat("\u732b"),
        dog("\u72d7"),
        wolf("\u72fc"),
        leopard("\u8c79"),
        tiger("\u864e"),
        lion("\u72ee"),
        elephant("\u8c61");

        private String name;

        ChessPieceName(String name){ this.name = name; }

        public String getName(){return name;}
    }
    /**
     * An enum <code>Team</code> describes each team color
     */
    public enum Team implements Serializable{
        black(Color.BLACK), gray(Color.gray);

        private Color color;

        Team(Color color){
            this.color = color;
        }

        public Color getColor(){ return color; }
    }

    //Default priorities:
    private static final int DEFAULT_HEIGHT = 80;
    private static final int DEFAULT_WIDTH = 80;
    private static final Font DEFAULT_FONT = new Font("华文行楷",Font.BOLD,30);

    //Instance properties:
    private Team team;
    private Priority priority;
    private ChessPieceName name;

    private Chessboard board;
    private Rules rules;

    public Chesspiece(Team team, Priority priority, ChessPieceName name, Chessboard board){
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.board = board;

        this.team = team;
        this.priority = priority;
        this.name = name;
    }

    /** Get the chess piece's priority.*/
    public int getPriority(){
        return priority.getPriority();
    }

    /**get the chess piece's team color.*/
    public Color getTeam(){ return team.color; }

    /**get the chess piece's name.*/
    public String getName(){ return name.getName(); }

    /**draw the default image*/
    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;

        //preparations:
        graphics2D.setColor(team.getColor());
        graphics2D.setFont(DEFAULT_FONT);

        graphics2D.drawOval(5, 5, 70, 70);
        graphics2D.drawOval(10, 10, 60, 60);
        graphics2D.fillOval(15, 15, 50, 50);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(name.getName(), 25, 50);
        graphics.setColor(Color.BLACK);
    }

    /**return a string describes the instance.*/
    @Override
    public String toString(){
        return (getTeam() == Color.BLACK ? " black" : " grey").toUpperCase() + " " + getName();
    }
}
