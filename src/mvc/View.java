package mvc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class View {
	
	private JFrame frame;
	private JButton load, blur, edge;
	private JFileChooser fileChooser;
	private JTextField link;
    private JSlider blurSlider;
    private JLabel statusLabel;
    private JPanel p1, p2, p3, p4;
	
	public View() {
		// TODO Auto-generated constructor stub
		createComponents();
        doLayout();
        addListeners();
        frame.setVisible(true);
	}
	
	private void createComponents() {

    	frame = new JFrame("Image Processing");
    	statusLabel = new JLabel("Welcome to the Image Processing Tool");
    	
    	p1 = new JPanel();
//    	p0.setLayout(new BorderLayout());
//    	p0.add(statusLabel,BorderLayout.WEST);
    	
    	Font newLabelFont=new Font(statusLabel.getFont().getName(),Font.ITALIC+Font.BOLD,statusLabel.getFont().getSize());
    	statusLabel.setFont(newLabelFont);
    	p1.add(statusLabel);
    	
    	p2 = new JPanel();
    	link = new JTextField("Image URL");
    	link.setFont(new Font("notSelected", Font.ITALIC, 14));
        link.setForeground(Color.gray);
        p2.add(link);
        load = new JButton("Load");
        p2.add(load);
        
        fileChooser = new JFileChooser();
    	fileChooser.setDialogTitle("Open your image");
        
    	p3 = new JPanel();
    	blur = new JButton("Blur");
    	edge = new JButton("Edge Detection");
    	p3.add(blur);
    	p3.add(edge);
    	
    	p4 = new JPanel();
    	blurSlider = new JSlider();
//    	currentTimeSlider.addChangeListener(new ChangeListener() {
//            public void stateChanged(ChangeEvent e) {
//                statusLabel.setText("Value : " 
//                + ((JSlider)e.getSource()).getValue());
//             }
//          });
    	blurSlider.setPreferredSize(new Dimension(600, 50));
    	p4.add(blurSlider);

	}
	
	private void doLayout() { 
		
    	frame.setLayout(new GridLayout(4, 1));
    	frame.setPreferredSize(new Dimension(800, 300));
    	frame.setResizable(false);
//    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
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
	
	private void loadFile() throws IOException {
	    	
        if (fileChooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selected = fileChooser.getSelectedFile();
//        fileChooser.
        if (selected == null) {
            return;
        }
        statusLabel.setText("Current image: " + selected.getName());
        String filename = selected.getAbsolutePath();
        System.out.println("Loading image from " + selected.getName() + " ...");
        
        //TODO - create a song from the file
        
        System.out.println("Loading complete.");
        doEnabling();

	}
	
	
	private void addListeners() {
		
		load.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
	                loadFile();
	            } catch (IOException ioe) {
	                System.out.println("not able to load from the file");
	            }	
			}
		});
		
		blur.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("~~");	
			}
		});
		
		edge.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("~~");
			}
		});
		
	}
	
	public static void main(String[] args) {
		new View();
	}

}
