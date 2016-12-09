package mvc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

public class View {
	
	JFrame frame;
	JButton load, blur, edge;
	JFileChooser fileChooser;
	JTextField link;
    JSlider blurSlider;
    JLabel statusLabel;
    JPanel p1, p2, p3, p4;
	
	public View() {
		blur = new JButton("Blur");
    	edge = new JButton("Edge Detection");
    	load = new JButton("Load");
		createComponents();
        doLayout();
        frame.setVisible(true);
	}
	
	private void createComponents() {
    	frame = new JFrame("Image Processing");
    	statusLabel = new JLabel("Welcome to the Image Processing Tool");
    	p1 = new JPanel();
    	Font newLabelFont=new Font(statusLabel.getFont().getName(),Font.ITALIC+Font.BOLD,statusLabel.getFont().getSize());
    	statusLabel.setFont(newLabelFont);
    	p1.add(statusLabel);

    	p2 = new JPanel();
    	link = new JTextField("Image URL");
    	link.setFont(new Font("notSelected", Font.ITALIC, 14));
        link.setForeground(Color.gray);
        p2.add(link);
        p2.add(load);
        fileChooser = new JFileChooser();
    	fileChooser.setDialogTitle("Open your image");
        
    	p3 = new JPanel();
    	p3.add(blur);
    	p3.add(edge);
    	
    	p4 = new JPanel();
    	blurSlider = new JSlider(1, 300);
    	blurSlider.setValue(0);
    	blurSlider.setPreferredSize(new Dimension(600, 50));
    	Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
    	labelTable.put(0, new JLabel("0"));
    	labelTable.put(60, new JLabel("1"));
    	labelTable.put(120, new JLabel("2"));
    	labelTable.put(180, new JLabel("3"));
    	labelTable.put(240, new JLabel("4"));
    	labelTable.put(300, new JLabel("5"));
    	blurSlider.setLabelTable(labelTable);
    	blurSlider.setPaintLabels(true);
    	p4.add(blurSlider);
	}
	
	private void doLayout() { 
		
    	frame.setLayout(new GridLayout(4, 1));
    	frame.setPreferredSize(new Dimension(800, 300));
    	frame.setResizable(false);
    	frame.add(p1);
    	frame.add(p2);
    	frame.add(p3);
    	frame.add(p4);
    	frame.pack();
    	doEnabling();
	}
	
	private void doEnabling() {
		load.setEnabled(true);
		blur.setEnabled(true);
		edge.setEnabled(true);
	}
	/**
	 * This method for adding ActionListener for buttons.
	 * @param listenForImg-ActionListner
	 */
	public void addViewListener(ActionListener listenForImg){
		load.addActionListener(listenForImg);
		blur.addActionListener(listenForImg);
		edge.addActionListener(listenForImg);
	}
	/**
	 * This method for adding ChangeListener for the blur slider.
	 * @param listenForImg
	 */
	public void addChangeListener(ChangeListener listenForImg){
		blurSlider.addChangeListener(listenForImg);
	}
	public static void main(String[] args) {
		new View();
	}

}
