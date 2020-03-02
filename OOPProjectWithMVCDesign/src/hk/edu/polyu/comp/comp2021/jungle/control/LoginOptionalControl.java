package hk.edu.polyu.comp.comp2021.jungle.control;

import hk.edu.polyu.comp.comp2021.jungle.model.Login;
import hk.edu.polyu.comp.comp2021.jungle.view.LoginFrame;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

/**
 * A control links between <code>Login</code> and <code>LoginFrame</code>
 */
public class LoginOptionalControl implements ActionListener{
    /**models this holds*/
    private Login login;

    /**views this controls*/
    private LoginFrame frame;

    private LoginOptionalControl(LoginFrame loginFrame){
        this.frame = loginFrame;
        login = Login.setOptionalOnFrame(frame);
    }

    /**set this on a given LoginFrame*/
    public static LoginOptionalControl installOptionalControlOnFrame(LoginFrame loginFrame){
        return new LoginOptionalControl(loginFrame);
    }

    @Override
    public void actionPerformed(ActionEvent event){
        if(event.getSource() == frame.restart){
            String blackTeamPlayer = JOptionPane.showInputDialog("Please input first player name");
            String greyTeamPlayer = JOptionPane.showInputDialog("Please input second player name");
            login.restart(blackTeamPlayer, greyTeamPlayer);
        }
        else if(event.getSource() == frame.open){
            int position = frame.chooser.showOpenDialog(null);
            File open = frame.chooser.getSelectedFile();
            login.open(position, open);
        }
    }
}
