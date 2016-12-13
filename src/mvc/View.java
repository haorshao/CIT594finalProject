package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
/**
 * GUI view, design all components and their layouts
 * @author Jiawei Xue
 *
 */
public class View {
	
	JFrame frame;
	JButton load, blur, edge;
	JFileChooser fileChooser;
	JTextField link;
    JSlider blurSlider;
    JLabel statusLabel;
    JPanel p0, p1, p2, p3, p4, p5;
	/**
	 * Constructor
	 */
	public View() {
		createComponents();
        doLayout();
        frame.setVisible(true);
	}
	/**
	 * create GUI components
	 */
	private void createComponents() {
    	frame = new JFrame("Image Processing");
    	// introduction panel
    	p0 = new JPanel();
    	p0.setPreferredSize(new Dimension(800, 200));
    	
    	statusLabel = new JLabel("Welcome to the Image Processing Tool");
    	p1 = new JPanel();
    	Font newLabelFont=new Font(statusLabel.getFont().getName(),Font.ITALIC+Font.BOLD,statusLabel.getFont().getSize());
    	statusLabel.setFont(newLabelFont);
    	p1.add(statusLabel);
    	
    	p2 = new JPanel();
    	load = new JButton("Load");
    	load.setPreferredSize(new Dimension(200, 30));
    	link = new JTextField("Image URL");
    	link.setPreferredSize(new Dimension(200, 30));
    	link.setFont(new Font("notSelected", Font.ITALIC, 14));
        link.setForeground(Color.gray);
        p2.add(link);
        p2.add(load);
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser = new JFileChooser(workingDirectory);
    	fileChooser.setDialogTitle("Open your image");
        
    	p3 = new JPanel();
    	blur = new JButton("Export");
    	blur.setPreferredSize(new Dimension(200, 30));
    	edge = new JButton("Edge Detection");
    	edge.setPreferredSize(new Dimension(200, 30));
    	p3.add(blur);
    	p3.add(edge);
    	
    	p4 = new JPanel();
    	blurSlider = new JSlider(1, 100);
    	blurSlider.setValue(0);
    	blurSlider.setPreferredSize(new Dimension(600, 50));
    	blurSlider.setExtent(1);
    	p4.add(new JLabel("Blur Extent:"));
    	p4.add(blurSlider);
    	
    	p0.setLayout(new GridLayout(4,1));
    	p0.add(p1);
    	p0.add(p2);
    	p0.add(p3);
    	p0.add(p4);
    	
    	p5 = new JPanel();
    	BufferedImage img;
		try {
			img = ImageIO.read(new File("noimage.png"));
			ImageIcon icon = new ImageIcon(img);
			icon.setImage(img.getScaledInstance(300, 300, Image.SCALE_DEFAULT));
			JLabel label = new JLabel(icon);
			p5.add(label);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * components layout
	 */
	private void doLayout() { 
		
    	frame.setLayout(new BorderLayout());
    	frame.setPreferredSize(new Dimension(975, 600));
    	frame.setResizable(false);
    	frame.add(p0, BorderLayout.NORTH);
    	frame.add(p5,BorderLayout.CENTER);
    	frame.pack();
    	doEnabling();
	}
	
	/**
	 * enable all buttons
	 */
	private void doEnabling() {
		load.setEnabled(true);
		blur.setEnabled(true);
		edge.setEnabled(true);
	}

	/**
	 * This method for adding ActionListener for buttons.
	 * @param listenForImg
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
	
	/**
	 * get the link
	 * @return
	 */
	public String getLink() {
		return link.getText();
	}
	
//	public static void main(String[] args) {
//		new View();
//	}
}
