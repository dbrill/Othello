
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dlbrill
 */
public class ScoreView extends JFrame implements ChangeListener{

    private Model model; 
    private JLabel scoreLight;
    private JLabel scoreDark;
    

    
    public ScoreView (Model model){
        //build the jframe
        this.model = model; 
        setSize(150,150);
        setLocation(1000, 50);
        setBackground(Color.GREEN);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        scoreLight = new JLabel();
        scoreDark = new JLabel();
        setLayout(new FlowLayout());
        

        
        model.addChangeListener(this);
        add(scoreLight);
        add(scoreDark);
        setVisible(true);
        //update the jframe 
        updateScoreView();
        scoreKeeper();
    }
    private void updateScoreView(){

        scoreLight.setText("Light:" + model.getCount(model.LIGHT));
        scoreDark.setText("Dark:" + model.getCount(model.DARK));
        //update the text using getCount method
    }
    public boolean gameOver(){
        if(((model.getCount(model.LIGHT) + model.getCount(model.DARK)) == (model.getSize()*model.getSize()))){
            return true;
        }
        else{
            return false;
        }
    }
    private void scoreKeeper(){
        String scores = ("Dark :" + model.getCount(model.DARK) + " Light: " + model.getCount(model.LIGHT) +"\n");        
        if(gameOver() == true){
            
            File file = new File("OthelloScores.odt");
            try {
                
                //FileWriter filewriter = new FileWriter(file, true);
                FileOutputStream filestream = new FileOutputStream(file, true);
                OutputStreamWriter outwriter = new OutputStreamWriter(filestream);
                BufferedWriter bufferwriter = new BufferedWriter(outwriter);
                bufferwriter.write(scores + "\n");
                bufferwriter.close();
            } catch (IOException ex) {
                Logger.getLogger(ScoreView.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }
        public static void main(String[] args) {
            Model m = new Model(8);
        ScoreView s = new ScoreView(m);
        
    }
        
        
        
        
        
    
    @Override
    public void stateChanged(ChangeEvent ce) {
        updateScoreView();
        scoreKeeper();
    }
    
}
