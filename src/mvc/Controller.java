package mvc;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ImageProcessing.Blur;
import ImageProcessing.SobelEdges;
import database.Loadfile;


public class Controller implements ActionListener, ChangeListener{
	View theView;
	Model theModel;
//	Loadfile loadBlur;
//	Loadfile loadEdge;
	int blurIter;
//	Blur blurSl;
	int prevSlider;
	public Controller(View theView, Model theModel){
		this.theView = theView;
		this.theModel = theModel;
		this.theView.addViewListener(this);
		this.theView.addChangeListener(this);
		prevSlider = 0;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		blurIter = theView.blurSlider.getValue();
		System.out.println("BlurIter: " + blurIter);
		int error = blurIter - prevSlider;
		theModel.blurSlider(error);
		prevSlider = blurIter;
	}
	@Override
	/**
	 * load from file or link
	 */
	public void actionPerformed(ActionEvent event){
		String cmd = event.getActionCommand();
		if(cmd.equals("Load")){
			if (theView.getLink().equals("Image URL")) {
				try {
	                loadFile();
	            } catch (IOException ioe) {
	                System.out.println("not able to load from the file");
	            }
			} else {
				try {
					URL link = new URL(theView.getLink());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}else if(cmd.equals("Edge Detection")){
			theModel.edgeDetection();
			displayOutput(theModel.loadEdge.outputImage);
		}else if(cmd.equals("Blur")){
			theModel.blurButton();
//			displayOutput(theModel.loadBlur.outputImage);
			updateImg(theModel.loadBlur.outputImage);
		}
	}
	
	private void loadFile() throws IOException{
		 if (this.theView.fileChooser.showOpenDialog(theView.frame) != JFileChooser.APPROVE_OPTION) {
	            return;
		 }
		 File selected = theView.fileChooser.getSelectedFile();
		 if (selected == null) {
	            return;
		 }
		 theView.statusLabel.setText("Current image: " + selected.getName());
		 System.out.println("Loading Image from " + selected.getName() + " ...");
		 this.theModel.loadFile(selected.getName(), "outputBlurTest.jpg", "outputSobelEdgesTest.jpg");
		 theView.statusLabel.setText("Current image: "+ theModel.loadBlur.inputFileName);
		 System.out.println("Loading complete.");
		 System.out.println("Image: " + theModel.loadBlur.inputFileName);
		 System.out.println("Mission Complete");
		 updateImg(theModel.loadBlur.currentImg);

	}
	
	public JLabel createIcon(BufferedImage img) {
		ImageIcon icon = new ImageIcon(img);
		icon.setImage(img.getScaledInstance(480, 360, Image.SCALE_DEFAULT));
		JLabel label = new JLabel(icon);
		return label;
	}
	
	public void updateImg(BufferedImage newImg) {
		theView.p5.removeAll();
		theView.p5.add(createIcon(newImg));
	}
	
	public void displayOutput(BufferedImage outputImg) {
		theView.p6.add(createIcon(outputImg));
		theView.frame.remove(theView.p5);
		theView.frame.add(theView.p5, BorderLayout.EAST);
		theView.frame.add(theView.p6, BorderLayout.WEST);
		
	}
	
	public static void main(String[] args){
		View view = new View();
		Model model = new Model();
		Controller test = new Controller(view, model);
	}
}
