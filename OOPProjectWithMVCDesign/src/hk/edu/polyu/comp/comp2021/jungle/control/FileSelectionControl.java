package hk.edu.polyu.comp.comp2021.jungle.control;

import hk.edu.polyu.comp.comp2021.jungle.model.Selector;
import hk.edu.polyu.comp.comp2021.jungle.view.TheJungleGame;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
 * A control links between <code>Selector</code> and <code>TheJungleGame</code>
 */
public class FileSelectionControl implements Serializable, ActionListener {
    /**models this holds*/
    private Selector selector;

    /**views this controls*/
    private TheJungleGame game;

    private FileSelectionControl(TheJungleGame game){
        this.game = game;
        selector = Selector.setMenuSelectorOnGame(game);
    }

    /**set a file selection control on the given game*/
    public static FileSelectionControl installFileSelectionOnGame(TheJungleGame game){
        return new FileSelectionControl(game);
    }

    @Override
    public void actionPerformed(ActionEvent event){
        if(event.getSource() == game.restart){
            String blackTeamPlayer = JOptionPane.showInputDialog("Please input first player name");
            String greyTeamPlayer = JOptionPane.showInputDialog("Please input second player name");
            selector.restartOperation(blackTeamPlayer, greyTeamPlayer);
        }
        if(event.getSource() == game.save){
            int position = game.chooser.showSaveDialog(null);
            File save = game.chooser.getSelectedFile();
            selector.saveOperation(position, save);
        }
        if(event.getSource() == game.open){
            int position = game.chooser.showOpenDialog(null);
            File open = game.chooser.getSelectedFile();
            selector.openOperation(position, open);
        }
    }
}
