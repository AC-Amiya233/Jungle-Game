package hk.edu.polyu.comp.comp2021.jungle.model;

import hk.edu.polyu.comp.comp2021.jungle.view.Chessboard;
import hk.edu.polyu.comp.comp2021.jungle.view.LoginFrame;
import hk.edu.polyu.comp.comp2021.jungle.view.TheJungleGame;

import javax.swing.*;
import java.io.*;

/**
 * Model class for updating any <code>LoginFrame</code> view
 * @see hk.edu.polyu.comp.comp2021.jungle.view.LoginFrame
 *
 * @className: Login
 * @version: final
 */
public class Login{
    /**statements this holds*/
    private LoginFrame loginFrame;

    private Login(LoginFrame loginFrame){
        this.loginFrame = loginFrame;
    }

    public static Login setOptionalOnFrame(LoginFrame frame){
        return new Login(frame);
    }

    public String restart(String blackTeamPlayer, String greyTeamPlayer){
        loginFrame.game = TheJungleGame.newInstanceFromValues(blackTeamPlayer, greyTeamPlayer);
        loginFrame.setVisible(false);
        return "new";
    }

    /** open a existed chess board class.*/
    public boolean open(int position, File open){
        if(open != null && position == JFileChooser.APPROVE_OPTION){
            try ( ObjectInputStream input = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(open)
                    )
            )){
                Chessboard lorded = (Chessboard) input.readObject();
                loginFrame.game = TheJungleGame.loadJPanelInstanceFromValues(lorded, open.getName());
            }catch (IOException io){
                JOptionPane.showMessageDialog( null, "Load file fail. Please try again.", "IO Exception", JOptionPane.ERROR_MESSAGE);
                io.printStackTrace();
                return false;
            }catch (ClassNotFoundException e){
                JOptionPane.showMessageDialog( null, "Load file fail. Please try again.", "ClassNotFound Exception", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
}
