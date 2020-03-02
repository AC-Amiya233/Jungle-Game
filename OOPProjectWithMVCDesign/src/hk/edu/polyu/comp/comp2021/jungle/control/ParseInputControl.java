package hk.edu.polyu.comp.comp2021.jungle.control;

import hk.edu.polyu.comp.comp2021.jungle.model.Parser;
import hk.edu.polyu.comp.comp2021.jungle.view.Chessboard;

import java.awt.event.*;
import java.io.*;

/**
 * A control links between <code>Parser</code> and <code></code>
 */
public class ParseInputControl implements Serializable, ActionListener {
    /**models this holds*/
    private Parser parser;

    /**views this controls*/
    private Chessboard board;

    private ParseInputControl(Chessboard board){
        this.board = board;
        parser = Parser.newParserOnChessboard(board);
    }

    /**set this on a given chessboard*/
    public static ParseInputControl installControllerOnChessboard(Chessboard board){
        return new ParseInputControl(board);
    }

    /**parse input*/
    public void parseInput(){
        if(board.command.getText() != null){
            parser.parseCommand(board.command.getText());
        }
    }

    @Override
    public void actionPerformed(ActionEvent event){
        parseInput();
    }
}
