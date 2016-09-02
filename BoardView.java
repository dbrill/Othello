
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;


public class BoardView extends JFrame implements ChangeListener{
    public DButton[][] buttons;
    private Model model;
    private Controller controller;
    private ScoreView scoreView;
    private int size;
    
    public BoardView(Model model, Controller controller){
        this.model = model;
        this.controller = controller;
        size = model.getSize();
        buttons = new DButton[size][size];
        
        setSize(800,800);
        setLocation(50,80);
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(size, size));
        model.addChangeListener(this);
        
        for (int row = 0; row < model.getSize(); row++)
            for (int col = 0; col < model.getSize(); col++){
                buttons[row][col] = new DButton(row, col);
                add(buttons[row][col]);
                buttons[row][col].addActionListener(controller);
                buttons[row][col].setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            }
        
        stateChanged(null);
        setVisible(true);
    }

    
    private void updateView(){
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++){
                buttons[row][col].setOpaque(true);
                if(model.isOccupiedBy(row, col, model.LIGHT))
                    buttons[row][col].setBackground(new Color(255, 150, 0));
                else if (model.isOccupiedBy(row, col, Model.DARK))
                    buttons[row][col].setBackground(new Color(170, 0, 255));
            }
    }
    
    @Override
    public void stateChanged(ChangeEvent ce) {
        updateView();
        
    }
}