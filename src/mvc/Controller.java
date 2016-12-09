package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Time;

import javax.swing.JFileChooser;
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
	public void actionPerformed(ActionEvent event){
		String cmd = event.getActionCommand();
		if(cmd.equals("Load")){
			try {
                loadFile();
            } catch (IOException ioe) {
                System.out.println("not able to load from the file");
            }
		}else if(cmd.equals("Edge Detection")){
			theModel.edgeDetection();
		}else if(cmd.equals("Blur")){
			theModel.blurButton();
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
	}
	public static void main(String[] args){
		View view = new View();
		Model model = new Model();
		Controller test = new Controller(view, model);
	}
}
