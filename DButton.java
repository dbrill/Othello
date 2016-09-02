
import javax.swing.*;


public class DButton extends JButton implements Dimensioned {
    private final int row;
    private final int column;
    
    public DButton(int row, int column) {
        super(row + ", " + column);
        this.row = row;
        this.column = column;
    }
    
    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }
}
