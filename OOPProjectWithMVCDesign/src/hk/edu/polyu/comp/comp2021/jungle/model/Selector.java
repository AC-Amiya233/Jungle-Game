package hk.edu.polyu.comp.comp2021.jungle.model;

import hk.edu.polyu.comp.comp2021.jungle.view.Chessboard;
import hk.edu.polyu.comp.comp2021.jungle.view.TheJungleGame;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Model class for updating any <code>TheJungleGame</code> view
 * @see hk.edu.polyu.comp.comp2021.jungle.view.TheJungleGame
 *
 * @className: Selector
 * @version: final
 */
public class Selector implements Serializable{
    /**statements this holds*/
    private TheJungleGame game;

    private Selector(TheJungleGame game){
        this.game = game;
    }

    /**set a menu selector on the jungle game*/
    public static Selector setMenuSelectorOnGame(TheJungleGame game){
        return new Selector(game);
    }

    /**restart a game*/
    public void restartOperation(String blackTeamPlayer, String greyTeamPlayer){
        game.container.removeAll();
        game.setTitle("Chessboard: " + blackTeamPlayer + " vs " + greyTeamPlayer);
        game.board = new Chessboard();
        game.container.add(game.board, BorderLayout.CENTER);
        game.validate();
    }

    /**save the current game*/
    public void saveOperation(int position, File save){
        if(save != null && position == JFileChooser.APPROVE_OPTION){
            try (ObjectOutputStream output = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(save)
                    )
            )
            ){
                output.writeObject(game.board);
            }catch (IOException e){
            }
        }
    }

    /**open a existed game*/
    public void openOperation(int position, File open){
        game.container.removeAll();
        game.container.repaint();
        game.container.validate();
        game.validate();

        if(open != null && position == JFileChooser.APPROVE_OPTION){
            try ( ObjectInputStream input = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(open)
                    )
            )){
                game.setTitle("Open: " + open);
                //Error.
                Chessboard lorded = (Chessboard) input.readObject();
                game.board = lorded;
                game.container.add(lorded, BorderLayout.CENTER);
                game.container.validate();
                game.repaint();
                game.validate();
            }catch (Exception e){
                JLabel label = new JLabel("No chessboard selected");
                label.setFont(game.DEFAULT_WORD_FONT);
                game.container.add(label, BorderLayout.CENTER);
                game.container.validate();
                game.validate();
            }
        }else{
            JLabel label = new JLabel("No chessboard selected");
            game.container.add(label, BorderLayout.CENTER);
            label.setFont(game.DEFAULT_WORD_FONT);
            game.container.validate();
            game.validate();
        }
    }
}
