package hk.edu.polyu.comp.comp2021.jungle.view;

import hk.edu.polyu.comp.comp2021.jungle.control.LoginOptionalControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * User oriented frame. A user can choose both restart a new game
 * or load any valid game from his disk.
 * after choose, it will create a new UI extends <code>JFrame</code>
 * @see hk.edu.polyu.comp.comp2021.jungle.view.TheJungleGame
 *
 * @className: LoginFrame
 * @version: final
 */
public class LoginFrame extends JFrame {
    /**components under control*/
    public JFileChooser chooser;
    public JButton restart;
    public JButton open;

    /**components access update*/
    public TheJungleGame game = null;

    /**default construct*/
    private LoginFrame(){
        //init control
        LoginOptionalControl control = LoginOptionalControl.installOptionalControlOnFrame(this);

        //add welcome label
        JLabel welcome = new JLabel("The Jungle Game", JLabel.CENTER);
        welcome.setFont(new Font("Serif", Font.BOLD, 60));
        welcome.setBounds(10, 10, 500, 100);
        welcome.setBorder(BorderFactory.createRaisedBevelBorder());
        add(welcome);

        //add readme part
        JTextArea readMe = new JTextArea();
        readMe.setText("ReadMe: \n" +
                "   1.During their turn, a player must move.\n" +
                "   2.A piece may not move to its own den.\n" +
                "   3.The rat is the only animal that is allowed to go onto a water \n" +
                "     square.\n" +
                "   4.The rat may not capture the elephant or another rat on land\n" +
                "     directly from a water square.\n" +
                "   5.The rat may attack the opponent rat if both pieces are in the\n" +
                "     water or on the land.\n" +
                "   6.The lion and tiger pieces may jump over a river by moving \n" +
                "     horizontally or vertically. Such a move is not allowed if \n" +
                "     there is a rat (whether friendly or enemy) on any of the inte-\n" +
                "     -rvening water squares.\n");
        readMe.setBorder(BorderFactory.createLoweredBevelBorder());
        readMe.setBounds(10, 110, 500, 380);
        readMe.setFont(new Font("Serif", Font.PLAIN, 20));
        readMe.setEnabled(false);
        add(readMe);

        //add start new button
        restart = new JButton("Restart/Start New");
        restart.setFont(new Font("Serif", Font.PLAIN, 30));
        restart.setBounds(10, 510, 250, 50);
        restart.setBorder(BorderFactory.createRaisedBevelBorder());
        restart.addActionListener(control);
        add(restart);

        chooser = new JFileChooser();

        //add open button
        open = new JButton("Open");
        open.setFont(new Font("Serif", Font.PLAIN, 30));
        open.setBounds(260, 510, 250, 50);
        open.setBorder(BorderFactory.createRaisedBevelBorder());
        open.addActionListener(control);
        add(open);

        //set frame's priorities
        setLayout(null);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Welcome to the jungle game");
        setSize(530, 600);
    }

    /**get a new instance of LoginFrame*/
    public static LoginFrame newInstance(){
        return new LoginFrame();
    }
}
