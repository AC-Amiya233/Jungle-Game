package hk.edu.polyu.comp.comp2021.jungle.model;

import hk.edu.polyu.comp.comp2021.jungle.view.Chesspiece;
import hk.edu.polyu.comp.comp2021.jungle.view.Chessboard;

import java.io.*;

/**
 * Rules of the jungle game, which is applied on
 * @see Chessboard It will give boolean values:
 * 1. Is step valid?
 * 2. Is any player win and who?
 *
 * @className: Rules
 * @version: final
 */
public class Rules implements Serializable {
    /**statements this holds*/
    //1. whole chessboard
    Chessboard board;
    Coordinate[][] coordinates;
    //2. each movement
    Chesspiece piece = null;
    int startCol, startRow, endCol, endRow;

    public Rules(Chessboard board, Coordinate[][] coordinates){
        this.board = board;
        this.coordinates = coordinates;
    }

    /**get the judgement of is enemy team is eliminated*/
    public boolean isEliminated(Chesspiece[] enemyTeam){
        boolean isEliminated = true;

        for(Chesspiece chesspiece: enemyTeam){
            if(chesspiece != null) {
                isEliminated = false;
                break;
            }
        }

        return isEliminated;
    }

    /**get the judgement of is its team occupy enemy's den*/
    public boolean isOccupyDen(){
        Coordinate end = coordinates[endCol][endRow];
        piece = end.chesspiece;
        boolean isOccupy = end.isDen &&
                end.belongTo.getColor() != piece.getTeam();

        if(isOccupy) return true;
        else return false;
    }

    /**get the judgement of whether player is winner or not*/
    public boolean isWine(Chesspiece[] enemyTeam){
        boolean isEliminated = isEliminated(enemyTeam);
        boolean isOccupyDen = isOccupyDen();

        if(isOccupyDen){
            return true;
        }
        else{
            if(isEliminated){
                return true;
            }
            else{
                return false;
            }
        }
    }

    /**get the judgement of whether a step is valid or not*/
    public boolean isValidStep(Chesspiece piece, int startCol, int startRow, int endCol, int endRow){
        boolean isValid = false;

        this.startCol = startCol;
        this.startRow = startRow;
        this.endCol = endCol;
        this.endRow = endRow;

        String name = piece.getName();

        Coordinate start = coordinates[startCol][startRow];
        Coordinate end = coordinates[endCol][endRow];

        //1. end square is not occupied
        boolean isMove = !end.isOccupied;
        //1. one square only.
        boolean isStep = Math.abs(startCol - endCol + startRow - endRow) == 1;

        //1. jump over a river by moving horizontally or vertically.
        //horizontally: 2, vertically: 3
        boolean isJump = (startRow == 2 && endRow == 6 && startCol - endCol == 0)
                || (startRow == 6 && endRow == 2 && startCol - endCol == 0)
                || (startCol == 0 && endCol == 3 && startRow - endRow == 0)
                || (startCol == 3 && endCol == 0 && startRow - endRow == 0)
                || (startCol == 3 && endCol == 6 && startRow - endRow == 0)
                || (startCol == 6 && endCol == 3 && startRow - endRow == 0);
        boolean anyRatInLeftRiver = false;
        boolean anyRatInRightRiver = false;
        for(int col = 1; col <= 2; col++){
            for(int row = 3; row <= 5; row++){
                if(coordinates[col][row].chesspiece != null){
                    anyRatInLeftRiver = true;
                    break;
                }
            }
        }
        for(int col = 4; col <= 5; col++){
            for(int row = 3; row <= 5; row++){
                if(coordinates[col][row].chesspiece != null){
                    anyRatInRightRiver = true;
                    break;
                }
            }
        }
        boolean isJumpable = isJump &&
                ((!anyRatInLeftRiver
                && ((startCol == 0 && endCol == 3) || (startCol == 3 && endCol == 0)
                || (startRow == 2 && endRow == 6) || (startRow == 6 || endRow == 2)))
                || (!anyRatInRightRiver
                && ((startCol == 3 && endCol == 6) || (startCol == 6 && endCol == 3))
                || (startRow == 2 && endRow == 6) || (startRow == 6 || endRow == 2)));

        //Judgements about river:
        boolean isInRiver = start.isRiver;
        boolean isGettingInRiver = end.isRiver;

        //Judgements about tripper:
        boolean isGettingInTrapper = end.isTrapper;

        //special movement
        //1. a animal can not move into its den.
        boolean isGettingInDen = end.isDen;
        boolean isGettingInItsDen = isGettingInDen && end.belongTo.getColor() == piece.getTeam();

        //attack a piece
        //1. end position piece must exist.
        //2. different team.
        boolean isAttack = end.chesspiece != null
                && start.chesspiece.getTeam() != end.chesspiece.getTeam();
        //1. higher rank pieces capture lower rank pieces.
        //2. except rats capture elephants.
        boolean isAttackable = isAttack && ((start.chesspiece.getPriority() >= end.chesspiece.getPriority()
                && (start.chesspiece.getPriority() != 8 && end.chesspiece.getPriority() != 1))
                || (start.chesspiece.getPriority() == 1 && end.chesspiece.getPriority() == 8)
                || (start.chesspiece.getPriority() == 1 && end.chesspiece.getPriority() == 1)
                || (isGettingInTrapper && end.belongTo.getColor() == piece.getTeam()));

        //1. can not enter its own den
        switch (name){
            case "\u9f20":
            {
                //Rat
                //1. can not attack in river
                //2. can attack on the land
                if(isStep && isMove && !isGettingInDen){
                    isValid = true;
                }
                else if(isStep && isAttack && isAttackable && !isInRiver && !isGettingInRiver){
                    isValid = true;
                }
                else if(isStep && isAttack && isInRiver && isAttackable && isGettingInRiver){
                    isValid = true;
                }
                else if(!isStep){
                    board.output.append("INVALID MOVEMENT: " +
                            piece.getName() + "steps larger than one.\n");
                    board.output.append("Execution fail.\n");
                }
                else if(isStep && !isMove && !isAttack){
                    board.output.append("INVALID MOVEMENT: " +
                            "(" + parseInteger(endCol) + ", " + endRow + ")" +
                            "has been taken by the same team piece.\n");
                    board.output.append("Execution fail.\n");
                }
                else if(isStep && isAttack && isAttackable && !isInRiver && isGettingInRiver){
                    board.output.append("INVALID ATTACK: " +
                            piece.getName() + " can not attack the rat in the river.\n");
                    board.output.append("Execution fail.\n");
                }
                else if(isStep && isAttack && isInRiver && isAttackable && !isGettingInRiver){
                    board.output.append("INVALID ATTACK: " +
                            piece.getName() + " can not attack any on land in any river.\n");
                    board.output.append("Execution fail.\n");
                }
                else if(isStep && isAttack && !isInRiver && !isAttackable){
                    board.output.append("INVALID ATTACK: " +
                            piece.getName() + "try to attack a higher rank piece.\n");
                    board.output.append("Execution fail.\n");
                }
                break;
            }
            case "\u732b":
            case "\u72d7":
            case "\u8c79":
            case "\u72fc":
            case "\u8c61":{
                if (isStep && isMove && !isGettingInDen && !isGettingInRiver) {
                    isValid = true;
                }
                else if (isStep && isAttack && isAttackable && !isGettingInRiver) {
                    isValid = true;
                }
                else if(!isStep){
                    board.output.append("INVALID MOVEMENT: " +
                            piece.getName() + " Steps larger than one.\n");
                    board.output.append("Execution fail.\n");
                }
                else if(isStep && isGettingInRiver){
                    board.output.append("INVALID MOVEMENT:" +
                            piece.getName() + " try to get in river.\n");
                    board.output.append("Execution fail.\n");
                }
                else if(isStep && !isMove && !isAttack){
                    board.output.append("INVALID MOVEMENT: " +
                            "(" + parseInteger(endCol) + ", " + endRow + ")" +
                            "has been taken by a same team piece.\n");
                    board.output.append("Execution fail.\n");
                }
                else if(isStep && isAttack && !isInRiver && !isAttackable){
                    board.output.append("INVALID ATTACK: " +
                            piece.getName() + "try to attack a higher rank piece.\n");
                    board.output.append("Execution fail.\n");
                }
                break;
            }
            case "\u864e":
            case "\u72ee": {
                if (isStep && isMove && !isGettingInItsDen && !isGettingInRiver) {
                    isValid = true;
                }
                else if (isStep && isAttack && isAttackable && !isGettingInRiver) {
                    isValid = true;
                }
                else if (isJump && isJumpable && isMove) {
                    isValid = true;
                }
                else if (isJump && isJumpable && isAttack && isAttackable) {
                    isValid = true;
                }
                else if (isJump && isJumpable && isAttack && !isAttackable){
                    board.output.append("INVALID ATTACK: " +
                            piece.getName() + "try to attack a higher rank piece.\n");
                    board.output.append("Execution fail.\n");
                }
                else if(isJump && !isJump){
                    board.output.append("INVALID STEP:" +
                            "some rat in the river.\n");
                    board.output.append("Execution fail.\n");
                }
                else if (isStep && isAttack && !isAttackable && !isGettingInRiver){
                    board.output.append("INVALID ATTACK: " +
                            piece.getName() + "try to attack a higher rank piece.\n");
                    board.output.append("Execution fail.\n");
                }
                else if (isStep && isAttack && isAttackable && isGettingInRiver){
                    board.output.append("INVALID MOVEMENT:" +
                            piece.getName() + " try to get in river.\n");
                    board.output.append("Execution fail.\n");
                }
                else if(isStep && !isMove && !isAttack){
                    board.output.append("INVALID MOVEMENT: " +
                            "(" + parseInteger(endCol) + ", " + endRow + ")" +
                            "has been taken by a same team piece.\n");
                    board.output.append("Execution fail.\n");
                }
                else if(isJump && !isMove && !isAttack){
                    board.output.append("INVALID MOVEMENT: " +
                            "(" + parseInteger(endCol) + ", " + endRow + ")" +
                            "has been taken by a same team piece.\n");
                    board.output.append("Execution fail.\n");
                }
                break;
            }
        }

        return isValid;
    }

    /**parse numbers to characters*/
    private char parseInteger(int num){
        switch (num){
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            case 5:
                return 'F';
            case 6:
                return 'G';
        }
        return ' ';
    }
}
