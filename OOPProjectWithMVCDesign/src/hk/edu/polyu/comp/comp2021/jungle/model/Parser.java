package hk.edu.polyu.comp.comp2021.jungle.model;

import hk.edu.polyu.comp.comp2021.jungle.view.Chesspiece;
import hk.edu.polyu.comp.comp2021.jungle.view.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Model class for updating any <code>Chessboard</code> view
 * @see hk.edu.polyu.comp.comp2021.jungle.view.Chessboard
 */
public class Parser implements Serializable {
    /**statements this holds*/
    private Chessboard board;

    /**other accessed models*/
    private Rules rules;

    private Parser(Chessboard board){
        this.board = board;
        rules = new Rules(board, board.coordinates);
    }

    public static Parser newParserOnChessboard(Chessboard chessboard){
        return new Parser(chessboard);
    }

    /**
     * Parse command input.
     * @param input a string allows format: move [fromPosition] [toPosition] only.
     */
    public void parseCommand(String input){
        input = input.toUpperCase();
        String[] divided = input.split(" ");
        String operation = divided[0];
        if(operation.equals("MOVE") && divided.length == 3){
            String fromPosition = divided[1];
            String toPosition = divided[2];
            int fromX = parseCharacter(fromPosition.charAt(0));
            int fromY = Integer.parseInt(""+fromPosition.charAt(1)) - 1;
            int endX = parseCharacter(toPosition.charAt(0));
            int endY = Integer.parseInt(""+toPosition.charAt(1)) - 1;
            board.output.append("AFTER PARSING: MOVE "
                    + fromPosition
                    + "(" + fromX + ", " + fromY + ") "
                    + toPosition
                    + "(" + endX + ", " + endY + ")\n");
            Chesspiece piece = board.coordinates[fromX][fromY].chesspiece;
            if(board.isBlackTurn && piece.getTeam() == Color.BLACK){
                if(piece != null){
                    boolean isValid = rules.isValidStep(piece, fromX, fromY, endX, endY);
                    //output.append(input + ": " + (isValid ? "available\n":"not available\n"));
                    if(isValid){
                        boolean isAttack = board.coordinates[endX][endY].chesspiece != null;
                        Chesspiece startPiece = board.coordinates[fromX][fromY].chesspiece;
                        board.coordinates[fromX][fromY].setOccupied(false);
                        board.coordinates[fromX][fromY].removeCurrentPiece();
                        if(!isAttack){
                            board.output.append("MOVEMENT: " +
                                    startPiece + " move from " + fromPosition + " to " + toPosition + "\n");
                            board.coordinates[endX][endY].setChessPiece(piece, board);
                        }
                        else{
                            Chesspiece endPiece = board.coordinates[endX][endY].chesspiece;
                            board.output.append("ATTACK: " +
                                    startPiece + " " + fromPosition +
                                    " attacks " +
                                    endPiece + " " + toPosition + "\n");
                            board.coordinates[endX][endY].removeChessPiece(endPiece, board);
                            board.coordinates[endX][endY].setChessPiece(piece, board);
                        }
                        board.command.setText("");
                        board.isBlackTurn = false;
                        board.isGreyTurn = true;
                        if(rules.isWine(board.blackTeam)){
                            JOptionPane.showMessageDialog(null, "GREY TEAM WIN!","Game Over", JOptionPane.INFORMATION_MESSAGE);
                            board.output.append("WINNER: GREY TEAM\n");
                            board.output.append("RESTART OR LOAD A GAME BY CLICK THE UPPER LEFT CORNER.\n");
                            board.confirm.setEnabled(false);
                            board.command.setEnabled(false);
                        }
                        else{
                            board.output.append("ROUND " + board.round++ +": TEAM GREY TURN.\n");
                        }
                    }
                }
                else{
                    board.output.append("NO SUCH PIECE:\n");
                    board.output.append("no such a black team piece at " +
                            "(" + fromPosition.charAt(0) + ", " + fromPosition.charAt(1) + ")\n");
                    board.output.append("Please try again.\n");
                }
            }
            else if(board.isGreyTurn && piece.getTeam() == Color.gray){
                if(piece != null){
                    boolean isValid = rules.isValidStep(piece, fromX, fromY, endX, endY);
                    //output.append(input + ": " + (isValid ? "available\n":"not available\n"));
                    if(isValid){
                        boolean isAttack = board.coordinates[endX][endY].chesspiece != null;
                        Chesspiece startPiece = board.coordinates[fromX][fromY].chesspiece;
                        board.coordinates[fromX][fromY].setOccupied(false);
                        board.coordinates[fromX][fromY].removeCurrentPiece();
                        if(!isAttack){
                            board.output.append("MOVEMENT: " +
                                    startPiece + " move from " + fromPosition + " to " + toPosition + "\n");
                            board.coordinates[endX][endY].setChessPiece(piece, board);
                        }
                        else{
                            Chesspiece endPiece = board.coordinates[endX][endY].chesspiece;
                            board.output.append("ATTACK: " +
                                    startPiece + " " + fromPosition +
                                    " attacks " +
                                    endPiece + " " + toPosition + "\n");
                            board.coordinates[endX][endY].removeChessPiece(endPiece, board);
                            board.coordinates[endX][endY].setChessPiece(piece, board);
                        }
                        board.command.setText("");
                        board.isBlackTurn = true;
                        board.isGreyTurn = false;
                        if(rules.isWine(board.blackTeam)){
                            JOptionPane.showMessageDialog(null, "BLACK TEAM WIN!","Game Over", JOptionPane.INFORMATION_MESSAGE);
                            board.output.append("WINNER: BLACK TEAM\n");
                            board.output.append("RESTART OR LOAD A GAME BY CLICK THE UPPER LEFT CORNER.\n");
                            board.confirm.setEnabled(false);
                            board.command.setEnabled(false);
                        }
                        else{
                            board.output.append("ROUND " + board.round++ +": TEAM BLACK TURN.\n");
                        }
                    }
                }
                else{
                    board.output.append("NO SUCH PIECE:\n");
                    board.output.append("No such a grey team piece at (" +
                            fromPosition.charAt(0) + ", " + fromPosition.charAt(1) + ")\n");
                    board.output.append("Please try again.\n");
                }
            }
            else if(board.isGreyTurn && piece.getTeam() != Color.gray){
                board.output.append("WRONG PIECE CONTROL: \n");
                board.output.append("Try to control " + piece + "(" + fromPosition + ")\n");
                board.output.append("Please try again.\n");
            }
            else{
                board.output.append("NOT YOUR TURN:\n");
                board.output.append("Please wait another player.\n");
            }
        }
        else{
            board.output.append("INVALID COMMAND:\n");
            board.output.append(board.command.getText() + "\n");
            board.output.append("Please try again.\n");
        }
    }

    /**
     * Parse characters to their numbers accordingly
     * @param c a character
     * @return 0, 1, ..., 6 integer.
     */
    private int parseCharacter(char c){
        switch (c){
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
        }
        return -1;
    }
}
