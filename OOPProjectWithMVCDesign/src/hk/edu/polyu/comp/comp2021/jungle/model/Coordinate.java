package hk.edu.polyu.comp.comp2021.jungle.model;

import hk.edu.polyu.comp.comp2021.jungle.view.Chesspiece;
import hk.edu.polyu.comp.comp2021.jungle.view.Chessboard;

import java.awt.*;
import java.io.Serializable;

/**
 * This section design ideas were referenced: https://blog.csdn.net/cnlht/article/details/8205733
 * Java实践（十二）——中国象棋
 *
 * Set a providing x and y values coordinate on
 * @see hk.edu.polyu.comp.comp2021.jungle.view.Chessboard.DrawGrids
 * A inner class extends JPanel, which draw board grids at the beginning of program.
 *
 * @className: Coordinate
 * @version: final
 */
public class Coordinate implements Serializable {
    /**statements this holds*/
    //1.coordinate: (x, y)
    int x;                  //1, 2, 3, ..., 7 * SIZE
                            //To simplify the process, transform [A, B, C, ..., G]
                            //to integers
    int y;                  //1, 2, 3, ..., 9 * SIZE

    //2.each field's priorities
    boolean isOccupied;
    final boolean isRiver;
    final boolean isDen;
    final boolean isTrapper;
    Chesspiece.Team belongTo;

    //3.piece this holds
    Chesspiece chesspiece;

    //4.chessboard this is setting
    Chessboard chessboard;

    public Coordinate(int x, int y, boolean isOccupied, boolean isRiver, boolean isTrapper, boolean isDen, Chesspiece.Team belongTo){
        this.x = x;
        this.y = y;
        this.isOccupied = isOccupied;
        this.isRiver = isRiver;
        this.isTrapper = isTrapper;
        this.isDen = isDen;
        this.belongTo = belongTo;
    }

    /** Set a XXX chesspiece on a XXX chessboard. Both initialization and movement use this method to implement.*/
    public void setChessPiece(Chesspiece chesspiece, Chessboard chessboard){
        this.chessboard = chessboard;
        this.chesspiece = chesspiece;
        chessboard.grids.add(chesspiece);
        int width = Chessboard.DEFAULT_GRID_LENGTH;
        int height = Chessboard.DEFAULT_GRID_LENGTH;
        chesspiece.setBounds(x, y, width, height);
        isOccupied = true;

        chessboard.grids.validate();
        chessboard.grids.repaint();
        chessboard.validate();
    }

    /** Remove a XXX chesspiece from a XXX chessboard */
    public void removeChessPiece(Chesspiece chesspiece, Chessboard chessboard){
        this.chessboard = chessboard;
        this.chesspiece = chesspiece;
        chessboard.grids.remove(chesspiece);
        if(chesspiece.getTeam() == Color.BLACK){
            for(int i = 0; i < 8; i++){
                if(chessboard.blackTeam[i] == chesspiece){
                    chessboard.blackTeam[i] = null;
                }
            }
        }
        else{
            for(int i = 0; i < 8; i++){
                if(chessboard.greyTeam[i] == chesspiece){
                    chessboard.greyTeam[i] = null;
                }
            }
        }
        isOccupied = false;

        chessboard.grids.validate();
        chessboard.grids.repaint();
        chessboard.validate();
    }

    /**Set chess piece to null*/
    public void removeCurrentPiece(){
        chesspiece = null;
    }

    /**set current position is occupied or not*/
    public void setOccupied(boolean isOccupied){ this.isOccupied = isOccupied; }

    /**get the judgement that whether current position is occupied or not*/
    public boolean isOccupied(){ return isOccupied; }

    /**get the judgement that whether current position is a river or not*/
    public boolean isRiver(){ return isRiver; }

    /**get the judgement that whether current is a den or not*/
    public boolean isDen(){ return isDen; }

    /**get the judgement that whether current is a trapper or not*/
    public boolean isTrapper(){ return isTrapper; }

    /**get the chess piece that whether current might hold a chess piece*/
    public Chesspiece getChessPiece(){ return chesspiece; }

    /**get the value of current x*/
    public int getX(){ return x; }

    /**get the value of current y*/
    public int getY(){ return y; }

    @Override
    public String toString(){
        return  " (" + (x - 1)/80 + ", " + (8 - y/80) + ") "
                + " (" + x + ", " + y + ") " + "\t"
                + "1.isOccupied: " + isOccupied + "\t"
                + "2.IsRiver: " + isRiver + "\t"
                + "3.IsDen: " + isDen + "\t"
                + "4.IsTrapper: " + isTrapper + "\t"
                + "5.BelongTo: " + (belongTo == null ? "null" : belongTo.getColor()) + "\t"
                + (chesspiece == null ? "null" : chesspiece);
    }
}
