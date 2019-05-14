import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class TwoDimArray extends JFrame {
    private final int START_X = 20;
    private final int START_Y = 40;
    private final int ROWS = 6;
    private final int COLS = 6;
    private final int BOX_WIDTH = 20;
    private final int BOX_HEIGHT = 20; 
    private MaskableBox boxes[][];
    private Color boxColors[][]; 
    private Button resetButton;
    private JPanel panel1;
    private JPanel panel2;
    private Color picked_color;
    
    public static void main(String[] args) {
	    TwoDimArray frame  = new TwoDimArray();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setSize(500,300);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		
		
    }
    public TwoDimArray(){
    boxes = new MaskableBox[ROWS][COLS];
    boxColors = new Color[ROWS][COLS];
    resetButton = new Button("ResetColors");
    resetButton.setBounds(200, 200, 120, 40);
    resetButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            //randomizeColors();
            buildBoxes();
            repaint();
        }
    });
    panel2 = new JPanel();
    panel2.setLocation(100, 200);
    resetButton.setLocation(100, 200);
    panel2.add(resetButton);
    this.add(panel2);
  
   
    //separate building colors so we can add a button later
    //to re-randomize them.
   // randomizeColors();
    buildBoxes();
}

public void paint(Graphics g) {
    for(int row = 0; row < boxes.length; row ++) {
        for(int col = 0; col < boxes[row].length; col++) {
            if(boxes[row][col].isClicked()) {
            	picked_color = JColorChooser.showDialog(panel1, "Pick Color", picked_color);
                boxes[row][col].setMaskColor(picked_color);
                boxes[row][col].setMask(!boxes[row][col].isMask());
                boxes[row][col].setClicked(false);
            }
            boxes[row][col].draw(g);
        }
    }
}

private void removeMouseListeners() {
    for(int row = 0; row < boxes.length; row ++) {
        for(int col = 0; col < boxes[row].length; col++) {
            removeMouseListener(boxes[row][col]);
        }
    }
}

private void buildBoxes() {
    removeMouseListeners();
    for(int row = 0; row < boxes.length; row++) {
        for(int col = 0; col < boxes[row].length; col++) {
            boxes[row][col] = 
              new MaskableBox(START_X + col * BOX_WIDTH,
                              START_Y + row * BOX_HEIGHT,
                              BOX_WIDTH,
                              BOX_HEIGHT,
                              Color.gray,
                              boxColors[row][col],
                              true,
                              this);
            addMouseListener(boxes[row][col]);
        }
    }
}

private void randomizeColors() {
    int[] chosenColors = {0, 0, 0, 0, 0, 0, 0, 0};
    Color[] availableColors = { Color.red, Color.blue, Color.green,
      Color.yellow, Color.cyan, Color.magenta, Color.pink, Color.orange };
    for(int row = 0; row < boxes.length; row++) {
        for(int col = 0; col < boxes[row].length; col++) {
            for(;;) {
                int rnd = (int)(Math.random() * 8);
                if(chosenColors[rnd] < 2) {
                    chosenColors[rnd]++;
                    boxColors[row][col] = availableColors[rnd];
                   break;
                }
            }
        }
    }
}
}