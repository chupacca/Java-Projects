/*
 * TCSS 305 - Assignment 5: Tetris
 * 
 * Creates the panel with the board.
 */

package panels;

import actions.BoardAction;
import backend.ActionMap;
import backend.TetrisShape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import listeners.KeyReader;

import model.Board;
import model.TetrisPiece;

/**
 * A panel with the board on it.
 * 
 * @author Peter M Chu
 * @version 05/20/2016
 */
public class TetrisPanel extends JPanel implements Observer, PropertyChangeListener {
    
    /** The amount I want the background to be opaque. */
    private static final float OPAQUE = 0.8f;
    
    /** The number of points per piece. */
    private static final int PIECE_PTS = 4;
    
    /** The default delay (in milliseconds) for the move timer. */
    private static final int MOVE_DELAY = 1000;

    /** The initial delay (in milliseconds) for the move timer. */
    private static final int INITIAL_DELAY = 500;
    
    /** Milliseconds taken from the timer's delay for every level. */
    private static final int STEP_DELAY = 100;
    
    /** Initial width of a Tetris game board. */
    private static final int INITIAL_WIDTH = 10;

    /** Initial height of a Tetris game board. */
    private static final int INITIAL_HEIGHT = 20;
    
    /** The number of lines to be ignored in the board string. */
    private static final int IGNORED_LINES = 5;
    
    /** The number of points gained from clearing a single line. */
    private static final int LINE_PTS = 10;
    
    /** Default Font Size. */
    private static final int FONT_SIZE = 20;
    
    /** Default Font Size. */
    private static final int LINES_PER_LEVEL = 5;
    
    /** Points property change name. */
    private static final String POINT_STR = "points";
    
    /** Level property change name. */
    private static final String LEVEL_STR = "level";
    
    /** Number of lines cleared property change name. */
    private static final String LINES_CLR = "linesCleared";
    
    /** String representation of rectangle. */
    private static final String RECTANGLE = "rectangle";
    
    
    /** Generated serial number. */
    private static final long serialVersionUID = -2125580410266258202L;
    
    /** The preferred window size. */
    private final Dimension myDimSize;
    
    /** The panel to view the controls for this game. */
    //private final ControlPanel myControlPanel;
    
    /** The collection of keystroke actions as integers. */
    private final int[] myControlKeys;
    
    /** The timer that controls the progression of the game. */
    private final Timer myGameTimer;
    
    /** A map with the character keys to a a color value. */
    private Map<Character, Color> myCharColMap;
    
    /** The integer that is associated with some key and a type of BoardAction. */
    private Map<Integer, BoardAction> myActionMap;
    
    /** The keyreader for this panel. */
    private KeyReader myKeyReader;
    
    /** The string letting me know which player this is. */
    private final String myPlayer;
    
    /** A boolean that represents whether or not this panel is displaying the game. */
    private boolean myIsDisplayed;
    
    /** A boolean that represents whether or not this panel is is pause mode. */
    private boolean myIsPaused;
    
    /** A boolean that represents whether or not this game has ever started. */
    private boolean myHasStarted;
    
    /** An integer that represents what level you are on. */
    private int myLevel;
    
    /** An integer representing your score. */
    private int myScore;
    
    /** The board for the Tetris Game. */
    private Board myBoard;
    
    /** The number of line cleared. */
    private int myLines;
    
    /** The shape of the blocks. */
    private String myShape;
    
    /** 
     * Constructs the TetrisPanel. 
     * @param theControlKeys (The integer array that has the control keys).
     * @param theWidth (The width of this panel).
     * @param theHeight (The height of this panel).
     * @param thePlayer (String that tells me which player this is).
     * */
    public TetrisPanel(final int[] theControlKeys, final int theWidth, final int theHeight, 
                       final String thePlayer) {
        super();
        myBoard = new Board(INITIAL_WIDTH, INITIAL_HEIGHT);
        myControlKeys = (int[]) theControlKeys.clone();
        myGameTimer = new Timer(MOVE_DELAY, new MoveListener());              
        myDimSize = new Dimension(theWidth, theHeight);
        myPlayer = thePlayer;
        myShape = RECTANGLE;
        setFactors();
        setup();
    }
    
    /** Sets up certain inital factors for this object. */
    private void setFactors() {
        final TitledBorder border = BorderFactory.createTitledBorder(myPlayer);
        border.setTitleColor(Color.WHITE);
        this.setBorder(border);
        myGameTimer.setInitialDelay(INITIAL_DELAY); 
        setBackground(Color.BLACK);
    }
    
    /** Sets up the appearance and the timer of the panel and frame. */
    private void setup() {
        setActionMap(makeActionMap((int[]) myControlKeys.clone()));
        myCharColMap = makeCharColorMap();
        myKeyReader = new KeyReader(this);
        firePropertyChange("keyChange" + myPlayer, null, myKeyReader);
        myLevel = 1;
        myLines = 0;
        myIsDisplayed = true;
        myIsPaused = false;
        myHasStarted = false;
        setPreferredSize(myDimSize);
        
        myBoard.addObserver(this);
         
        setSize(myDimSize);
        firePropertyChange("changeTwoPanel" + myPlayer, null, myDimSize.getSize());
    }
    
    /**
     * A boolean to let you know if this game ever started.
     * @return a boolean indicating whether this game has ever started.
     */
    public boolean isStarted() {
        return myHasStarted;
    }
    
    /** 
     * Allows access to the key reader.
     * @return myKeyReader (the key reader for this object). 
     */
    public KeyReader getKeyReader() {
        return myKeyReader;
    }
    
    /**
     * Allows to set the dimensions of this board and panel.
     * @param theWidth (The width of this panel).
     * @param theHeight (The height of this panel).
     * @param theBoardWidth (The width of this board).
     * @param theBoardHeight (The height of this board).
     */
    public void setGameSize(final int theWidth, final int theHeight, final int theBoardWidth, 
                           final int theBoardHeight) {
        myBoard = new Board(theBoardWidth, theBoardHeight);
        myActionMap.clear();
        myCharColMap.clear();
        stop();
        myDimSize.setSize(theWidth, theHeight);
        firePropertyChange(POINT_STR, null, myScore);
        firePropertyChange(LEVEL_STR, null, myLevel);
        myScore = 0;
        setup();
        
        
    }
    
    /**
     * Sets this panels visibility.
     * @param theIsDisplayed (the boolean representing whether this panel is visible).
     */
    public void setIsDisplay(final boolean theIsDisplayed) {
        myIsDisplayed = theIsDisplayed;
    }
    
    /**
     * Sets this panels pause mode.
     * @param theIsPaused (the boolean representing whether this panel is in pause mode).
     */
    public void setIsPaused(final boolean theIsPaused) {
        myIsPaused = theIsPaused;
    }
    
    /**
     * Gets this panels pause mode boolean.
     * @return myIsPaused (whether or not this game was paused).
     */
    public boolean isPaused() {
        return myIsPaused;
    }
    
    /**
     * Lets you know if the timer is running or not.
     * @return a boolean indicating whether the timer is running.
     */
    public boolean isRunning() {
        return myGameTimer.isRunning();
    }
    
    /**
     * Makes a map with characters as keys and colors as values.
     * @return a map with character (that represent a type of tetris piece block)
     * and color values.
     */
    private Map<Character, Color> makeCharColorMap() {
        final char[] charPieces = {'I', 'J', 'L', '0', 'S', 'T', 'Z'};
        final Color[] colors = {Color.BLUE, Color.GREEN, Color.ORANGE, Color.CYAN, Color.RED, 
                                Color.PINK, Color.MAGENTA};
        final Map<Character, Color> map = new HashMap<Character, Color>();
        for (int i = 0; i < colors.length; i++) {
            map.put(charPieces[i], colors[i]);
        }
        return map;
        
    }
    
    /**
     * Create a map for the actions with the same keys as myControlMap.
     * @param theControlKeys (The integer array that has the control keys).
     * @return Map<String, BoardAction> (a map with myControlMap's keys 
     * and actions as values).
     */
    public Map<Integer, BoardAction> makeActionMap(final int[] theControlKeys) {
        
        final int[] controlKeys = (int[]) theControlKeys.clone();
        
        final Map<Integer, BoardAction> actionMap = new HashMap<Integer, BoardAction>();
        
        final BoardAction[] actionArray = new ActionMap(this).getActionMap();
        
        for (int i = 0; i < controlKeys.length; i++) {
            actionMap.put(controlKeys[i], actionArray[i]);
        }
        
        return actionMap;
    }
    
    /**
     * Sets the actionMap.
     * @param theMap the Action Map I want for this panel.
     */
    public void setActionMap(final Map<Integer, BoardAction> theMap) {
        final Map<Integer, BoardAction> map = new HashMap<Integer, BoardAction>(); 
        //theMap.putAll(map);
        map.putAll(theMap);
        
        myActionMap = map;
    }
    
    /** Starts the timer. */
    public void start() {
        myGameTimer.setDelay(MOVE_DELAY);
        myHasStarted = true;
        myIsPaused = false;
        myScore = 0;
        myLevel = 1;
        myLines = 0;
        firePropertyChange(POINT_STR, null, myScore);
        firePropertyChange(LEVEL_STR, null, myLevel);
        firePropertyChange("clearLines", null, 0);
        myBoard.newGame();
        myGameTimer.start();
    }
    
    /** Stops the timer. */
    public void stop() {
        myIsPaused = false;
        clearViewNPanel();
        myGameTimer.stop();
    }
    
    /** Clears the respective ViewNextPanel. */
    public void clearViewNPanel() {
        firePropertyChange("stop", null, myPlayer);
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        //System.out.println(myPlayer);
        //System.out.println(myBoard.toString()); //////////////////
        //System.out.println();
        
        if (myHasStarted) {
            if (myIsDisplayed && !myIsPaused) {
                 
                final int width = myBoard.getWidth() + 3;
                
                String board = myBoard.toString();
                
                board = board.substring(width * IGNORED_LINES - 1, board.length() - width - 1);
                board = board.replace("|", "").replace("-", "").replace("\n", "");
                                
                for (int i = 0; i < board.length(); i++) {
                    
                    if (board.charAt(i) != ' ') {
                        paintBlock(i, board.charAt(i), g2d);
                    }
                }
                
            } else {
                
                if (myIsPaused) {
                    paintText(g2d, "Paused");
                } else {
                    paintText(g2d, "Not Playing");
                }    
            }
            
        } else {
            paintText(g2d, "Press 'New Game' in File");
        }
        
    }
    
    /**
     * Paints a text to the string.
     * @param theGraphics (The graphic I want to paint the text one).
     * @param theText (The String I want painted).
     */
    private void paintText(final Graphics theGraphics, final String theText) {
        final Font font = new Font(Font.SERIF, Font.BOLD + Font.ITALIC, FONT_SIZE);
        theGraphics.setFont(font);
     
        final FontRenderContext renderContext = ((Graphics2D) theGraphics).
                        getFontRenderContext();
        
        final GlyphVector glyphVector = font.createGlyphVector(renderContext, theText);

        final Rectangle2D visualBounds = glyphVector.getVisualBounds().getBounds();
        final int x =
                (int) ((getWidth() - visualBounds.getWidth()) / 2 
                                - visualBounds.getX());
        final int y = 
                (int) ((getHeight() - visualBounds.getHeight()) / 2 
                                - visualBounds.getY());

        theGraphics.drawString(theText, x, y);
    }
    
    /**
     * Paints a block given the index on the board's string and the char.
     * @param theIndex (The index on the board's string).
     * @param theChar (The character that represents what type of piece this
     * block belongs to).
     * @param theGraphic (the graphic I want to draw on).
     */
    private void paintBlock(final int theIndex, final char theChar, 
                            final Graphics2D theGraphic) {
        
        final int xInc = this.getWidth() / myBoard.getWidth();
        final int yInc = this.getHeight() / myBoard.getHeight();
        
        final int xCor = theIndex % myBoard.getWidth();
        final int tempY = theIndex - xCor / yInc;
        final int yCor = tempY / 10;
        
        
        theGraphic.setPaint(myCharColMap.get(theChar));
        
        final TetrisShape tempShape = new TetrisShape(xCor * xInc, yCor * yInc, xInc, yInc, 
                                                      myShape);
        final RectangularShape shape = tempShape.getShape();
                        //getShape(xCor * xInc, yCor * yInc, xInc, yInc); 
        
        theGraphic.fill(shape);
        theGraphic.setPaint(Color.WHITE);
        theGraphic.draw(shape);

        
    }
    
    
    /**
     * Given two x-coordinates and two y-coordinates, a Rectangular shape is returned 
     * depending on what myShape is.
     * @param x1 (the first x-coordinate).
     * @param y1 (the first y-coordinate).
     * @param x2 (the second x-coordinate).
     * @param y2 (the second y-coordinate).
     * @return a Rectangular Shape that will be represented as blocks in the game.
     
    private RectangularShape getShape(final int x1, final int y1, final int x2, final int y2) {
        RectangularShape shape = new Rectangle.Double();
        
        if (RECTANGLE.equals(myShape)) {
            shape = new Rectangle2D.Double(x1, y1, x2, y2);
        } else if (ROUNDED_REC.equals(myShape)) {
            shape = new RoundRectangle2D.Double(x1, y1, x2, y2, ROUNDED_REC_NUM, 
                                                ROUNDED_REC_NUM);
        } else if (ELLIPSE.equals(myShape)) {
            shape = new RoundRectangle2D.Double(x1, y1, x2, y2, ROUNDED_REC_ELL, 
                                                ROUNDED_REC_ELL);
        }
        
        
        return shape;
    }*/
    
    /**
     * Gets the board I am playing with.
     * @return myBoard (the board object that my tetris game is using).
     */
    public Board getBoard() {
        return myBoard;
    }
    
    /**
     * myActionMap can be accessed from an outside class.
     * @return myActionMap (the map with the integer value of 
     * a key and an action).
     */
    public Map<Integer, BoardAction> getContActMap() {
        return myActionMap;
    }
    
    @Override
    public void update(final Observable theObject, final Object theArgument) {
        
        if (theArgument instanceof String) {
            repaint();
        } else if (theArgument instanceof TetrisPiece) {
            final TetrisPiece piece = (TetrisPiece) theArgument;
            firePropertyChange("piece", null, piece);
            firePropertyChange("color", null, myCharColMap.get(piece.getBlock().getChar()));
            myScore += PIECE_PTS;
            
            if (myHasStarted) {
                firePropertyChange(POINT_STR, null, myScore - PIECE_PTS);
            } else {
                firePropertyChange(POINT_STR, null, myScore);
            }
        } else if (theArgument instanceof Boolean) {
            myGameTimer.stop();
            repaint();
            JOptionPane.showMessageDialog(null, "GAME OVER" + myPlayer, "NICE TRY! ", 
                                          JOptionPane.PLAIN_MESSAGE);
        } else if (theArgument instanceof Object[]) {
            myScore += LINE_PTS * ((Object[]) theArgument).length;
            firePropertyChange(POINT_STR, null, myScore);
            myLines += ((Object[]) theArgument).length;
            firePropertyChange(LINES_CLR, null, ((Object[]) theArgument).length);
            if (myLines % LINES_PER_LEVEL == 0) {
                myLevel++;
                firePropertyChange(LEVEL_STR, null, myLevel);
                if (myGameTimer.getDelay() - STEP_DELAY >= 0) {
                    myGameTimer.setDelay(myGameTimer.getDelay() - STEP_DELAY);
                }
            }  
        }
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent theArgument) {
        
        if ("resize".equals(theArgument.getPropertyName())) {
            final int[] temp = (int[]) theArgument.getNewValue();
            final int[] hWRC = (int[]) temp.clone();
            this.setSize(new Dimension(hWRC[0], hWRC[1]));
            
        }
    }
    
    /** Method that returns the name of the player. 
     *  @return myPlayer (name of the player).
     */
    public String getPlayer() {
        return myPlayer;
    }
    
    /**
     * Returns the timer for this panel's tetris game.
     * @return the Timer for this panel.
     */
    public Timer getTimer() {
        return myGameTimer;
    }
    
    /**
     * Give a copy of myControlKeys.
     * @return myControlKeys (an integer array representing key values).
     */
    public int[] getPanelKeys() {
        return (int[]) myControlKeys.clone();
    }
    
    
    /**
     * Returns the board height.
     * @return myHeight (the width of my board).
     */
    public int getBoardHeight() {
        return myBoard.getHeight(); 
    }
    
    
    /**
     * Returns the board width.
     * @return myWidth (the width of my board).
     */
    public int getBoardWidth() {
        return myBoard.getWidth(); 
    }
    
    /**
     * Makes the background of this panel opaque.
     * @param theIsTrippy (a boolean that represents whether the opaque will be enabled).
     */
    public void enableTrippy(final boolean theIsTrippy) {
        if (theIsTrippy) {
            setBackground(new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(),
                                    Color.BLACK.getBlue(), OPAQUE));
        } else {
            setBackground(Color.BLACK);
        }
        
    }
    
    /**
     * Changes the type of shape the blocks.
     * @param theShape (the string name of rectangular shape).
     */
    public void setShape(final String theShape) {
        myShape = theShape;
        if (myGameTimer.isRunning()) {
            repaint();
        }
    }
    
    /**
     * A class that listens for timer events and moves the shape, checking for
     * the window boundaries and changing direction as appropriate.
     */
    public class MoveListener implements ActionListener {
        
        @Override
        public void actionPerformed(final ActionEvent theArgument) {
            myBoard.step();
        }

    }
 
}
