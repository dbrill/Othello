
import java.io.Serializable;
import java.util.*;
import javax.swing.event.*;


public class Model implements Serializable{
    public static final boolean LIGHT = true;
    public static final boolean DARK = false;
    private char[][] spaces;
    private char turn;
    private Collection<ChangeListener> changeListeners;
    
    public Model(int size){
        changeListeners = new ArrayList<ChangeListener>();
        if (size%2 != 0)
            spaces = new char[size+1][size+1];
        else if (size < 8)
            spaces = new char[8][8];
        else if (size > 16)
            spaces = new char[16][16];
        else 
            spaces = new char[size][size];
        reset();
        turn = playerToChar(DARK);
    }
    
    public String toString(){
        String result = "";
        for (int row = 0; row < getSize(); row++){
            for (int column = 0; column < getSize(); column++){
                result += spaces[row][column] + " ";
                if (column == getSize()-1)//When it reaches end of row
                    result += "\n";//go to the next line
            }
        }
        return result;
    }
    
    private boolean charToPlayer(char piece){//encapsulating a type change 
        if (piece == 'D')
            return false;
        return LIGHT;
    }
    
    private char playerToChar(boolean piece){
        if (piece == DARK)
            return 'D';
        else
            return 'L';
    }
    
    public int getSize(){
        return spaces.length;
    }
    
    public int getCount(boolean player){
        int count = 0;
        for (int row = 0; row < getSize(); row++)
            for (int column = 0; column < getSize(); column++){
                if (isOccupiedBy(row, column, player))
                    count++;
            }
        return count;
    }
    
    public boolean getTurn(){
        return charToPlayer(turn);
    }
    
    public void takeTurn(){
        if(charToPlayer(turn))//if light, change to dark
            turn = playerToChar(DARK);
        else
            turn = playerToChar(LIGHT);        
        notifyChangeListeners();
    }
    
    public void reset(){
        for (int row = 0; row < getSize(); row++) {
            for (int column = 0; column < getSize(); column++) {
                spaces[row][column] = '.';
            }
        }
        //setting the middle four pieces to their starting values        
        occupy((getSize()-2)/2, (getSize()-2)/2, LIGHT);
        occupy((getSize()-2)/2, (getSize()-2)/2 + 1, DARK);
        occupy((getSize()-2)/2 + 1, (getSize()-2)/2, DARK);
        occupy((getSize()-2)/2 + 1, (getSize()-2)/2 + 1, LIGHT);
    }
    
    public void addChangeListener(ChangeListener cl){
        changeListeners.add(cl);
    }
    
    public void removeChangeListener(ChangeListener cl){
        changeListeners.remove(cl);
    }
    
    public void notifyChangeListeners(){
        for(ChangeListener listener : changeListeners)
            listener.stateChanged(null);
    }
    
    public void occupy(int row, int column, boolean player){
        if (row < 0 || row > getSize() || column < 0 || column > getSize())
            throw new IndexOutOfBoundsException();
        spaces[row][column] = playerToChar(player);
    }
    
    public boolean isOccupied(int row, int column){
        if (row < 0 || row > getSize() || column < 0 || column > getSize())
            throw new IndexOutOfBoundsException();
        return spaces[row][column] != '.';
    }
    
    public boolean isOccupiedBy(int row, int column, boolean player){
        if (row < 0 || row > getSize() || column < 0 || column > getSize())
            throw new IndexOutOfBoundsException();
        return spaces[row][column] == playerToChar(player);
    }
    
    
    public static void main(String[] args) {
        Model board = new Model(8);
        Controller controller = new Controller(board);        
        ScoreView s = new ScoreView(board);
        BoardView view = new BoardView(board, controller);

    }
}