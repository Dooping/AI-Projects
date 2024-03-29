package circuit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
	        setWindow();
	    }
	
	private static void setWindow() {
		
	    final String[] labels = {"Population: ", "Elite: ", "Crossover", "Mutation: ", "Gerações: ","Crossover Op: ", "HillClimbing: "};
	    final JTextField []textField = new JTextField[labels.length-2];
	    int labelsLength = labels.length;

	    //Create and populate the panel.
	    JPanel p = new JPanel(new SpringLayout());
	    for (int i = 0; i < labelsLength-2; i++) {
	        JLabel l = new JLabel(labels[i], JLabel.TRAILING);
	        p.add(l);
	        textField[i] = new JTextField(10);
	        l.setLabelFor(textField[i]);
	        p.add(textField[i]);
	    }
	    JLabel l = new JLabel(labels[labels.length-2], JLabel.TRAILING);
	    p.add(l);
	    final JComboBox c = new JComboBox();
	    c.addItem("OX1");
	    c.addItem("OX2");
	    p.add(c);
	    l = new JLabel(labels[labels.length-1], JLabel.TRAILING);
	    p.add(l);
	    final JCheckBox cc = new JCheckBox();
	    cc.setSelected(true);
	    p.add(cc);
	    JButton button = new JButton("Start!");
	    p.add(new JLabel());
	    p.add(button);
	    textField[0].setText(""+CircuitTest.DEFAULT_POP);
	    textField[1].setText(""+GeneticAlgorithm.ELITE);
	    textField[2].setText(""+Math.round(100*GeneticAlgorithm.DEFAULT_P_CROSSOVER));
	    textField[3].setText(""+Math.round(100*GeneticAlgorithm.DEFAULT_P_MUTATE));
	    textField[4].setText(""+GeneticAlgorithm.GEN_CAP);

	    //Lay out the panel.
	    p.setLayout(new GridLayout(8, 2));
	    
	    button.addActionListener(new ActionListener() {

	        public void actionPerformed(ActionEvent e)
	        {
	        	try {
	        		CircuitTest circuit = new CircuitTest(Integer.parseInt(textField[0].getText()),
							Float.parseFloat(textField[2].getText())/100, Float.parseFloat(textField[3].getText())/100,
							Integer.parseInt(textField[4].getText()), Integer.parseInt(textField[1].getText()),
							c.getSelectedIndex(), cc.isSelected());
	        	} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
	            //Execute when button is pressed
	            //System.out.println("Test");
	        }
	    });
	    
	    JFrame frame = new JFrame("Values");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    p.setOpaque(true); 
	    frame.setContentPane(p);
	    
	    frame.setLocation(600, 100);
	    frame.pack();
	    frame.setVisible(true);
	}
	
}
