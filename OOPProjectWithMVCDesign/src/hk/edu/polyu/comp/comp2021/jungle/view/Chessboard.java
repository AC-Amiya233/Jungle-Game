package hk.edu.polyu.comp.comp2021.jungle.view;

import hk.edu.polyu.comp.comp2021.jungle.control.ParseInputControl;
import hk.edu.polyu.comp.comp2021.jungle.model.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;

/**
 * Description: A class describes how a chessboard is constructed.
 * It has a inner class <code>DrawGrids</code>
 * @see Chessboard.DrawGrids
 *
 * @className: Chessboard
 * @version: final
 */
public class Chessboard extends JPanel implements Serializable {
    /**
     * An enum describes all possible chinese characters used.
     * Shou Xue     ->      獸穴
     * Xian Jing    ->      陷阱
     * Xiao He      ->      小河
     */
    private enum ChineseCharactersUsed implements Serializable {
        Shou("\u7378"),
        Xue("\u7a74"),
        Xiao("\u5c0f"),
        He("\u6cb3"),
        Xian("\u9677"),
        Jing("\u9631");

        private String cn;

        ChineseCharactersUsed(String cn) {
            this.cn = cn;
        }

        public String getCn() {
            return cn;
        }
    }
    /**Inner class for drawing a chessboard.*/
    public class DrawGrids extends JPanel implements Serializable {
        /**default fonts*/
        private final Font DEFAULT_BIG_SIZE_FONT = new Font("华文行楷", Font.BOLD, 20);
        private final Font DEFAULT_SMALL_SIZE_FONT = new Font("华文行楷", Font.BOLD, 15);

        /**GeneralPath for drawing river*/
        GeneralPath path = new GeneralPath();

        /**default constructor*/
        public DrawGrids() {
            setLayout(null);
        }

        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D graphics2D = (Graphics2D) graphics;

            //Set graph location to (80, 80)
            graphics2D.translate(DEFAULT_GRID_LENGTH, DEFAULT_GRID_LENGTH);
            graphics2D.setColor(Color.BLACK);
            int rowCount = 9;
            char lineCount = 65;
            for (int row = 0; row <= 9; row++) {
                for (int line = 0; line <= 7; line++) {
                    if (line == 0) graphics2D.drawString(rowCount-- + "",
                            DEFAULT_GRID_LENGTH * line + DEFAULT_GRID_LENGTH / 2,
                            DEFAULT_GRID_LENGTH * row + DEFAULT_GRID_LENGTH / 2);
                    else if (row == 9) graphics2D.drawString(lineCount++ + "",
                            DEFAULT_GRID_LENGTH * line + DEFAULT_GRID_LENGTH / 2,
                            DEFAULT_GRID_LENGTH * row + DEFAULT_GRID_LENGTH / 2);
                    else {
                        Rectangle2D.Double rectangle = new Rectangle2D.Double(DEFAULT_GRID_LENGTH * line,
                                DEFAULT_GRID_LENGTH * row,
                                DEFAULT_GRID_LENGTH, DEFAULT_GRID_LENGTH);
                        graphics2D.draw(rectangle);
                    }
                }
            }

            //graphics2D.setStroke(new BasicStroke(2));
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //Draw the LEFT river
            //X∈[160, 240], Y∈[240, 400]
            drawRiver(graphics2D, DEFAULT_GRID_LENGTH * 2, DEFAULT_GRID_LENGTH * 3);
            drawRiver(graphics2D, DEFAULT_GRID_LENGTH * 2, DEFAULT_GRID_LENGTH * 4);
            drawRiver(graphics2D, DEFAULT_GRID_LENGTH * 2, DEFAULT_GRID_LENGTH * 5);
            drawRiver(graphics2D, DEFAULT_GRID_LENGTH * 3, DEFAULT_GRID_LENGTH * 3);
            drawRiver(graphics2D, DEFAULT_GRID_LENGTH * 3, DEFAULT_GRID_LENGTH * 4);
            drawRiver(graphics2D, DEFAULT_GRID_LENGTH * 3, DEFAULT_GRID_LENGTH * 5);

            //Draw the RIGHT river
            //X∈[400, 480], Y∈[240, 400]
            drawRiver(graphics2D, DEFAULT_GRID_LENGTH * 5, DEFAULT_GRID_LENGTH * 3);
            drawRiver(graphics2D, DEFAULT_GRID_LENGTH * 5, DEFAULT_GRID_LENGTH * 4);
            drawRiver(graphics2D, DEFAULT_GRID_LENGTH * 5, DEFAULT_GRID_LENGTH * 5);
            drawRiver(graphics2D, DEFAULT_GRID_LENGTH * 6, DEFAULT_GRID_LENGTH * 3);
            drawRiver(graphics2D, DEFAULT_GRID_LENGTH * 6, DEFAULT_GRID_LENGTH * 4);
            drawRiver(graphics2D, DEFAULT_GRID_LENGTH * 6, DEFAULT_GRID_LENGTH * 5);

            graphics2D.draw(path);

            //Draw the dens
            drawDen(graphics2D, DEFAULT_GRID_LENGTH * 4, DEFAULT_GRID_LENGTH * 8);
            drawDen(graphics2D, DEFAULT_GRID_LENGTH * 4, 0);

            //Draw the trappers
            //Top
            drawTrapper(graphics2D, DEFAULT_GRID_LENGTH * 3, 0);
            drawTrapper(graphics2D, DEFAULT_GRID_LENGTH * 4, DEFAULT_GRID_LENGTH * 1);
            drawTrapper(graphics2D, DEFAULT_GRID_LENGTH * 5, 0);
            //Button
            drawTrapper(graphics2D, DEFAULT_GRID_LENGTH * 3, DEFAULT_GRID_LENGTH * 8);
            drawTrapper(graphics2D, DEFAULT_GRID_LENGTH * 4, DEFAULT_GRID_LENGTH * 7);
            drawTrapper(graphics2D, DEFAULT_GRID_LENGTH * 5, DEFAULT_GRID_LENGTH * 8);

            //Add the words.
            graphics2D.setFont(DEFAULT_BIG_SIZE_FONT);
            graphics2D.setColor(Color.WHITE);
            String shou = ChineseCharactersUsed.Shou.getCn();
            String xue = ChineseCharactersUsed.Xue.getCn();
            //Dens
            graphics2D.drawString(shou, DEFAULT_GRID_LENGTH * 4 + 30, DEFAULT_GRID_LENGTH * 8 + 35);
            graphics2D.drawString(xue, DEFAULT_GRID_LENGTH * 4 + 30, DEFAULT_GRID_LENGTH * 8 + 60);
            graphics2D.drawString(xue, DEFAULT_GRID_LENGTH * 4 + 30, 35);
            graphics2D.drawString(shou, DEFAULT_GRID_LENGTH * 4 + 30, 60);

            //Rivers
            graphics2D.setFont(new Font("华文行楷", Font.BOLD, 40));
            graphics2D.setColor(Color.black);
            String xiao = ChineseCharactersUsed.Xiao.getCn();
            String he = ChineseCharactersUsed.He.getCn();
            //River:left
            graphics2D.drawString(xiao, DEFAULT_GRID_LENGTH * 3 - 20, DEFAULT_GRID_LENGTH * 4 + 10);
            graphics2D.drawString(he, DEFAULT_GRID_LENGTH * 3 - 20, DEFAULT_GRID_LENGTH * 5 + 10);
            //River:right
            graphics2D.drawString(xiao, DEFAULT_GRID_LENGTH * 6 - 20, DEFAULT_GRID_LENGTH * 4 + 10);
            graphics2D.drawString(he, DEFAULT_GRID_LENGTH * 6 - 20, DEFAULT_GRID_LENGTH * 5 + 10);

            //trappers
            graphics2D.setFont(DEFAULT_SMALL_SIZE_FONT);
            graphics2D.setColor(Color.darkGray);
            String xian = ChineseCharactersUsed.Xian.getCn();
            String jing = ChineseCharactersUsed.Jing.getCn();

            graphics2D.drawString(xian, DEFAULT_GRID_LENGTH * 3 + 32, DEFAULT_GRID_LENGTH * 8 + 38);
            graphics2D.drawString(jing, DEFAULT_GRID_LENGTH * 3 + 32, DEFAULT_GRID_LENGTH * 8 + 53);
            graphics2D.drawString(xian, DEFAULT_GRID_LENGTH * 4 + 32, DEFAULT_GRID_LENGTH * 7 + 38);
            graphics2D.drawString(jing, DEFAULT_GRID_LENGTH * 4 + 32, DEFAULT_GRID_LENGTH * 7 + 53);
            graphics2D.drawString(xian, DEFAULT_GRID_LENGTH * 5 + 32, DEFAULT_GRID_LENGTH * 8 + 38);
            graphics2D.drawString(jing, DEFAULT_GRID_LENGTH * 5 + 32, DEFAULT_GRID_LENGTH * 8 + 53);

            graphics2D.drawString(xian, DEFAULT_GRID_LENGTH * 3 + 32, 53);
            graphics2D.drawString(jing, DEFAULT_GRID_LENGTH * 3 + 32, 38);
            graphics2D.drawString(xian, DEFAULT_GRID_LENGTH * 4 + 32, DEFAULT_GRID_LENGTH + 53);
            graphics2D.drawString(jing, DEFAULT_GRID_LENGTH * 4 + 32, DEFAULT_GRID_LENGTH + 38);
            graphics2D.drawString(xian, DEFAULT_GRID_LENGTH * 5 + 32, 53);
            graphics2D.drawString(jing, DEFAULT_GRID_LENGTH * 5 + 32, 38);
        }

        /**
         * Code Reference: https://qtdebug.com/java-smooth-curve/
         * Draw a river by components
         *
         * @param graphics2D
         * @param x          coordinate.
         * @param y          coordinate.
         * @see GeneralPath
         * @see Point
         * and bezier curve
         * c1((sp.x + ep.x), sp.y)
         * sp'-------'----------
         * |                 |
         * ---------'--------'ep
         * c2((sp.x + ep.x), ep.y)
         */
        public void drawRiver(Graphics2D graphics2D, int x, int y) {
            //width = 80, height = 80
            Point[][] points = new Point[10][11];
            //init points
            for (int row = 0; row < 10; row++) {
                for (int line = 0; line < 11; line++) {
                    if (line % 2 == 0) points[row][line] = new Point(x + line * 8, y + row * 8);
                    else points[row][line] = new Point(x + line * 8, y + row * 8 + 8);
                }
            }

            for (int i = 0; i < 10; i++) {
                path.moveTo(points[i][0].x, points[i][0].y);
                for (int j = 0; j < 10; j++) {
                    Point sp = points[i][j];
                    Point ep = points[i][j + 1];
                    Point c1 = new Point((sp.x + ep.x) / 2, sp.y);
                    Point c2 = new Point((sp.x + ep.x) / 2, ep.y);

                    path.curveTo(c1.x, c1.y, c2.x, c2.y, ep.x, ep.y);
                }
            }
        }

        /**
         * Draw a den by components by 3 ovals.
         *
         * @param graphics2D
         * @param x          coordinate.
         * @param y          coordinate.
         */
        public void drawDen(Graphics2D graphics2D, int x, int y) {
            graphics2D.drawOval(x + 5, y + 5, 70, 70);
            graphics2D.drawOval(x + 10, y + 10, 60, 60);
            graphics2D.fillOval(x + 15, y + 15, 50, 50);
        }

        /**
         * Code reference: https://blog.csdn.net/xietansheng/article/details/55669157
         * Draw a trapper by components: oval, rectangle and line
         *
         * @param graphics2D
         * @param x          coordinate.
         * @param y          coordinate.
         */
        public void drawTrapper(Graphics2D graphics2D, int x, int y) {
            //determine the vertices
            Point leftTopVert = new Point(x, y);
            Point rightTopVert = new Point(x + DEFAULT_GRID_LENGTH, y);
            Point leftButtonVert = new Point(x, y + DEFAULT_GRID_LENGTH);
            Point rightButtonVert = new Point(x + DEFAULT_GRID_LENGTH, y + DEFAULT_GRID_LENGTH);
            int[] xPoints = new int[]{leftTopVert.x + 15, leftTopVert.x + 15, leftTopVert.x + 20, leftTopVert.x + 20,
                    rightTopVert.x - 20, rightTopVert.x - 20, rightTopVert.x - 20, rightTopVert.x - 15,
                    rightTopVert.x - 15, rightTopVert.x - 10, rightTopVert.x - 10, rightTopVert.x - 15,
                    rightButtonVert.x - 15, rightButtonVert.x - 10, rightButtonVert.x - 10, rightButtonVert.x - 15,
                    rightButtonVert.x - 15, rightButtonVert.x - 20, rightButtonVert.x - 20, leftButtonVert.x + 20,
                    leftButtonVert.x + 20, leftButtonVert.x + 15, leftButtonVert.x + 15, leftButtonVert.x + 10,
                    leftButtonVert.x + 10, leftButtonVert.x + 15, leftTopVert.x + 15, leftTopVert.x + 10,
                    leftTopVert.x + 10, leftTopVert.x + 15};
            int[] yPoints = new int[]{leftTopVert.y + 15, leftTopVert.y + 10, leftTopVert.y + 10, leftTopVert.y + 15,
                    rightTopVert.y + 15, rightTopVert.y + 10, rightTopVert.y + 10, rightTopVert.y + 10,
                    rightTopVert.y + 15, rightTopVert.y + 15, rightTopVert.y + 20, rightTopVert.y + 20,
                    rightButtonVert.y - 20, rightButtonVert.y - 20, rightButtonVert.y - 15, rightButtonVert.y - 15,
                    rightButtonVert.y - 10, rightButtonVert.y - 10, rightButtonVert.y - 15, leftButtonVert.y - 15,
                    leftButtonVert.y - 10, leftButtonVert.y - 10, leftButtonVert.y - 15, leftButtonVert.y - 15,
                    leftButtonVert.y - 20, leftButtonVert.y - 20, leftTopVert.y + 20, leftTopVert.y + 20,
                    leftTopVert.y + 15, leftTopVert.y + 15};
            int nPoints = 30;
            graphics2D.drawPolyline(xPoints, yPoints, nPoints);
            graphics2D.fillRect(x + 20, y + 20, 40, 40);
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillOval(x + 25, y + 25, 30, 30);
            graphics2D.setColor(Color.BLACK);
        }
    }

    /**default/immutable priorities*/
    private static final Font DEFAULT_WORD_FONT = new Font("Serif", Font.PLAIN, 20);
    public static final int DEFAULT_GRID_LENGTH = 80;
    public static final int DEFAULT_X_LENGTH = 7;
    public static final int DEFAULT_Y_LENGTH = 9;

    /**fixed components*/
    private JScrollPane readMe;
    private JTextArea info;
    private JScrollPane outputPanel;

    /**set at least one control components*/
    public JButton confirm;

    /**control this holds*/
    public ParseInputControl control;

    /**components access update*/
    public DrawGrids grids;
    public JTextArea output;                            //Output area
    public JTextField command;                          //Input area

    //locations/team priorities:
    public Coordinate[][] coordinates;                  //(x, y) or (col, row)
    public Chesspiece[] blackTeam;
    public Chesspiece[] greyTeam;

    //Movement priorities:
    public boolean isBlackTurn = true;
    public boolean isGreyTurn = false;
    public int round = 1;

    /**default constructor: get a unchanged chessboard*/
    public Chessboard(){
        //add user oriented window
        output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font("Serif", Font.PLAIN, 15));
        outputPanel = new JScrollPane(output);
        outputPanel.setBounds(800, 80, 350, 480);
        outputPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        outputPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(outputPanel);

        //add read me part
        info = new JTextArea();
        info.setFont(new Font("Serif", Font.PLAIN, 20));
        info.setEnabled(false);
        info.setText("Ranks and Corresponds:\n" +
                "   Rank    Piece     Chinese Characters\n" +
                "   8       Elephant          象\n" +
                "   7       Lion                 狮\n" +
                "   6       Tiger                虎\n" +
                "   5       Leopard           豹\n" +
                "   4       Wolf                狼\n" +
                "   3       Dog                 狗\n" +
                "   2       Cat                  猫\n" +
                "   1       Rat                  鼠\n");
        readMe = new JScrollPane(info);
        readMe.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        readMe.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        readMe.setBounds(800, 570, 350, 300);
        add(readMe);

        output.append("Init chessboard...\n");

        //draw the background grids
        grids = new DrawGrids();
        grids.setSize(800, 900);
        add(grids);

        //init coordinates
        //X:col Y:row
        Coordinate[][] cooRowReverse = new Coordinate[7][9];
        for (int col = 0; col < DEFAULT_X_LENGTH; col++) {
            for (int row = 0; row < DEFAULT_Y_LENGTH; row++) {
                int rowReversed = 8 - row;
                boolean isRiver = false;
                boolean isDen = false;
                boolean isTrapper = false;
                Chesspiece.Team belongTo = null;
                if ((3 <= rowReversed && rowReversed <= 5) && ((1 <= col && col <= 2) || (4 <= col && col <= 5))) {
                    isRiver = true;
                }
                if (col == 3) {
                    if (rowReversed == 0) {
                        isDen = true;
                        belongTo = Chesspiece.Team.black;
                    } else if (rowReversed == 8) {
                        isDen = true;
                        belongTo = Chesspiece.Team.gray;
                    } else if (rowReversed == 1) {
                        isTrapper = true;
                        belongTo = Chesspiece.Team.black;
                    } else if (rowReversed == 7) {
                        isTrapper = true;
                        belongTo = Chesspiece.Team.gray;
                    }
                }
                if (col == 2 || col == 4) {
                    if (rowReversed == 0) {
                        isTrapper = true;
                        belongTo = Chesspiece.Team.black;
                    } else if (rowReversed == 8) {
                        isTrapper = true;
                        belongTo = Chesspiece.Team.gray;
                    }
                }
                cooRowReverse[col][row] = new Coordinate((col + 1) * DEFAULT_GRID_LENGTH, (row) * DEFAULT_GRID_LENGTH, false, isRiver, isTrapper, isDen, belongTo);
            }
        }
        coordinates = new Coordinate[7][9];
        for (int col = 0; col < DEFAULT_X_LENGTH; col++) {
            for (int row = 0; row < DEFAULT_Y_LENGTH; row++) {
                coordinates[col][row] = cooRowReverse[col][8 - row];
            }
        }

        output.append("init chess pieces...\n");
        //init the chess pieces according to their priorities.
        Chesspiece blackRat = new Chesspiece(Chesspiece.Team.black, Chesspiece.Priority.rat, Chesspiece.ChessPieceName.rat, this);
        Chesspiece blackCat = new Chesspiece(Chesspiece.Team.black, Chesspiece.Priority.cat, Chesspiece.ChessPieceName.cat, this);
        Chesspiece blackDog = new Chesspiece(Chesspiece.Team.black, Chesspiece.Priority.dog, Chesspiece.ChessPieceName.dog, this);
        Chesspiece blackWolf = new Chesspiece(Chesspiece.Team.black, Chesspiece.Priority.wolf, Chesspiece.ChessPieceName.wolf, this);
        Chesspiece blackLeopard = new Chesspiece(Chesspiece.Team.black, Chesspiece.Priority.leopard, Chesspiece.ChessPieceName.leopard,this );
        Chesspiece blackTiger = new Chesspiece(Chesspiece.Team.black, Chesspiece.Priority.tiger, Chesspiece.ChessPieceName.tiger, this);
        Chesspiece blackLion = new Chesspiece(Chesspiece.Team.black, Chesspiece.Priority.lion, Chesspiece.ChessPieceName.lion, this);
        Chesspiece blackElephant = new Chesspiece(Chesspiece.Team.black, Chesspiece.Priority.elephant, Chesspiece.ChessPieceName.elephant, this);
        coordinates[0][0].setChessPiece(blackTiger, this);
        coordinates[0][2].setChessPiece(blackElephant, this);
        coordinates[1][1].setChessPiece(blackCat, this);
        coordinates[2][2].setChessPiece(blackWolf, this);
        coordinates[4][2].setChessPiece(blackLeopard, this);
        coordinates[5][1].setChessPiece(blackDog, this);
        coordinates[6][0].setChessPiece(blackLion, this);
        coordinates[6][2].setChessPiece(blackRat, this);
        blackTeam = new Chesspiece[]{blackRat, blackCat, blackDog, blackWolf, blackLeopard, blackTiger, blackLion, blackElephant};

        Chesspiece grayRat = new Chesspiece(Chesspiece.Team.gray, Chesspiece.Priority.rat, Chesspiece.ChessPieceName.rat, this);
        Chesspiece grayCat = new Chesspiece(Chesspiece.Team.gray, Chesspiece.Priority.cat, Chesspiece.ChessPieceName.cat, this);
        Chesspiece grayDog = new Chesspiece(Chesspiece.Team.gray, Chesspiece.Priority.dog, Chesspiece.ChessPieceName.dog, this);
        Chesspiece grayWolf = new Chesspiece(Chesspiece.Team.gray, Chesspiece.Priority.wolf, Chesspiece.ChessPieceName.wolf, this);
        Chesspiece grayLeopard = new Chesspiece(Chesspiece.Team.gray, Chesspiece.Priority.leopard, Chesspiece.ChessPieceName.leopard, this);
        Chesspiece grayTiger = new Chesspiece(Chesspiece.Team.gray, Chesspiece.Priority.tiger, Chesspiece.ChessPieceName.tiger, this);
        Chesspiece grayLion = new Chesspiece(Chesspiece.Team.gray, Chesspiece.Priority.lion, Chesspiece.ChessPieceName.lion, this);
        Chesspiece grayElephant = new Chesspiece(Chesspiece.Team.gray, Chesspiece.Priority.elephant, Chesspiece.ChessPieceName.elephant, this);
        coordinates[0][6].setChessPiece(grayRat, this);
        coordinates[0][8].setChessPiece(grayLion, this);
        coordinates[1][7].setChessPiece(grayDog, this);
        coordinates[2][6].setChessPiece(grayLeopard, this);
        coordinates[4][6].setChessPiece(grayWolf, this);
        coordinates[5][7].setChessPiece(grayCat, this);
        coordinates[6][6].setChessPiece(grayElephant, this);
        coordinates[6][8].setChessPiece(grayTiger, this);
        greyTeam = new Chesspiece[]{grayRat, grayCat, grayDog, grayWolf, grayLeopard, grayTiger, grayLion, grayElephant};

        //for (int col = 6; col >= 0; col--) {
        //    for (int row = 8; row >= 0; row--) {
        //        System.out.println(coordinates[col][row]);
        //    }
        //}

        //init control
        control = ParseInputControl.installControllerOnChessboard(this);

        //add the components
        JLabel label = new JLabel("Command: ");
        label.setFont(DEFAULT_WORD_FONT);
        label.setBounds(80, 900, 100, 30);
        add(label);

        command = new JTextField(420);
        command.setBounds(180, 900, 420, 30);
        add(command);

        confirm = new JButton("Confirm");
        confirm.setFont(DEFAULT_WORD_FONT);
        confirm.setBounds(620, 900, 100, 30);
        confirm.addActionListener(control);
        add(confirm);

        //Debugger button: just make layout correct.
        JButton debuggerButton = new JButton(" ");
        debuggerButton.setContentAreaFilled(false);
        debuggerButton.setBorderPainted(false);
        debuggerButton.setEnabled(false);
        add(debuggerButton);

        //set panel's priorities:
        setSize(1300, 1000);
        setLayout(null);

        output.append("init done...\n");
        output.append("ROUND " + round++ +": TEAM BLACK TURN.\n");
    }
}
