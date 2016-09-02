
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dlbrill
 */
public class Controller implements ActionListener {

    private Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void actionPerformed(ActionEvent ae) {
        Dimensioned hasDim = (Dimensioned) ae.getSource();
        int row = hasDim.getRow();
        int col = hasDim.getColumn();

        if (!model.isOccupied(row, col)) {
            //test all 8 possible directions for a valid move
            if (validUpstream(row, col, 1, 1)
                    | validUpstream(row, col, 1, 0)
                    | validUpstream(row, col, 1, -1)
                    | validUpstream(row, col, -1, 1)
                    | validUpstream(row, col, -1, 0)
                    | validUpstream(row, col, -1, -1)
                    | validUpstream(row, col, 0, 1)
                    | validUpstream(row, col, 0, -1)) {


                model.occupy(row, col, model.getTurn());
                model.takeTurn();
                System.out.println(model);
            }

        }
    }

    public boolean validUpstream(int row, int col, int deltaRow, int deltaCol) {
 
        if (row + deltaRow >= model.getSize() || col + deltaCol >= model.getSize()
                || row + deltaRow < 0 || col + deltaCol < 0) 
            //if it is out of the board return false
        {
            return false;
        }
        if (model.isOccupied(row + deltaRow, col + deltaCol)) {
            //if it's occupied...continue
            if (model.isOccupiedBy(row + deltaRow, col + deltaCol, model.getTurn())) {
                // if it's occupied by the player 
                if (model.isOccupied(row, col)) {
                    // if previous box is not empty
                    //occupy and return true
                    return true;
                } else {
                    return false;
                }

            }
            if (validUpstream(row + deltaRow , col + deltaCol, deltaRow, deltaCol)) {
                //if it's a legal move occupy and return true
                    model.occupy(row + deltaRow, col + deltaCol, model.getTurn());
                    return true;
                }
        }
    return false;
}
    }