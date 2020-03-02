package hk.edu.polyu.comp.comp2021.jungle.view;

import hk.edu.polyu.comp.comp2021.jungle.control.FileSelectionControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * This section design ideas were referenced: https://blog.csdn.net/cnlht/article/details/8205733
 * Java实践（十二）——中国象棋
 *
 * A static frame to show the created or loaded panel, which has following
 * priorities:
 * 1. board         A displayed chess board
 * @see Chessboard
 *
 * @className: TheJungleGame
 * @version: final
 */
public class TheJungleGame extends JFrame implements Serializable{
    //Default priorities:
    public static final Font DEFAULT_WORD_FONT = new Font("Serif", Font.PLAIN, 20);

    /**controller*/
    FileSelectionControl control;

    /**components under control*/
    public JMenuBar commandBar;
    public JMenu fileMenu;
    public JMenuItem restart, save, open;

    /**components access update*/
    public JFileChooser chooser;
    public Container container;
    public Chessboard board;

    private TheJungleGame(String player1, String player2){
        //add menu bar on the top of the frame
        commandBar = new JMenuBar();
        fileMenu = new JMenu("File");
        fileMenu.setFont(DEFAULT_WORD_FONT);
        restart = new JMenuItem("Restart/New Game");
        restart.setFont(DEFAULT_WORD_FONT);
        save = new JMenuItem("Save");
        save.setFont(DEFAULT_WORD_FONT);
        open = new JMenuItem("Open");
        open.setFont(DEFAULT_WORD_FONT);
        commandBar.add(fileMenu);
        setJMenuBar(commandBar);
        fileMenu.add(restart);
        fileMenu.add(save);
        fileMenu.add(open);

        //init control
        control = FileSelectionControl.installFileSelectionOnGame(this);

        restart.addActionListener(control);
        save.addActionListener(control);
        open.addActionListener(control);

        //file chooser
        chooser = new JFileChooser();

        //Create a new chessboard.
        board = new Chessboard();

        //set the container
        container = getContentPane();
        container.add(board, BorderLayout.CENTER);

        //set frame's priorities
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
        setTitle("Chessboard: [Black]" + player1 + " vs " + "[Grey]" + player2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1300, 1020);
        setResizable(true);
        setLocation(500,10);
    }

    private TheJungleGame(Chessboard lorded, String path){
        //add menu bar on the top of the frame
        commandBar = new JMenuBar();
        fileMenu = new JMenu("File");
        fileMenu.setFont(DEFAULT_WORD_FONT);

        restart = new JMenuItem("Restart/New Game");
        restart.setFont(DEFAULT_WORD_FONT);

        save = new JMenuItem("Save");
        save.setFont(DEFAULT_WORD_FONT);

        open = new JMenuItem("Open");
        open.setFont(DEFAULT_WORD_FONT);

        //init control
        control = FileSelectionControl.installFileSelectionOnGame(this);
        restart.addActionListener(control);
        save.addActionListener(control);
        open.addActionListener(control);


        commandBar.add(fileMenu);
        fileMenu.add(restart);
        fileMenu.add(save);
        fileMenu.add(open);

        setJMenuBar(commandBar);

        //file chooser
        chooser = new JFileChooser();

        //set the container
        board = lorded;

        container = getContentPane();
        container.add(lorded, BorderLayout.CENTER);

        //set frame's priorities
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
        setTitle(path);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1300, 1020);
        setResizable(true);
        setLocation(500,10);
    }

    /**get a new instance with given string values*/
    public static TheJungleGame newInstanceFromValues(String player1, String player2){
        return new TheJungleGame(player1, player2);
    }

    /**get a new instance with opened chessboard file*/
    public static TheJungleGame loadJPanelInstanceFromValues(Chessboard lorded, String path){
        return new TheJungleGame(lorded, path);
    }
}
